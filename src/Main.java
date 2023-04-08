
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hi, I'm WeatherBot. I can help you with all your weather needs.");
//        String mode = askUserAboutMode();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String input = scanner.nextLine().toLowerCase().trim();
            if(input.equals("exit")){
                break;
            }
            EasyOpenAI openAI = new EasyOpenAI();
            input = openAI.getSingleLocationToken(input);
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
            System.out.println("A problem occurred while fetching the data.Please try again later");
            return "exit";
        }
        return weather.toString();

    }
}


