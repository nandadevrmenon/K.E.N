import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {


        EasyOpenAI openAI = new EasyOpenAI();
        openAI.chat("I want to go to Dublin 12 degree, Mumbai 32 degree, and New York -2 degree.");


    EasyWeather weather = new EasyWeather();


        ArrayList<String> locations = new ArrayList<String>();
        locations.add("cork,ireland");
        locations.add("dublin,ireland");
        locations.add("galway,ireland");
        locations.add("wicklow,ireland");
        locations.add("tallaght,ireland");
        ArrayList<Double> temps = weather.getThreeDayTempStarting(LocalDate.now(),"helsinki");
        ArrayList<Double> humidities = weather.getThreeDayHumidityStarting(LocalDate.now(),"helsinki");
        ArrayList<Double> cloudcovers = weather.getThreeDayCloudStarting(LocalDate.now(),"helsinki");
        ArrayList<Double> winds = weather.getThreeDayWindStarting(LocalDate.now(),"helsinki");

        System.out.println(temps);
        System.out.println(humidities);
        System.out.println(cloudcovers);
        System.out.println(winds);

    }
}
