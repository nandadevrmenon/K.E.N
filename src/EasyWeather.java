import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.github.prominence.openweathermap.api.model.onecall.current.CurrentWeatherData;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.github.prominence.openweathermap.api.request.weather.CurrentWeatherRequester;

public class EasyWeather {
    private OpenWeatherMapClient weatherClient = OpenWeatherAPI_Singleton.getInstance();
    public Weather getWeatherByCity(String city){

        Weather weather= weatherClient.currentWeather().single().byCityName(city).language(Language.ENGLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
        return weather;
    }




}
