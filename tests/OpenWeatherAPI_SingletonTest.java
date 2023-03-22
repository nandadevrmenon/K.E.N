import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherAPI_SingletonTest {

        @Test
        void getInstance() {
            OpenWeatherAPI_Singleton openWeatherAPI_singleton = new OpenWeatherAPI_Singleton();
            OpenWeatherMapClient openWeatherClient = OpenWeatherAPI_Singleton.getInstance();

            if(openWeatherClient == null) {
                System.out.println("OpenWeatherClient is null");
            }
            else {
                System.out.println("Connected to OpenWeatherClient");
            }
        }

}