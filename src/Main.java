import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Main extends Application {

    public AnchorPane welcomeWindow = new AnchorPane();
    public TextField messageBar = new TextField();
    public TextArea textArea = new TextArea();
    public String mode = null;
    public ArrayList<String> m_locations = null;
    public LocalDate m_startDate = null;
    public PrintStream ps;

    public static void main(String[] args) {
        launch(args);
    }

    public static String askUserAboutMode(Scanner scanner) {
        String mode = null;

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

    public static void askForCity(String text) {        //Live Weather Mode
        EasyOpenAI openAI = new EasyOpenAI();
        String input = text.toLowerCase().trim();     //makes the input all lowercase and removes leading and trialing whitespace

        input = openAI.getSingleCityToken(input);   //returns only one location token out of whatever input the user gave us
        String weatherString = getWeather(input);          //we get the weather for that location using the weather API

        if (!weatherString.isEmpty()) {                       //if the weather String actually has a value we print it out
            System.out.println("The weather there is " + "\r\n" + weatherString);
            System.out.println("Would you like to know the weather at another place? If not, type 'exit'.");
        }
    }

    public static void getWeatherForTrip(String input) {         //trip Planning Mode
        System.out.println("You are now in Trip-Planning Mode. Type 'exit' if you want to exit this mode.");
        System.out.println("enter the names of 5 cities that you are going travel to within 3 days ");
        ArrayList<String> locations = get5CitiesFromUser(input);      //hold the 5 cities that the user will travel to
        LocalDate startDate = getStartDateFromUser(input, locations);      //gets a valid start date from user
        System.out.println("ok so the start date is :" + startDate.toString());
        System.out.println("So the weather at those places for the trip is:");
        ArrayList<WeatherForecast> tripForecast = EasyWeather.getTripForecast(locations, startDate);         //gets the required forecasts
        ArrayList<String> prettyFCs = getPrettyForecasts(tripForecast, locations);// gets and prints formatted weather forecasts
        printClothRecommendations(prettyFCs, locations);

    }

    public static ArrayList<String> get5CitiesFromUser(String input) {
        EasyOpenAI openAI = new EasyOpenAI();
        ArrayList<String> locations = new ArrayList<>();
        input = input.toLowerCase().trim();     //makes the input all lowercase and removes leading and trialing whitespace

        if (input.contains("clear")) {       //if input is clear it clears the already registered locations
            System.out.println("Enter the names of 5 cities that you going to travel two within a three day time period.");
            locations.clear();
        }
        ArrayList<String> newLocations = openAI.getAllCityTokens(input);     //we use open AI to get the location names from user input
        if (newLocations == null) {             //if there are no location names in the input we get the user to try again
            System.out.println("I didn't catch a location in your input. Please type in 5 cities.");
        }
        int min = Math.min(5 - locations.size(), newLocations.size());        //since we can only add as many locations are provided or as many it takes to fill 5 slots
        for (int i = 0; i < min; i++) {                  //then we add those locations to locations array
            locations.add(newLocations.get(i));
        }
        if (locations.size() == 5) {                    //if we have 5 locations we display a sample schedule
            // and ask the user to confirm it, so we can provide relatively more accurate info
            printSchedule(locations);

            if (input.toLowerCase().trim().contains("clear")) {      //if the user inputs 'clear we clear all locations and try again
                System.out.println("Enter the names of 5 cities that you going to travel two within a three day time period.");
                locations.clear();
            } else {            //else we can continue
                System.out.println("Great. Let me quickly fetch the weather forecast in those cities.");
            }
        } else {                    //if we don't have 5 locations yet, we ask the user to enter more locations
            System.out.println("You have entered " + locations.size() + " locations which are " + locations);
            System.out.println("Enter " + (5 - locations.size()) + " locations more. Or type in 'clear' to start over.");
        }

        return locations;
    }

    //I made this public so that i can use it in the test class
    public static LocalDate getStartDateFromUser(String in, ArrayList<String> locations) {
        System.out.println("When are you starting the trip?( Accepted formats: today,tomorrow,day after tomorrow,dd/MM/YYYY)");
        System.out.println("(Note: The API we use only allows a 5 day forecast so your trip has to start between today and the day after tomorrow.)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");        //create a datetimeformatter

        String input = in.toLowerCase().trim();     //we clean input
        if (input.contains("now") || input.contains("today")) {             //if input contains now or today then we consider that the requested date is today
            if (LocalTime.now().isAfter(LocalTime.of(18, 0, 0, 0))) {        //if the time for current day is part 6pm then we the API settings we use don't allow us to access the forcast for the end of the 5th day. So we can only start the trip the next day.
                System.out.println("The day is almost over. You can use the live weather to check today's weather \n and then we can plan the trip for you starting tomorrow.");
                return LocalDate.now().plusDays(1);
            }
            if (LocalTime.now().isAfter(LocalTime.of(9, 00, 00, 0))) {            //if the time is after 9 then we assum that only one location can be visited one the first day.
                System.out.println("Since your starting the trip now, I am assuming the schedule looks like this: ");
                System.out.println("Day one : " + locations.get(0));
                System.out.println("Day two : " + locations.get(1) + " and " + locations.get(2));
                System.out.println("Day three : " + locations.get(3) + " and " + locations.get(4));
            }
            return LocalDate.now();
        } else if (input.contains("tomorrow")) {      //if input contains tomorrow
            if (input.contains("day after")) {          //and it contains 'day after' we return the object for the day after tomorrow
                return LocalDate.now().plusDays(2);
            }
            return LocalDate.now().plusDays(1);
        } else if (input.contains("day after")) {
            return LocalDate.now().plusDays(2);
        } else if (validDate(input, formatter)) {          //if date is in forat dd/mm/yyyy but the date is outside bounds we say that to the user
            LocalDate startDate = LocalDate.parse(input, formatter);
            if (startDate.isAfter(LocalDate.now().plusDays(2)) || (startDate.isBefore(LocalDate.now()))) {
                System.out.println("That date is outside the bounds for which I can provide you a forecast. Please try again when the date comes closer.");
            }
            return startDate;
        } else {      //else it means tha the dat is not ina ny of the formats we accept
            System.out.println("The date you have entered is not in a format I can understand. Please try to be clearer.");
        }
        return null;
    }

    public static ArrayList<String> getPrettyForecasts(ArrayList<WeatherForecast> fcs, ArrayList<String> locations) {  //returns string array of pretty formatted forecasts
        ArrayList<String> prettyFCs = new ArrayList<String>();
        for (int i = 0; i < fcs.size(); i++) {        //for each ForeCast object, get the pretty forecast and add it into array
            String prettyFC = EasyWeather.getPrettyForecast(fcs.get(i), locations.get(i));
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

    public static void printClothRecommendations(ArrayList<String> messages, ArrayList<String> locations) {
        EasyOpenAI openAI = new EasyOpenAI();                 //for each for pretyForecast, we use OpenAI to print out clcothing recommendations
        for (int i = 0; i < messages.size(); i++) {
            System.out.println();
            System.out.println("Getting cloth recommendations for : " + locations.get(i) + ". Please Wait...");
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

    public static void printSchedule(ArrayList<String> locations) {      //prints out trip schedule
        System.out.println("So I'm assuming that time line for the trip is as follows");
        System.out.println("Day one : " + locations.get(0) + " and " + locations.get(1));
        System.out.println("Day two : " + locations.get(2) + " and " + locations.get(3));
        System.out.println("Day three : " + locations.get(4));
        System.out.println("If you want to changes these locations type 'clear' and add locations again.Or type 'continue' to get on with the next step.");
    }

    public static boolean validDate(String input, DateTimeFormatter formatter) {     //checks if the dat is in a given format
        try {
            LocalDate.parse(input, formatter);       //tries to parse date
        } catch (DateTimeParseException dtpe) {
            return false;           //if failed return false;
        }
        return true;
    }

    public static String capitaliseFirst(String str) {
        String result = str.substring(0, 1).toUpperCase();
        return result + str.substring(1);
    }

    @Override
    public void start(Stage stage) {
        Parent root;

        try { // load the UI from the FXML file
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UI.fxml")));
        } catch (IOException e) { // if the FXML file is not found or null, throw an exception
            throw new RuntimeException(e);

        }

        // load icon
        stage.getIcons().add(new Image("image.png"));
        // load style
        root.getStylesheets().add("style.css");
        // set the title of the window
        stage.setTitle("Weather App");
        // set the scene of the window
        stage.setScene(new javafx.scene.Scene(root));
        // show the window
        stage.show();
    }

    @FXML
    private void onClick(ActionEvent event) {
        event.consume();
        // hide welcomeWindow when button click
        if (welcomeWindow.isVisible() && !messageBar.getText().isEmpty()) {
            welcomeWindow.setVisible(false);
        }


        System.setOut(ps);
        System.setErr(ps);

        // get user input
        String input = messageBar.getText().toLowerCase().trim();
        System.out.println("User input: " + input);
        // clear the messageBar
        messageBar.clear();

        if (mode == null) {
            mode = input;     //gets the trimmed and lowercase input
            if (mode.contains("current") || mode.contains("live")) {     //if the input contains the words live or current , we assume they
                // want the current weather at a location
                mode = "current";
            } else if (mode.contains("plan") || mode.contains("trip")) {       //else if the input contains the words plan or trip, we assume they want to plan a trip
                mode = "trip";
            } else if (mode.contains("exit")) {         //if the user wants to exit we break out
                Platform.exit();
            } else {          //if input contains none of those keywords, we  ask for clearer input
                System.out.println("Sorry I couldn't understand could you be a bit clearer?");
            }
        }

        if (mode.equals("current")) {
            if (input.contains("current") || input.contains("live")) {
                askForCity(input);
            } else if (input.contains("exit")) {
                Platform.exit();
            } else {
                System.out.println("Sorry I couldn't understand could you be a bit clearer?");
            }
        } else if (mode.equals("trip")) {
            if (input.contains("plan") || input.contains("trip")) {
                System.out.println("You are now in Trip-Planning Mode. Type 'exit' if you want to exit this mode.");
                System.out.println("enter the names of 5 cities that you are going travel to within 3 days ");

                if (!input.isEmpty()) {
                    if (m_locations == null) {
                        System.out.println("Enter the names of 5 cities that you going to travel two within a three day time period.");
                        m_locations = get5CitiesFromUser(input);
                    } //hold the 5 cities that the user will travel to

                    if (m_startDate == null) {
                        System.out.println("When are you starting the trip? (Please enter in the format of dd-mm-yyyy)");
                        m_startDate = getStartDateFromUser(input, m_locations);      //gets a valid start date from user
                    }

                    System.out.println("ok so the start date is :" + m_startDate.toString());
                    System.out.println("So the weather at those places for the trip is:");

                    if (m_startDate != null && m_locations != null) {
                        ArrayList<WeatherForecast> tripForecast = EasyWeather.getTripForecast(m_locations, m_startDate);         //gets the required forecasts
                        ArrayList<String> prettyFCs = getPrettyForecasts(tripForecast, m_locations);// gets and prints formatted weather forecasts

                        printClothRecommendations(prettyFCs, m_locations);
                    }
                }

            } else if (input.contains("exit")) {
                Platform.exit();
            } else {
                System.out.println("Sorry I couldn't understand could you be a bit clearer?");
            }
        }

    }

    public void initialize() {
        ps = new PrintStream(new Console(textArea));
    }

    public class Console extends OutputStream {
        private final TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char) b));
        }
    }
}


