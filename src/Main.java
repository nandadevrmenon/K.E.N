
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hi, I'm WeatherBot. I can help you with all your weather needs.");
        String mode = null;
        Scanner scanner = new Scanner(System.in);
        do {
            mode = askUserAboutMode(scanner);
            if (mode == null) break;
            if (mode.equals("current")) {
                askForCity(scanner);
            }
            if (mode.equals("trip")) {
                getWeatherForTrip(scanner);
            }
        } while (mode != null);
//        ArrayList<WeatherForecast> weathers = EasyWeather.getThreeDayForecast("mumbai",LocalDate.now());



    }

    public static String askUserAboutMode(Scanner scanner) {
        String mode = null;
        System.out.println("I can either tell you the current weather in a city or " +
                "I can help you plan clothes for a 3 day trip around 5 cities.");
        System.out.println("Which one can I help you with ? Also you can type 'exit' to get out of the program.");
        while (scanner.hasNext()) {
            mode = scanner.nextLine().toLowerCase().trim();     //gets the trimmed and lowercase input
            if (mode.contains("current") || mode.contains("live")) {     //if the input conatins the words live or current , we assume they
                // want the current weather at a location
                mode = "current";
                break;
            } else if (mode.contains("plan") || mode.contains("trip")) {       //else if the input contains the words plan or trip, we assume they want to plan a trip
                mode = "trip";
                break;
            } else if (mode.contains("exit")) {         //if the user wants to exit we break out
                mode = null;
                break;
            } else {          //if input contains none of those keywords, we  ask for clearer input
                System.out.println("Sorry I couldn't understand could you be a bit clearer?");
            }
        }
        return mode;
    }

    public static void askForCity(Scanner scanner) {
        System.out.println("what place would you like to know the current weather for?");
        EasyOpenAI openAI = new EasyOpenAI();
        while (scanner.hasNext()) {
            String input = scanner.nextLine().toLowerCase().trim();     //makes the input all lowercase and removes leading and trialing whitespace
            if (input.contains("exit")) {
                break;
            }
            input = openAI.getSingleCityToken(input);   //returns only one location token out of whatever input the user gave us
            String weatherString = getWeather(input);          //we get the weather for that location using the weather API
            if(weatherString.equals("exit")){
                break;
            }
            else if (!weatherString.isEmpty()) {                       //if the weather String actually has a value we print it out
                System.out.println("The weather there is " + "\r\n" + weatherString);
                System.out.println("Would you like to know the weather at another place? If not, type 'exit'.");
            }

        }
    }

    public static String getWeather(String city) {
        EasyWeather easyWeather = new EasyWeather();
        Weather weather = null;
        try {
            weather = easyWeather.getWeatherByCity(city);
        } catch (IllegalArgumentException iae) {       //if there is an illegal argument exception it probably means
            // there is a spelling mistake in the location or the location does not exist
            System.out.println(iae.getMessage());
            System.out.println("Try another Location please");
            return "";
        }
        if (weather == null) {
            System.out.println("A problem occurred while fetching the data.Please try again later");       //weather is null when we have a connectivity issue
            return "exit";
        }
        return weather.toString();

    }




    public static void getWeatherForTrip(Scanner scanner) {
        System.out.println("You are now in Trip-Planning Mode. Type 'exit' if you want to exit this mode.");
        System.out.println("enter the names of 5 cities that you are going travel to within 3 days ");
      ArrayList<String> locations = get5CitiesFromUser(scanner);
        LocalDate startDate = getStartDateFromUser(scanner,locations);
        System.out.println("ok so the start date is :"+startDate.toString());

    }

    public static ArrayList<String> get5CitiesFromUser(Scanner scanner) {
        EasyOpenAI openAI = new EasyOpenAI();
        ArrayList<String> locations = new ArrayList<String>();
        while (scanner.hasNext()) {
            String input = scanner.nextLine().toLowerCase().trim();     //makes the input all lowercase and removes leading and trialing whitespace
            if (input.contains("exit")) {
                break;
            } else if (input.contains("clear")) {       //if input is clear it clears the already registered locations
                System.out.println("Enter the names of 5 cities that you going to travel two within a three day time period.");
                locations.clear();
                continue;
            }
            ArrayList<String> newLocations = openAI.getAllCityTokens(input);     //we use open AI to get the location names from user input
            if (newLocations == null) {             //if there are no location names in the input we get the user to try again
                System.out.println("I didn't catch a location in your input. Please type in 5 cities.");
                continue;
            }
            int min = Math.min(5 - locations.size(), newLocations.size());        //since we can only add as many locations are provided or as many it takes to fill 5 slots
            for (int i = 0; i < min; i++) {                  //then we add those locations to locations array
                locations.add(newLocations.get(i));
            }
            if (locations.size() == 5) {                    //if we have 5 locations we display a sample schedule
                // and ask the user to confirm it, so we can provide relatively more accurate info
                System.out.println("So I'm assuming that time line for the trip is as follows");
                System.out.println("Day one : " + locations.get(0) + " and " + locations.get(1));
                System.out.println("Day two : " + locations.get(2) + " and " + locations.get(3));
                System.out.println("Day three : " + locations.get(4));
                System.out.println("If you want to changes these locations type 'clear' and add locations again.Or type 'continue' to get on with the next step.");
                if (scanner.nextLine().toLowerCase().trim().contains("clear")) {      //if the user inputs 'clear we clear all locations and try again
                    System.out.println("Enter the names of 5 cities that you going to travel two within a three day time period.");
                    locations.clear();
                } else {            //else we can continue
                    System.out.println("Great. Let me quickly fetch the weather forecast in those cities.");
                    break;
                }
            } else {                    //if we don't have 5 locations yet, we ask the user to enter more locations
                System.out.println("You have entered " + locations.size() + " locations which are " + locations.toString());
                System.out.println("Enter " + (5 - locations.size()) + " locations more. Or type in 'clear' to start over.");
            }
        }
        return locations;
    }

    private static LocalDate getStartDateFromUser(Scanner scanner, ArrayList<String> locations) {
        System.out.println("When are you starting the trip?( Accepted formats: today,tomorrow,day after tomorrow,dd/MM/YYYY)");
        System.out.println("(Note: The API we use only allows a 5 day forecast so your trip has to start between today and the day after tomorrow.)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while(scanner.hasNext()) {
            String input = scanner.nextLine().toLowerCase().trim();
            if(input.isEmpty()) continue;
            if (input.contains("now") || input.contains("today")) {
                if(LocalTime.now().isAfter(LocalTime.of(9, 00, 00, 0))){
                    System.out.println("Since your starting the trip now, I am assuming the schedule looks like this: ");
                    System.out.println("Day one : " + locations.get(0));
                    System.out.println("Day two : " + locations.get(1) + " and " + locations.get(2));
                    System.out.println("Day three : "+locations.get(3) + " and " + locations.get(4));
                }
                return LocalDate.now();
            }
            else if (input.contains("tomorrow")) {
                if (input.contains("day after")) {
                    return LocalDate.now().plusDays(2);
                }
                return LocalDate.now().plusDays(1);
            }
            else if (validDate(input,formatter)) {
                LocalDate startDate = LocalDate.parse(input, formatter);
                if(startDate.isAfter(LocalDate.now().plusDays(2)) || (startDate.isBefore(LocalDate.now()))){
                    System.out.println("That date is outside the bounds for which I can provide you a forecast. Please try again when the date comes closer.");
                    continue;
                }
                return startDate;
            }
            else {
                System.out.println("The date you have entered is not in a format I can understand. Please try to be clearer.");
            }
        }
       return null;
    }




    public static boolean validDate(String input, DateTimeFormatter formatter){
        try{
            LocalDate.parse(input,formatter);
        }catch(DateTimeParseException dtpe){
            return false;
        }
        return true;
    }
}


