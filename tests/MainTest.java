import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testAskUserAboutModeCurrent1() {
        // Simulate user input "live\n"
        ByteArrayInputStream in = new ByteArrayInputStream("live\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        String mode = Main.askUserAboutMode(scanner);
        assertEquals("current", mode);
    }

    @Test
    public void testAskUserAboutModeCurrent2() {
        // Simulate user input "live\n"
        ByteArrayInputStream in = new ByteArrayInputStream("current\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        String mode = Main.askUserAboutMode(scanner);
        assertEquals("current", mode);
    }

    @Test
    public void testAskUserAboutModeTrip1(){
        // Simulate user input "live\n"
        ByteArrayInputStream in = new ByteArrayInputStream("plan\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        String mode = Main.askUserAboutMode(scanner);
        assertEquals("trip", mode);
    }

    @Test
    public void testAskUserAboutModeTrip2(){
        // Simulate user input "live\n"
        ByteArrayInputStream in = new ByteArrayInputStream("trip\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        String mode = Main.askUserAboutMode(scanner);
        assertEquals("trip", mode);
    }
    @Test
    public void testAskUserAboutModeExit(){
        // Simulate user input "live\n"
        ByteArrayInputStream in = new ByteArrayInputStream("exit\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        String mode = Main.askUserAboutMode(scanner);
        assertEquals(null, mode);
    }

//    @Test
//    public void testAskUserAboutMode_invalidInput() {
//        String input = "something";
//        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        Scanner scanner = new Scanner(System.in);
//        String result = Main.askUserAboutMode(scanner);
//        String expected ="Sorry I couldn't understand could you be a bit clearer?";
//        System.out.println(result);
//        assertTrue(result.equals(expected));
//    }

    @Test
    public void testGetWeather() {
        // Test with valid city
        String weatherInfo = Main.getWeather("New York");
        assertNotNull(weatherInfo);
        assertTrue(weatherInfo.contains("New York"));
        // Test with invalid city
        weatherInfo = Main.getWeather("Some Invalid City");
        assertEquals("", weatherInfo);

        // Test with null city
        weatherInfo = Main.getWeather(null);
        assertEquals("", weatherInfo);
    }
/*
    @Test
    public void testGet5CitiesFromUser() {//This one passes depending if the chatGPT times out or not
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("New York\nParis\nTokyo\ndublin\n\nexit\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Call the method to be tested
        ArrayList<String> locations = Main.get5CitiesFromUser(scanner);

        // Test the output
        assertEquals(Arrays.asList("new york", "paris", "tokyo", "dublin"), locations);
    }

      @Test
     void testGetStartDateFromUser() {
        String input = "tomorrow\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ArrayList<String> locations = new ArrayList<>();
        locations.add("London");
        locations.add("Paris");
        locations.add("Berlin");
        locations.add("Rome");
        locations.add("Barcelona");

        LocalDate startDate = Main.getStartDateFromUser(scanner, locations);

        assertEquals(LocalDate.now().plusDays(1), startDate);
    }

     */
    @Test
    public void testValidDate() {
        // Arrange
        String validDate = "2023-04-17";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        // Act
        boolean result =Main.validDate(validDate, formatter);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testInvalidDate() {
        // Arrange
        String invalidDate = "2023-04-31"; // April only has 30 days
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        // Act
        boolean result = Main.validDate(invalidDate, formatter);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testInvalidFormat() {
        // Arrange
        String invalidFormat = "17/04/2023";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        // Act
        boolean result = Main.validDate(invalidFormat, formatter);

        // Assert
        assertFalse(result);
    }
    @Test
    public void testCapitaliseFirst() {
        // Arrange
        String input = "hello world";
        String expectedOutput = "Hello world";

        // Act
        String output = Main.capitaliseFirst(input);

        // Assert
        assertEquals(expectedOutput, output);
    }


    @Test
    public void testCapitaliseFirstAllCaps() {
        // Arrange
        String input = "HELLO WORLD";
        String expectedOutput = "HELLO WORLD";

        // Act
        String output = Main.capitaliseFirst(input);

        // Assert
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testCapitaliseFirstSingleLetter() {
        // Arrange
        String input = "a";
        String expectedOutput = "A";

        // Act
        String output = Main.capitaliseFirst(input);

        // Assert
        assertEquals(expectedOutput, output);
    }

//    @Test
//    void testGetWeather() {
//    String input = "London";
//        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//
//    }


}