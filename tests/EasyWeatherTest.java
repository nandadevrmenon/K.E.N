import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EasyWeatherTest {

    @Test
    void testGetWeatherByCity() {
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";
        System.out.println(easyWeather.getWeatherByCity(city));
        if(easyWeather.getWeatherByCity(city).getLocation().getName().equals("Dublin")){
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
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
        if(easyWeather.getTemperatureByCity(city) == weather.getTemperature().getValue()){
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
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
        if(easyWeather.getHumidityByCity(city) == weather.getHumidity().getValue()){
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
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
        if(easyWeather.getWindByCity(city) == weather.getWind().getSpeed()){
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
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
        if(easyWeather.getCloudByCity(city) == weather.getClouds().getValue()){
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
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
        else{
            assertTrue(false);
        }
    }
    //test for invalid city input
    @Test
    void testGetTemperatureByCityInvalids(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Ddsv";
        if(-300 ==  easyWeather.getTemperatureByCity(city)){
            System.out.println("Invalid city");
            assertTrue(true);
        }
        else if(easyWeather.getTemperatureByCity(city) == -301){
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
    }


    @Test
    void testGetHumidityByCityInvalids(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "rrew";
        if(-300 ==  easyWeather.getHumidityByCity(city)){
            System.out.println("Invalid city");
            assertTrue(true);
        }
        else if(easyWeather.getHumidityByCity(city)  == -301){
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
    }


    @Test
    void testGetCloudsByCityInvalids(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "c";
        if(-300 ==  easyWeather.getCloudByCity(city)){
            System.out.println("Invalid city");
            assertTrue(true);
        }
        else if(easyWeather.getCloudByCity(city) == -301){
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
            assertTrue(true);
        }
        else{
            assertTrue(false);
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
        else if(easyWeather.getRainByCity(city) == -301){
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
    }




}