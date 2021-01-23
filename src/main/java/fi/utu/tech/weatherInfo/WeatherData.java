package fi.utu.tech.weatherInfo;

/*
 * Class presenting current weather
 * Is returned by  weather service class
 */

public class WeatherData {

	/*
	 * What kind of data is needed? What are the variable types. Define class
	 * variables to hold the data
	 */

	/*
	 * Since this class is only a container for weather data we only need to set the
	 * data in the constructor.
	 */

	private double rainAmount;
	private double temperature;

	public WeatherData(double rainAmount, double temperature) {
		this.rainAmount = rainAmount;
		this.temperature = temperature;
	}

	/*
	 * In case of server error
	 */
	public WeatherData() {
	}


	public double getTemperature() {
		return rainAmount;
	}

	public double getRainAmount() {
		return temperature;
	}
}
