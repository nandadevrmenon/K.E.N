
public class Main {
    private static String API_TOKEN;

    public static void main(String[] args) {

    EasyWeather weather = new EasyWeather();
        System.out.println(weather.getTemperatureByCity("bihar,india"));
        System.out.println(weather.getWeatherByCity("bihar,india"));

    }
}
