
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hi, I'm WeatherBot. I can help you with all your weather needs.");
        String mode = null;
        do{
            mode =  askUserAboutMode();
            if(mode.equals("current")){
                askForCity();
            }
        }while(mode!=null);




    }


    public static void askForCity(){
        System.out.println("what place would you like to know the current weather for?");
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()){
            String input = scanner.nextLine().toLowerCase().trim();
            if(input.contains("exit")){
                break;
            }
            EasyOpenAI openAI = new EasyOpenAI();
            input = openAI.getSingleLocationToken(input);
            String weatherString = getWeather(input);
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

    public static String askUserAboutMode(){
        String mode = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("I can either tell you the current weather in a city or " +
                "I can help you plan clothes for a 3 day trip around 5 cities.");
        System.out.println("Which one can I help you with ? Also you can type 'exit' to get out of the program." );
        while(scanner.hasNext()){
            mode = scanner.nextLine().toLowerCase().trim();
            if(mode.contains("current") || mode.contains("live")) {
                mode ="current";
                break;
            }
            else if(mode.contains("plan") || mode.contains("trip")) {
                mode="trip";
                break;
            }
            else if(mode.contains("exit")){
                mode=null;
                break;
            }
            else {
                System.out.println("Sorry I couldn't understand could you be a bit clearer?");
            }
        }
        return mode;
    }
}


