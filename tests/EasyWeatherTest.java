import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Clouds;
import com.github.prominence.openweathermap.api.model.WeatherState;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EasyWeatherTest {

    @Test
    void testGetWeatherByCity() {
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";
        System.out.println(easyWeather.getWeatherByCity(city));
        assertTrue(easyWeather.getWeatherByCity(city).getLocation().getName().equals("Dublin"));
    }


    @Test
    void testGetTemperatureByCity() {
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();

       Weather weather= weatherClient.currentWeather()
                .single()
                .byCityName(city)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();
        assertTrue(easyWeather.getTemperatureByCity(city) == weather.getTemperature().getValue());

    }

    @Test
    void testGetHumidityByCity() {
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();

        Weather weather= weatherClient.currentWeather()
                .single()
                .byCityName(city)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();
        assertTrue(easyWeather.getHumidityByCity(city) == weather.getHumidity().getValue());

    }

    @Test
    void testGetWindByCity() {
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();

        Weather weather= weatherClient.currentWeather()
                .single()
                .byCityName(city)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();
        assertTrue(easyWeather.getWindByCity(city) == weather.getWind().getSpeed());

    }

    @Test
    void testGetCloudByCity(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();

        Weather weather= weatherClient.currentWeather()
                .single()
                .byCityName(city)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();
        assertTrue(easyWeather.getCloudByCity(city) == weather.getClouds().getValue());

    }

    @Test
    void testGetRainByCity(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();

        Weather weather= weatherClient.currentWeather()
                .single()
                .byCityName(city)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();
        if(easyWeather.getRainByCity(city) == weather.getRain().getOneHourLevel()){
            assertTrue(true);
        }
        else {
            assertTrue(easyWeather.getRainByCity(city) == weather.getRain().getOneHourLevel());
        }
    }
    //test for invalid city input
    @Test
    void testGetTemperatureByCityInvalids(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Ddsvsss";
        if(easyWeather.getTemperatureByCity(city) ==-300){
            System.out.println("Invalid city");
            assertTrue(true);
        }
        else {
            assertTrue(easyWeather.getTemperatureByCity(city) == -301);
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
        }
    }


    @Test
    void testGetHumidityByCityInvalids(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "rrew";
        if(easyWeather.getHumidityByCity(city)==-300){
            System.out.println("Invalid city");
            assertTrue(true);
        }
        else if(easyWeather.getHumidityByCity(city)  == -301){
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
            assertTrue(true);
        }
        else{
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
            assertTrue(easyWeather.getHumidityByCity(city)  == -301);
        }
    }


    @Test
    void testGetCloudsByCityInvalids(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "ckajsd";
        if(-300 ==  easyWeather.getCloudByCity(city)){
            System.out.println("Invalid city");
            assertTrue(true);
        }
        else{
            assertTrue(easyWeather.getCloudByCity(city) == -301);
        }
    }

    @Test
    void testGetRainByCityInvalids(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "vcb";
        if(-300 ==  easyWeather.getRainByCity(city)){
            System.out.println("Invalid city");
            assertTrue(true);
        }
        else{
            assertTrue(easyWeather.getRainByCity(city) == -301);
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
        }
    }

    @Test
    public void testThreeDayForecast() {
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";
        LocalDate startDate = LocalDate.now().plusDays(3);
        ArrayList<WeatherForecast> threeDayForecast = easyWeather.getThreeDayForecast(city, startDate);
        assertNotNull(threeDayForecast);
        assertTrue(threeDayForecast.size()>=5);  // 2 forecasts per day for 3 days but if we ask for a forecast that starts in 3 days time, we might not get the last timestamp from the API
        for (WeatherForecast forecast : threeDayForecast) {
            //make sure that the dates we are getting are all before (stardate plus 3 days) and after current date
            assertTrue(forecast.getForecastTime().toLocalDate().isBefore(startDate.plusDays(3)) &&
                    forecast.getForecastTime().toLocalDate().isAfter(LocalDate.now()));
            //also make sure that we only get the timestamps for 9 am and 6pm
            assertTrue(forecast.getForecastTime().getHour() == 10 || forecast.getForecastTime().getHour() == 19);
        }
    }

    @Test
    public void testTripForecast(){
        ArrayList<WeatherForecast> tripForecasts = new ArrayList<WeatherForecast>();
        ArrayList<String> locations = new ArrayList<String>(Arrays.asList("dublin","nonsense","nonsense","dublin","Los Angeles"));
        LocalDate startDate = LocalDate.now().plusDays(1);
        tripForecasts= EasyWeather.getTripForecast(locations,startDate);
        assertTrue(tripForecasts.get(0) != null);
        assertTrue(tripForecasts.get(1) == null);
        assertTrue(tripForecasts.get(2) == null);
        assertTrue(tripForecasts.get(3) != null);
        assertTrue(tripForecasts.get(4) != null);
    }

@Test
    public void testGetPrettyForecast(){
        ArrayList<WeatherForecast> forecast = EasyWeather.getTripForecast(new ArrayList<String>(Arrays.asList("nagaland","kuching","los angeles","askjdas","Lagos")),LocalDate.now());//sample locations


        String location ="Kuching";
        WeatherForecast fc = forecast.get(1);       //points to kuching
        String weather = "Location: "+location +", Weather State: "+fc.getWeatherState().getName()+"("+fc.getWeatherState().getDescription()+"), ";
        weather = weather + "Temperature: "+fc.getTemperature().getValue()+" " +fc.getTemperature().getUnit()+", "+fc.getClouds().toString()+", Wind:"+fc.getWind().getSpeed()+" "+fc.getWind().getUnit()+", ";     //we make the string we are supposed to get
        if(fc.getRain()!=null){     //if rain info exists add it to the string
        weather=weather+fc.getRain().toString();
        }
        assertTrue( EasyWeather.getPrettyForecast(fc,location).equals(weather));        //assert that what we get is equal to the above string

            //test for non existent location
        fc=forecast.get(3);
        location ="rubbish";
        weather = "Location: "+location+", Sorry I do not have data for that location. Maybe it is misspelled?";
        assertTrue(EasyWeather.getPrettyForecast(fc,location).equals(weather));
}



}