import com.github.prominence.openweathermap.api.OpenWeatherMapClient;

public class OpenWeatherAPI_Singleton {
    private static OpenWeatherMapClient single_instance = null;
    private static final String API_TOKEN = "5a7ad938261325c20efbf4c89b7c09b4";

    OpenWeatherAPI_Singleton() {}

    // thread safe singleton
    public static synchronized OpenWeatherMapClient getInstance()
    {
        if (single_instance == null)
            single_instance = new OpenWeatherMapClient(API_TOKEN);

        return single_instance;
    }


}
