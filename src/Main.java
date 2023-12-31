import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        //MainUI.launch(MainUI.class, args); The UI doens't work, so we're not using it
        // It would've been too much work to integrate it with our actual system.
        run();
    }

    public static String askUserAboutMode(Scanner scanner) {
        String mode = null;
        System.out.println("""
                I'm WeatherBot. I can help you with all your weather needs.
                I can understand you better if you type in full sentences.
                Also you can type 'exit' to get out of the program.""");


        System.out.println("I have live weather mode and trip planning mode. Which mode would you like to select?");

        while (scanner.hasNext()) {
            mode = scanner.nextLine().toLowerCase().trim();     //gets the trimmed and lowercase input
            if (mode.isEmpty()) continue;
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


    public static void askForCity(Scanner scanner) {        //Live Weather Mode
        System.out.println("what place would you like to know the current weather for?");
        EasyOpenAI openAI = new EasyOpenAI();
        while (scanner.hasNext()) {
            String input = scanner.nextLine().toLowerCase().trim();     //makes the input all lowercase and removes leading and trialing whitespace
            if(input.isEmpty()) continue;
            if (input.contains("exit")) {
                break;
            }
            input = openAI.getSingleCityToken(input);   //returns only one location token out of whatever input the user gave us
            String weatherString = getWeather(input);          //we get the weather for that location using the weather API
            if(weatherString.equals("exit")){
                break;
            } else if (!weatherString.isEmpty()) {                       //if the weather String actually has a value we print it out
                System.out.println("The weather there is " + "\r\n" + weatherString);
                System.out.println("Would you like to know the weather at another place? If not, type 'exit'.");
            }

        }
    }

    //I made this public so that i can use it in the test class
    public static LocalDate getStartDateFromUser(Scanner scanner, ArrayList<String> locations) {
        System.out.println("When are you starting the trip?( Accepted formats: today,tomorrow,day after tomorrow,dd/MM/YYYY)");
        System.out.println("(Note: The API we use only allows a 5 day forecast so your trip has to start between today and the day after tomorrow.)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");        //create a datetimeformatter

        while(scanner.hasNext()) {      //while user inputs date
            String input = scanner.nextLine().toLowerCase().trim();     //we clean input
            if(input.isEmpty()) continue;                                       //if input is empty we just continue and wait till user actually inputs something
            if (input.contains("now") || input.contains("today")) {             //if input contains now or today then we consider that the requested date is today
                if(LocalTime.now().isAfter(LocalTime.of(18,0,0,0))){        //if the time for current day is part 6pm then we the API settings we use don't allow us to access the forcast for the end of the 5th day. So we can only start the trip the next day.
                    System.out.println("The day is almost over. You can use the live weather to check today's weather \n and then we can plan the trip for you starting tomorrow.");
                    return LocalDate.now().plusDays(1);
                }
                if(LocalTime.now().isAfter(LocalTime.of(9, 00, 00, 0))){            //if the time is after 9 then we assum that only one location can be visited one the first day.
                    System.out.println("Since your starting the trip now, I am assuming the schedule looks like this: ");
                    System.out.println("Day one : " + locations.get(0));
                    System.out.println("Day two : " + locations.get(1) + " and " + locations.get(2));
                    System.out.println("Day three : "+locations.get(3) + " and " + locations.get(4));
                }
                return LocalDate.now();
            } else if (input.contains("tomorrow")) {      //if input contains tomorrow
                if (input.contains("day after")) {          //and it contains 'day after' we return the object for the day after tomorrow
                    return LocalDate.now().plusDays(2);
                }
                return LocalDate.now().plusDays(1);
            } else if (input.contains("day after")){
                return LocalDate.now().plusDays(2);
            } else if (validDate(input,formatter)) {          //if date is in forat dd/mm/yyyy but the date is outside bounds we say that to the user
                LocalDate startDate = LocalDate.parse(input, formatter);
                if(startDate.isAfter(LocalDate.now().plusDays(2)) || (startDate.isBefore(LocalDate.now()))){
                    System.out.println("That date is outside the bounds for which I can provide you a forecast. Please try again when the date comes closer.");
                    continue;
                }
                return startDate;
            } else {      //else it means tha the dat is not ina ny of the formats we accept
                System.out.println("The date you have entered is not in a format I can understand. Please try to be clearer.");
            }
        }
        return null;
    }

    public static ArrayList<String> getPrettyForecasts(ArrayList<WeatherForecast> fcs,ArrayList<String> locations){  //returns string array of pretty formatted forecasts
        ArrayList<String> prettyFCs = new ArrayList<String>();
        for (int i = 0 ; i <fcs.size(); i++){        //for each ForeCast object, get the pretty forecast and add it into array
            String prettyFC = EasyWeather.getPrettyForecast(fcs.get(i),locations.get(i));
            prettyFCs.add(prettyFC);
            System.out.println(prettyFC);
        }
        return prettyFCs;
    }

    public static String getWeather(String city) {
        Weather weather = null;
        try {
            weather = EasyWeather.getWeatherByCity(city);
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

    public static void getWeatherForTrip(Scanner scanner) {         //trip Planning Mode
        System.out.println("You are now in Trip-Planning Mode. Type 'exit' if you want to exit this mode.");
        System.out.println("enter the names of 5 cities that you are going travel to within 3 days ");
        ArrayList<String> locations = get5CitiesFromUser(scanner);      //hold the 5 cities that the user will travel to
        LocalDate startDate = getStartDateFromUser(scanner,locations);      //gets a valid start date from user
        System.out.println("ok so the start date is :"+startDate.toString());
        System.out.println("So the weather at those places for the trip is:");
        ArrayList<WeatherForecast> tripForecast = EasyWeather.getTripForecast(locations,startDate);         //gets the required forecasts
        ArrayList<String> prettyFCs =  getPrettyForecasts(tripForecast,locations);// gets and prints formatted weather forecasts
        printClothRecommendations(prettyFCs,locations);

    }

    public static ArrayList<String> get5CitiesFromUser(Scanner scanner) {
        EasyOpenAI openAI = new EasyOpenAI();
        ArrayList<String> locations = new ArrayList<String>();
        while (scanner.hasNext()) {
            String input = scanner.nextLine().toLowerCase().trim();     //makes the input all lowercase and removes leading and trialing whitespace
            if(input.isEmpty()) continue;
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
                printSchedule(locations);
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

    public static void printClothRecommendations(ArrayList<String> messages,ArrayList<String> locations){
        EasyOpenAI openAI = new EasyOpenAI();                 //for each for pretyForecast, we use OpenAI to print out clcothing recommendations
        for(int i = 0 ; i <messages.size(); i++){
            System.out.println();
            System.out.println("Getting cloth recommendations for : "+locations.get(i)+". Please Wait...");
            System.out.println();
            try {
                Thread.sleep(18000);        //we sleep for 18000 ms since OpenAI API has a cooldown of 20 seconds after each request.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(openAI.getClothRecommendations(messages.get(i)));

        }
        System.out.println();
    }


    public static void printSchedule(ArrayList<String> locations){      //prints out trip schedule
        System.out.println("So I'm assuming that time line for the trip is as follows");
        System.out.println("Day one : " + locations.get(0) + " and " + locations.get(1));
        System.out.println("Day two : " + locations.get(2) + " and " + locations.get(3));
        System.out.println("Day three : " + locations.get(4));
        System.out.println("If you want to changes these locations type 'clear' and add locations again.Or type 'continue' to get on with the next step.");
    }


    public static boolean validDate(String input, DateTimeFormatter formatter){     //checks if the dat is in a given format
        try{
            LocalDate.parse(input,formatter);       //tries to parse date
        }catch(DateTimeParseException dtpe){
            return false;           //if failed return false;
        }
        return true;
    }

    public static String capitaliseFirst(String str){
        String result = str.substring(0,1).toUpperCase();
        return result+str.substring(1);
    }

    public static void run() {
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
    }
}


