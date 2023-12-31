import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringJoiner;

public class EasyOpenAI {
    private static final String API_TOKEN = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final CloseableHttpClient httpClient;
    private JSONObject requestBody;
    private Stack<JSONObject> messages;
    private JSONObject responseObject = null;
    private HttpPost httpPost = null;


    public EasyOpenAI() { // Initialize request body
        httpClient = HttpClients.createDefault();
        httpPost = new HttpPost(API_URL);
        httpPost.setHeader("Authorization", "Bearer " + API_TOKEN);
        httpPost.setHeader("Content-Type", "application/json");

        requestBody = new JSONObject();
        messages = new Stack<>(); //the stack of messages that is passed into the OpenAI API
    }

    private void addMessage(String role, String content) {
        messages.add(new JSONObject().put("role", role).put("content", content));
    }

    public String getSingleCityToken(String message) {
        addMessage("system","I am an NLP that returns city tokens in the format token,token,token,... " +
                "I will only return what is required and I'm not supposed to give any context or explanation as to why I returned that." +
                " Also I will not use any periods in the outputs I give. Also I will treat single words as tokens as well" +
                "If there are no cities I will return a token 'null'."); // gaslight the bot into thinking it's an NLP that returns location tokens

        addMessage("user",  "Give me only city tokens in the text '"+message+"'");      //gets chatgpt to return only city tokens from user input text
        NLPSend();
        String output = Receive();
        if(output.contains("null")) {     //the API will return 'null' if there was no location token in the input
            System.out.println("Sorry I didn't catch a location in your input. Please try again.");
            return null;
        }
        if(output.contains(",")){        //if there is more than one location we return the first one only
            System.out.println("You have entered a few locations but I can only give you the weather for one at a time.");
            output=output.substring(0,output.indexOf(","));
        }
        messages.clear();
        if(output.contains("dublin")){      //correct edge cases because the API favours cities in the USA with the same name
            return "dublin,ie";
        }
        if(output.contains("rome")){
            return "rome,IT";
        }

        return output;
    }

    public ArrayList<String> getAllCityTokens(String message){
        addMessage("system","I am an NLP that returns city tokens in the format token,token,token,... " +
                "I will only return what is required and I'm not supposed to give any context or explanation as to why I returned that." +
                " I will not use any periods in the outputs I give.I will not return any country names.  Also I will treat single words as tokens as well" +
                "If there are no cities I will return a token 'null'."); // gaslight the bot into thinking it's an NLP that returns location tokens

        addMessage("user",  "Give me only city tokens in the text '"+message+"'");      //gets chatgpt to return only city tokens from user input text
        NLPSend();
        String tokens = Receive();
        if(tokens.contains("null")) {     //the API will return 'null' if there was no location token in the input
            return null;
        }
        messages.clear();
        String[] tokenArray = tokens.split(",");
        ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(tokenArray));
        for(int i = 0;i<tokenList.size();i++){
            if(tokenList.get(i).contains("dublin")){
                tokenList.set(i,"dublin,ie");
            }
        }
        return tokenList;
    }

    public String getClothRecommendations(String weather){
        addMessage("system","I am a chatbot that tells users what kind of clothes to weather for certain weather conditions." + "My answers are about 60 words long."); // gaslight the bot into thinking it's made to recommend cloths for certain weather conditions
        weather = weather.replace("°"," degrees ");     //because ° is not a part of utf-8 which is the only input OpenAI can take
        if(weather.contains("Sorry I do not have data for that location. Maybe it is misspelled?"))
            return"Sorry, I can't recommend clothes for a null weather.";
        addMessage("user",  "What should I wear if the weather is as follows :'"+weather+"'.");   //gets chatgpt to make recommedations on what clothes to wear.
        NormalSend();
        String recommendation = Receive();
        messages.clear();

        //this code word wraps every 15 words into a single line
        StringJoiner stringJoiner = new StringJoiner("\n");
        String [] tokens = recommendation.split(" ");
        String str =tokens[0];
        for(int  i = 1; i <tokens.length;i++){
            str=str+" "+tokens[i];
            if(i%15==0){
                stringJoiner.add(str);
                str="";
            }
        }
        stringJoiner.add(str);
        String wrappedText = stringJoiner.toString();
        return wrappedText;
    }

    private void NLPSend() { // Send request to API

        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 1000);        //max tokens that can be returns by the API = 1000
        requestBody.put("messages", messages); // Send messages
        requestBody.put("temperature", 0.1); // Set temperature to 0.1 // makes AI more predictable
        requestBody.put("top_p", 0.1); // Set top_p to 0.1      //makes AI more likely to give the same answer every time.
        StringEntity entity = new StringEntity(requestBody.toString());
        httpPost.setEntity(entity);
    }

    private void NormalSend() { // Send request to API

        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 1000);
        requestBody.put("messages", messages); // Send messages
        requestBody.put("temperature", 0.5); // Set temperature to 0.1 // makes AI more predictable
        requestBody.put("top_p", 0.5); // Set top_p to 0.1      //makes AI more likely to give the same answer every time.

        StringEntity entity = new StringEntity(requestBody.toString());
        httpPost.setEntity(entity);
    }



    private String Receive() {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpEntity responseEntity = response.getEntity();
        String responseString;
        try {
            responseString = EntityUtils.toString(responseEntity);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        String content = "";
        responseObject = new JSONObject(responseString);
        try{
             content = responseObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        }catch(JSONException je){       //this would indicate that the user is sending too many requests in a short amount of time we prompt the user to wait.
            System.out.println("Please slow down. I can only handle a certain number of requests in a minute. Please wait for 20 seconds. ");
            return "null";
        }

        return content;
    }
}
