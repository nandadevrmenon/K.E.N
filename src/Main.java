
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
            if(mode==null) break;
            if(mode.equals("current")){
                askForCity();
            }
            if(mode.equals("trip")){
                //planClothesForTrip();
            }
        }while(mode!=null);




    }


    public static void askForCity(){
        System.out.println("what place would you like to know the current weather for?");
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()){
            String input = scanner.nextLine().toLowerCase().trim();     //makes the input all lowercase and removes leading and trialing whitespace
            if(input.contains("exit")){
                break;
            }
            EasyOpenAI openAI = new EasyOpenAI();
            input = openAI.getSingleLocationToken(input);   //returns only one location token out of whatever input the user gave us
            String weatherString = getWeather(input);          //we get the weather for that location using the weather API
            if(!weatherString.isEmpty()){                       //if the weather String actually has a value we print it out
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
        catch( IllegalArgumentException iae){       //if there is an illegal argument exception it probably means
            // there is a spelling mistake in the location or the location does not exist
            System.out.println(iae.getMessage());
            System.out.println("Try another Location please");
            return "";
        }
        if(weather == null){
            System.out.println("A problem occurred while fetching the data.Please try again later");       //weather is null when we have a connectivity issue
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
            mode = scanner.nextLine().toLowerCase().trim();     //gets the trimmed and lowercase input
            if(mode.contains("current") || mode.contains("live")) {     //if the input conatins the words live or current , we assume they
                // want the current weather at a location
                mode ="current";
                break;
            }
            else if(mode.contains("plan") || mode.contains("trip")) {       //else if the input contains the words plan or trip, we assume they want to plan a trip
                mode="trip";
                break;
            }
            else if(mode.contains("exit")){         //if the user wants to exit we break out
                mode=null;
                break;
            }
            else {          //if input contains none of those keywords, we  ask for clearer input
                System.out.println("Sorry I couldn't understand could you be a bit clearer?");
            }
        }
        return mode;
    }
}


