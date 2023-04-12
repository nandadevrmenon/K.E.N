import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


final class OpenWeatherAPI_SingletonTest {

    @Test
    void testGetInstance() {
        OpenWeatherMapClient openWeatherClient = OpenWeatherAPI_Singleton.getInstance();

        assertNotNull(openWeatherClient);      // tests that client connects successfully and does not return null

        //we create a copy of the api to test if we get the same instance or not
        OpenWeatherMapClient clientCopy = OpenWeatherAPI_Singleton.getInstance();
        assertEquals(openWeatherClient, clientCopy);

    }
}