import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import junit.framework.Test;

public class OpenWeatherAPI_Singleton {
    private static OpenWeatherMapClient single_instance = null;
    private static final String API_TOKEN = "5a7ad938261325c20efbf4c89b7c09b4";

    OpenWeatherAPI_Singleton() {}

    // thread safe singleton
    public static synchronized OpenWeatherMapClient getInstance()
    {
        if (single_instance == null)
            single_instance = new OpenWeatherMapClient(API_TOKEN);
            single_instance.setConnectionTimeout(1000);
            single_instance.setReadTimeout(1000);

        return single_instance;
    }


}
