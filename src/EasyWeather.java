import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;


import java.time.LocalDate;
import java.util.ArrayList;

public class EasyWeather {
    private OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();
    public Weather getWeatherByCity(String city){

        Weather weather= weatherClient.currentWeather()
                .single()
                .byCityName(city)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        return weather;
    }

    public double getTemperatureByCity(){
        return 0;
    }

    public double getHumidityByCity(){
        return 0;
    }

    public ArrayList<Double> getThreeDayTempStarting(LocalDate startDate, ArrayList<String> Locations){
        ArrayList<Double> temperatures = new ArrayList<Double>();
        return temperatures;
    }

    public ArrayList<Double> getThreeDayHumidityStarting(LocalDate startDate, ArrayList<String> Locations){
        ArrayList<Double> humidities = new ArrayList<Double>();
        return humidities;
    }

    public ArrayList<Double> getThreeDayWindStarting(LocalDate startDate, ArrayList<String> Locations){
        ArrayList<Double> winds = new ArrayList<Double>();
        return winds;
    }

    public ArrayList<Double> getThreeDayCloudStarting(LocalDate startDate, ArrayList<String> Locations){
        ArrayList<Double> cloudcovers = new ArrayList<Double>();
        return cloudcovers;
    }


    private ArrayList<WeatherForecast> getFiveDayForecast(){
        Forecast forecast = weatherClient
                .forecast5Day3HourStep()
                .byCityName("tallaght")
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .count(40)
                .retrieve()
                .asJava();
        return new ArrayList<WeatherForecast>(forecast.getWeatherForecasts());
    }







}
