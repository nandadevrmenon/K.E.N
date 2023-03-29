import com.fasterxml.jackson.databind.util.JSONPObject;
import json.JSONObject;
import netscape.javascript.JSObject;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;


public class Main {
    private static String OPENAI_API_TOKEN = "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";
    public static void main(String[] args) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/engines/davinci-codex/completions");
        httpPost.setHeader("Authorization", "Bearer " + OPENAI_API_TOKEN);
        httpPost.setHeader("Content-Type", "application/json");

        JSONPObject requestBody = new JSONPObject();
        requestBody.put("prompt", "Hello, World!");
        requestBody.put("max_tokens", 5);



        EasyWeather weather = new EasyWeather();
        System.out.println(weather.getWeatherByCity("mumbai"));
    }
}
