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
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/completions");
        String OPENAI_API_TOKEN = "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";

        httpPost.setHeader("Authorization", "Bearer " + OPENAI_API_TOKEN);
        httpPost.setHeader("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", "Speak Human you mf robot");
        requestBody.put("max_tokens", 200);

        StringEntity entity = new StringEntity(requestBody.toString());
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity);


        JSONObject responseObject = new JSONObject(responseString);

        System.out.println(responseObject.toString());


        //EasyWeather weather = new EasyWeather();
        //System.out.println(weather.getWeatherByCity("mumbai"));
    }
}
