import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Stack;

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
        messages = new Stack<>();
    }

    private void addMessage(String role, String content) {
        messages.add(new JSONObject().put("role", role).put("content", content));
    }

    public String getSingleLocationToken(String message) {
        addMessage("system","I am an NLP that returns city tokens in the format token,token,token,... " +
                "I will only return what is required and I'm not supposed to give any context or explanation as to why I returned that." +
                " Also I will not use any periods in the outputs I give. Also I will treat single words as tokens as well" +
                "If there are no cities I will return a token 'null'."); // gaslight the bot into thinking it's a location NLP

        addMessage("user",  "Give me only city tokens in the text '"+message+"'");
        NLPSend();
        String output = Receive();
        if(output.equals("null")) {
            System.out.println("Sorry I didn't catch a location in your input. Please try again.");
            return null;
        }
        if(output.indexOf(",")!=-1){
            output=output.substring(0,output.indexOf(","));
        }
        messages.clear();
        return output;
    }


    private void NLPSend() { // Send request to API

        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 1000);
        requestBody.put("messages", messages); // Send messages
        requestBody.put("temperature", 0.1); // Set temperature to 0.1 // makes AI more predictable
        requestBody.put("top_p", 0.1); // Set top_p to 0.1      //makes AI more likely to give the same answer every time.
        StringEntity entity = new StringEntity(requestBody.toString());
        httpPost.setEntity(entity);
    }

    private void ItinerarySend() { // Send request to API

        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 1000);
        requestBody.put("messages", messages); // Send messages

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
        String responseString = null;
        try {
            responseString = EntityUtils.toString(responseEntity);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }


        responseObject = new JSONObject(responseString);
        String content = responseObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        return content;
    }
}
