
import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hi, I'm WeatherBot, I can tell you the current weather of any place in the world."+
                "\r\n"+"Which city's weather would you like to know? ");

        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()){
            if(scanner.next().toLowerCase().trim().equals("exit")){
                break;
            }
            askCity(scanner);
        }
    }

    public static void askCity(Scanner scanner){
        String city = scanner.next().toLowerCase().trim();

    }
}


