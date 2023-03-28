import com.github.prominence.openweathermap.api.OpenWeatherMapClient;

import java.io.File;

public class Main {
    private static String API_TOKEN;

    public static void main(String[] args) {

    EasyWeather weather = new EasyWeather();
        System.out.println(weather.getWeatherByCity("mumbai"));
    }
}
