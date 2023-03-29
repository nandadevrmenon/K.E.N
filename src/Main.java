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


public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/chat/completions");
        String OPENAI_API_TOKEN = "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";

        httpPost.setHeader("Authorization", "Bearer " + OPENAI_API_TOKEN);
        httpPost.setHeader("Content-Type", "application/json");

        JSONObject[] messages = {
                new JSONObject().put("role", "system").put("content", "You are a great weather chat bot."),
                new JSONObject().put("role", "user").put("content", "Find me 3 destinations with good weather and tell me wich ones I have to visit first. "),
                //new JSONObject().put("role", "user").put("content", "Where was it played?")
        };

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 1000);
        requestBody.put("messages", messages);

        StringEntity entity = new StringEntity(requestBody.toString());
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity);


        JSONObject responseObject = new JSONObject(responseString);

        System.out.println(responseObject.toString());


        //TODO: Parse JSON & Add helper class


        //EasyWeather weather = new EasyWeather();
        //System.out.println(weather.getWeatherByCity("mumbai"));
    }
}
