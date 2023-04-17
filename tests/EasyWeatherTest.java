import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
        } else if (weather.getWind()==null && easyWeather.getWindByCity(city) == -301){
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }

    }

    @Test
    void testGetCloudByCity(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Las Vegas,us";

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
        else if (weather.getClouds()==null && easyWeather.getCloudByCity(city) == -301){
            System.out.println("NO DATA/NO CONNECTION TO INTERNET");
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
        if (weather.getRain()==null && easyWeather.getRainByCity(city)==-301  ){
            assertTrue(true);
        }
        else if(easyWeather.getRainByCity(city) == weather.getRain().getOneHourLevel()){
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

    //TESTING THE 3 DAY FORECASTS
    //INVALID DATE 3 DAY TEMP
    @Test
    void testGetThreeDayTempStartingInvalid(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";
        //invalid date
        LocalDate date = LocalDate.of(2020, 1, 1);
      //check for illegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> easyWeather.getThreeDayTempStarting(date, city));
    }
    //valid date
    @Test
    void testGetThreeDayTempStartingValid(){
            EasyWeather easyWeather = new EasyWeather();
            String city = "Dublin,ie";

            LocalDate startDate = LocalDate.now().plusDays(1);
            ArrayList<Double> temperatures = easyWeather.getThreeDayTempStarting(startDate, city);
            assertNotNull(temperatures);
            assertEquals(6, temperatures.size());

    }

    @Test
    void testGetThreeDayHumidityStartingInvalid(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";
        //invalid date
        LocalDate date = LocalDate.of(2020, 1, 1);
        //check for illegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> easyWeather.getThreeDayHumidityStarting(date, city));
    }

    @Test
    void testGetThreeDayHumidityStartingValid(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        LocalDate startDate = LocalDate.now().plusDays(1);
        ArrayList<Double> temperatures = easyWeather.getThreeDayHumidityStarting(startDate, city);
        assertNotNull(temperatures);
        assertEquals(6, temperatures.size());

    }

    @Test
    void testGetThreeDayWindStartingInvalid(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";
        //invalid date
        LocalDate date = LocalDate.of(2020, 1, 1);
        //check for illegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> easyWeather.getThreeDayWindStarting(date, city));
    }

    @Test
    void testGetThreeDayWindStartingValid(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        LocalDate startDate = LocalDate.now().plusDays(1);
        ArrayList<Double> temperatures = easyWeather.getThreeDayWindStarting(startDate, city);
        assertNotNull(temperatures);
        assertEquals(6, temperatures.size());

    }

    @Test
    void testGetThreeDayCloudStartingInvalid(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";
        //invalid date
        LocalDate date = LocalDate.of(2020, 1, 1);
        //check for illegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> easyWeather.getThreeDayCloudStarting(date, city));
    }

    @Test
    void testGetThreeDayCloudStartingValid(){
        EasyWeather easyWeather = new EasyWeather();
        String city = "Dublin,ie";

        LocalDate startDate = LocalDate.now().plusDays(1);
        ArrayList<Double> temperatures = easyWeather.getThreeDayCloudStarting(startDate, city);
        assertNotNull(temperatures);
        assertEquals(6, temperatures.size());

    }

//    @Test
//    public void testThreeDayForecast() {
//        EasyWeather easyWeather = new EasyWeather();
//        String city = "Dublin,ie";
//        LocalDate startDate = LocalDate.now().plusDays(1);
//        ArrayList<WeatherForecast> threeDayForecast = easyWeather.getThreeDayForecast(city, startDate);
//        assertNotNull(threeDayForecast);
//        assertEquals(6, threeDayForecast.size());  // 2 forecasts per day for 3 days
//        for (WeatherForecast forecast : threeDayForecast) {
//            assertTrue(forecast.getForecastTime().toLocalDate().isEqual(startDate) ||
//                    forecast.getForecastTime().toLocalDate().isEqual(startDate.plusDays(1)) ||
//                    forecast.getForecastTime().toLocalDate().isEqual(startDate.plusDays(2)));
//            assertTrue(forecast.getForecastTime().getHour() == 10 || forecast.getForecastTime().getHour() == 19);
//        }
//    }

}