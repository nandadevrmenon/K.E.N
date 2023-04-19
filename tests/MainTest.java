
import org.junit.jupiter.api.Test;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
        System.setIn(new ByteArrayInputStream("current\r\n trip".getBytes()));
        Scanner scanner = new Scanner(System.in);
        String mode = Main.askUserAboutMode(scanner);
        assertEquals("current", mode);

        mode = Main.askUserAboutMode(scanner);

        assertEquals("trip", mode);

    }


    @Test
    public void testGetWeatherSpecificsTemp(){
        System.setIn(new ByteArrayInputStream("temp\r\ntemperature\ncold\nwarm\nhot\nchilly\nfeel".getBytes()));
        Scanner scanner = new Scanner(System.in);
        String specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("temperature", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("temperature", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("temperature", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("temperature", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("temperature", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("temperature", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("temperature", specifics);
    }
    @Test
    public void testGetWeatherSpecificsRain(){
        System.setIn(new ByteArrayInputStream("rain\r\nprecipitation\r\nwet\n\rsnow\n\rpouring\nhail".getBytes()));
        Scanner scanner = new Scanner(System.in);
        String specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("rain", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("rain", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("rain", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("rain", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("rain", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("rain", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("rain", specifics);
    }

    @Test
    public void testGetWeatherSpecificsWind() {
        System.setIn(new ByteArrayInputStream("wind\r\nblowing\n\rstrong\n\rstormy\n\rwindy\n\rwind".getBytes()));
        Scanner scanner = new Scanner(System.in);
        String specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("wind", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("wind", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("wind", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("wind", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("wind", specifics);
    }
    @Test
    public void testGetWeatherSpecificsClouds() {
        System.setIn(new ByteArrayInputStream("clouds\r\nclear\n\rcloudy\n\rclouds\n\rpartly cloudy\n\rpartly sunny\n\rpartly cloudy".getBytes()));
        Scanner scanner = new Scanner(System.in);
        String specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("clouds", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("clouds", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("clouds", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("clouds", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("clouds", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("clouds", specifics);
    }
    @Test
    public void testGetWeatherSpecificsHumidity() {
        System.setIn(new ByteArrayInputStream("humidity\r\nhumid\n\rmoist\n\rwet\n\rwet".getBytes()));
        Scanner scanner = new Scanner(System.in);
        String specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("humidity", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("humidity", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("humidity", specifics);
        specifics = Main.getWeatherSpecifics(scanner);
        assertEquals("humidity", specifics);
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
//    void testAskForCity(){
//        String input = "Cork\n";
//        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        Scanner scanner = new Scanner(System.in);
//
//        String city = Main.askForCity(scanner);
//
//        assertEquals("cork", city);
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