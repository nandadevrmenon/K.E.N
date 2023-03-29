import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;


import java.time.LocalDate;
import java.util.ArrayList;

public class EasyWeather {
    private OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();
    public Weather getWeatherByCity(String city) throws IllegalArgumentException{
        Weather weather;
        try{
            weather= weatherClient.currentWeather()
                    .single()
                    .byCityName(city)
                    .language(Language.ENGLISH)
                    .unitSystem(UnitSystem.METRIC)
                    .retrieve()
                    .asJava();
        }catch(NoDataFoundException ndfe){
            if(ndfe.getMessage().contains("Data for provided parameters wasn't found")){
                throw new IllegalArgumentException("The location entered might not be a valid location.");
            }
            else weather = null;
        }
        return weather;
    }

    public double getTemperatureByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        double temp = weather.getTemperature().getValue();
            return temp;
    }

    public double getHumidityByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        double humidity = weather.getHumidity().getValue();
        return humidity;
    }
    public double getWindByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        double wind = weather.getWind().getSpeed();
        return wind;
    }

    public double getCloudByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        double cloud = weather.getClouds().getValue();
        return cloud;
    }

    public double getRainByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        double rain = weather.getRain().getThreeHourLevel();
        return rain;
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
