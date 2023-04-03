
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hi, I'm WeatherBot, I can tell you the current weather of any place in the world."+
                "\r\n"+"Which city's weather would you like to know? ");

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String input = scanner.next().toLowerCase().trim();
            if(input.equals("exit")){
                break;
            }
            String weatherString = getWeather(input);
            if(weatherString.equals("exit")) break;
            if(!weatherString.isEmpty()){
                System.out.println("The weather there is "+"\r\n"+ weatherString);
                System.out.println("Would you like to know the weather at another place? If not, type 'exit'.");
            }
        }
    }

    public static String getWeather(String city){
        EasyWeather easyWeather = new EasyWeather();
        Weather weather=null;
        try{
           weather =  easyWeather.getWeatherByCity(city);
        }
        catch( IllegalArgumentException iae){
            System.out.println(iae.getMessage());
            System.out.println("Try another Location please");
            return "";
        }
        if(weather == null){
            System.out.println("A problem occured while fetching the data.Please try again later");
            return "exit";
        }
        return weather.toString();

    }
}


