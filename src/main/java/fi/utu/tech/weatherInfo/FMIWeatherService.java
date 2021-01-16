package fi.utu.tech.weatherInfo;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.net.URLConnection;

public class FMIWeatherService {

	private final String CapURL = "https://opendata.fmi.fi/wfs?request=GetCapabilities";
	private final String FeaURL = "https://opendata.fmi.fi/wfs?request=GetFeature";
	private final String ValURL = "https://opendata.fmi.fi/wfs?request=GetPropertyValue";
	private static final String DataURL = "http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::multipointcoverage&place=turku&"
			+ "&storedquery_id=fmi::observations::weather::hourly::simple&place=turku&starttime=2012-02-27T00:00:00Z"
			+ "&endtime=2012-02-27T00:00:00Z&parameters=TA_PT1H_AVG, TA_PT1H_MAX";

	/*
	 * In this method your are required to fetch weather data from The Finnish
	 * Meteorological Institute. The data is received in XML-format.
	 */

	public static WeatherData getWeather() {
		String DataURL = "http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::multipointcoverage&place=turku&"
				+ "&storedquery_id=fmi::observations::weather::hourly::simple&place=turku&starttime=2012-02-27T00:00:00Z"
				+ "&endtime=2012-02-27T00:00:00Z&parameters=TA_PT1H_AVG, TA_PT1H_MAX";

		URLConnection url = new URL(DataURL).openConnection();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();

		Document doc = db.parse(url.getInputStream());

		NodeList list = doc.getElementsByTagName("wfs:member");

		for (int i; i< list.getLength(); i++) {
			Node n = list.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element el = (Element) n;
				String type = el.getElementsByTagName("BsWfs:ParameterName").item(0).getTextContent();

				if (type.equals("TA_PT1H_AVG")) {
					String TA_PT1H_AVG = el.getElementsByTagName("BsWfs:ParameterValue").item(0).getTextContent();
				}
				else if (type.equals("TA_PT1H_MAX")) {
					String TA_PT1H_MAX = el.getElementsByTagName("BsWfs:ParameterValue").item(0).getTextContent();
				}

			}
		}

		WeatherData weatherData = new WeatherData();


		return weatherData;
	}

}

