import com.github.prominence.openweathermap.api.OpenWeatherMapClient;

import java.io.File;

public class Main {
    private static String API_TOKEN;

    public static void main(String[] args) {

        OpenWeatherAPI_Singleton openWeatherAPI_singleton = new OpenWeatherAPI_Singleton();
        OpenWeatherMapClient openWeatherClient = OpenWeatherAPI_Singleton.getInstance();

        if(openWeatherClient == null) {
            System.out.println("OpenWeatherClient is null");
        }
        else {
            System.out.println("Connected to OpenWeatherClient");
        }



        /*
        final Weather weather = openWeatherClient.currentWeather()
                .single()
                .byCityName("London")
                .unitSystem(UnitSystem.METRIC)
                .retrieve().asJava();


        System.out.println(weather.getTemperature());

         */
    }
}
