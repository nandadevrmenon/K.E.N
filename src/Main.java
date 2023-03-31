import org.apache.hc.core5.http.ParseException;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {


        EasyOpenAI openAI = new EasyOpenAI();
        openAI.chat("I want to go to Dublin 12 degree, Mumbai 32 degree, and New York -2 degree.");


        //EasyWeather weather = new EasyWeather();
        //System.out.println(weather.getWeatherByCity("mumbai"));
    }
}
