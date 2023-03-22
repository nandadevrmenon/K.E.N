import com.github.prominence.openweathermap.api.OpenWeatherMapClient;

import java.io.File;

public class Main {
    private static String API_TOKEN;

    public static void main(String[] args) {

        try {
            File API_TOKEN_FILE = new File("resources/API_KEY.txt");
            API_TOKEN = new java.util.Scanner(API_TOKEN_FILE).useDelimiter("\\Z").next();
        } catch (Exception e) {
            System.out.println("API_KEY.txt not found");
        }

        System.out.println("Hello, World!");

        OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(API_TOKEN);

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
