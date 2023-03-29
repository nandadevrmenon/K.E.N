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
        System.out.println(easyWeather.getTemperatureByCity(city));
        if(easyWeather.getTemperatureByCity(city) > -300){
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
        String city = "Dublin,ie";
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
        String city = "Dublin,ie";
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
        String city = "Dublin,ie";
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
        String city = "Dublin,ie";
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