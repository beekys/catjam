package fi.utu.tech.weatherInfo;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FMIWeatherService {

	private final String CapURL = "https://opendata.fmi.fi/wfs?request=GetCapabilities";
	private final String FeaURL = "https://opendata.fmi.fi/wfs?request=GetFeature";
	private final String ValURL = "https://opendata.fmi.fi/wfs?request=GetPropertyValue";
	private static final String DataURL = "http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::multipointcoverage&place=turku&";
	private static String BaseURL = "http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::multipointcoverage&place=helsinki&starttime=%sT%s:00:00Z&endtime=%sT%s:00:00Z&parameters=temperature,PrecipitationAmount";

	/*
	 * In this method your are required to fetch weather data from The Finnish
	 * Meteorological Institute. The data is received in XML-format.
	 */

	private static URL buildFetchURL(int hour) throws MalformedURLException {
		String date = java.time.LocalDate.now().toString();
		String finalURL = String.format(BaseURL, date, String.valueOf(hour+12), date, String.valueOf(hour+12));
		return new URL(finalURL);
	}

	private static NodeList buildXMLNodeList(StringBuffer response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		var doc = docBuilder.parse(new InputSource(new StringReader(response.toString())));
		NodeList dataNodes = doc.getElementsByTagName("gml:DataBlock");
		return dataNodes;
	}

	private static StringBuffer getResponse(BufferedReader in) throws IOException
	{
		StringBuffer response = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}


	/**
	 * @return	WeatherData weatherData - contains temperature and rain amount
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static WeatherData getWeather(int hour) throws IOException, ParserConfigurationException, SAXException {

		URL url = buildFetchURL(hour);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		// Get the right data from XML
		NodeList dataNodes = buildXMLNodeList(getResponse(in));

		String[] dataArray = dataNodes.item(0).getTextContent().replaceAll("\\s{2,}", " ").trim().split(" ");

		double temperature = Double.parseDouble(dataArray[0]);
		double rainAmount = Double.parseDouble(dataArray[1]);

		return new WeatherData(temperature, rainAmount);
	}

}
