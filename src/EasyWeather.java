import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Rain;
import com.github.prominence.openweathermap.api.model.weather.Weather;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class EasyWeather {
    private final static OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();
    public static Weather getWeatherByCity(String city) throws IllegalArgumentException{
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

    public static double getTemperatureByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        return weather.getTemperature().getValue();

    }

    public static double getHumidityByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        return weather.getHumidity().getValue();
    }
    public static double getWindByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        return  weather.getWind().getSpeed();

    }

    public static double getCloudByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }

        return weather.getClouds().getValue();
    }

    public static double getRainByCity(String city){
        Weather weather;
        try{
            weather = getWeatherByCity(city);
        }catch(IllegalArgumentException iae){
            return -300;            //use -300 as en error code to tell us that the location entered isn't a valid location
        }
        if(weather == null){
            return -301;            //-301 as a no data found error code
        }
        Rain rain = weather.getRain();
        if(rain==null){
            return -301;
        }
        return rain.getOneHourLevel();
    }


    public static ArrayList<WeatherForecast> getThreeDayForecast(String city,LocalDate startDate) throws IllegalArgumentException{
        Forecast fiveDaysWeather;
        try{
            fiveDaysWeather = weatherClient
                    .forecast5Day3HourStep()
                    .byCityName(city)
                    .language(Language.ENGLISH)
                    .unitSystem(UnitSystem.METRIC)
                    .count(40)
                    .retrieve()
                    .asJava();      //returns 5 day forecast with 3 hour diff in each timestamp
        }catch(NoDataFoundException ndfe){
                throw new IllegalArgumentException("The location entered might not be a valid location.");
        }

        //we extract 3day forecast for only 9am and 6pm of each day
        ArrayList<WeatherForecast> fiveDayForecast = new ArrayList<>(fiveDaysWeather.getWeatherForecasts());
        Iterator<WeatherForecast> fdit = fiveDayForecast.iterator();
        ArrayList<WeatherForecast> threeDayForecast = new ArrayList<>();

        int count =0;
        while(fdit.hasNext() && count<6){
            WeatherForecast forecast = fdit.next();
            if(forecast.getForecastTime().isBefore(startDate.atStartOfDay())){continue;}
            if(forecast.getForecastTime().getHour()==19 || forecast.getForecastTime().getHour()==10){
                threeDayForecast.add(forecast);
                count++;
            }

        }
        return threeDayForecast;
    }

    private static WeatherForecast getFirstForecast(String location,LocalDate startDate){
        ArrayList<WeatherForecast> allForecasts;
        try{
            allForecasts = getThreeDayForecast(location,startDate);
        }
        catch(IllegalArgumentException iae){
            return null;
        }
        return allForecasts.get(0);
    }
    private static WeatherForecast getSecondForecast(String location,LocalDate startDate){
        ArrayList<WeatherForecast> allForecasts;
        try{
            allForecasts = getThreeDayForecast(location,startDate);
        }
        catch(IllegalArgumentException iae){
            return null;
        }
        return allForecasts.get(1);
    }
    private static WeatherForecast getThirdForecast(String location,LocalDate startDate){
        ArrayList<WeatherForecast> allForecasts;
        try{
            allForecasts = getThreeDayForecast(location,startDate);
        }
        catch(IllegalArgumentException iae){
            return null;
        }
        return allForecasts.get(2);
    }
    private static WeatherForecast getFourthForecast(String location,LocalDate startDate){
        ArrayList<WeatherForecast> allForecasts;
        try{
            allForecasts = getThreeDayForecast(location,startDate);
        }
        catch(IllegalArgumentException iae){
            return null;
        }
        return allForecasts.get(3);
    }
    private static WeatherForecast getFifthForecast(String location,LocalDate startDate){
        ArrayList<WeatherForecast> allForecasts;
        try{
            allForecasts = getThreeDayForecast(location,startDate);
        }
        catch(IllegalArgumentException iae){
            return null;
        }
        return allForecasts.get(4);
    }

    public static ArrayList<WeatherForecast> getTripForecast(ArrayList<String> locations,LocalDate startDate){
        ArrayList<WeatherForecast> tripForecasts = new ArrayList<>();
        tripForecasts.add(getFirstForecast(locations.get(0),startDate));
        cooldown();
        tripForecasts.add(getSecondForecast(locations.get(1),startDate));
        cooldown();
        tripForecasts.add(getThirdForecast(locations.get(2),startDate));
        cooldown();
        tripForecasts.add(getFourthForecast(locations.get(3),startDate));
        cooldown();
        tripForecasts.add(getFifthForecast(locations.get(4),startDate));

        return tripForecasts;
    }

    public static String getPrettyForecast(WeatherForecast fc,String location){

        String str = "Location: "+location+", ";
        return str;

    }



    private static void cooldown(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }









}
