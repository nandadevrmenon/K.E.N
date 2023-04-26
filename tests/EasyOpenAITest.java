import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

class EasyOpenAITest {
    @Test
    public void testgetSingleCityToken(){
        String input = "I wanna go to new york and manhattan";
        EasyOpenAI openai = new EasyOpenAI();
        String output = openai.getSingleCityToken(input);
        assertTrue(output.equals("new york"));
        input =" I wanna go to mumbai and dublin.Here's some random text as well.";
        output = openai.getSingleCityToken(input);
        assertTrue(output.equals("mumbai"));
        input ="Whats the weather in dublin right now?";
        output = openai.getSingleCityToken(input);
        assertTrue(output.equals("dublin,ie"));
    }

    @Test
    public void testGetgetAllCityTokens(){
        String input = "I wanna go to new york and manhattan";
        EasyOpenAI openai = new EasyOpenAI();
        ArrayList<String> output = openai.getAllCityTokens(input);
        assertTrue(output.equals(new ArrayList<String>(Arrays.asList("new york","manhattan"))));
        input =" I wanna go to mumbai and dublin and pune and goa .Here's some random text as well.";
        output = openai.getAllCityTokens(input);
        assertTrue(output.equals(new ArrayList<String>(Arrays.asList("mumbai", "dublin,ie","pune","goa"))));
        input ="Whats the weather in dublin cork swords santry tallaght new jersey right now?";
        output = openai.getAllCityTokens(input);
        assertTrue(output.equals(new ArrayList<String>(Arrays.asList("dublin,ie", "cork","swords","santry","tallaght"))));
    }


    public void testGetClothRecommednations(){

    }
}