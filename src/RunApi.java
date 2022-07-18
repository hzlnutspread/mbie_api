import com.google.gson.Gson;
import java.io.FileInputStream;
import java.lang.module.Configuration;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Properties;

public class RunApi {
    public static void main(String[] args) throws Exception {

        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("src/api.properties");
        defaultProps.load(in);
        in.close();

        String url = defaultProps.getProperty("URL");
        String token = defaultProps.getProperty("TOKEN");

        // First API request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest getAreaCodeReq = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "text/csv")
                .build();
        HttpResponse<String> getAreaCodeResp = httpClient.send(getAreaCodeReq, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(getAreaCodeResp.body(), Data.class);

        ArrayList<String> areaCode = new ArrayList<>();
        for (Item item : data.getItems()) {
            areaCode.add(item.code);
        }
        System.out.println(areaCode);
        Thread.sleep(1000);

        // Second API request
        HttpClient httpClientTwo = HttpClient.newHttpClient();
        HttpRequest getRequestTwo = HttpRequest.newBuilder()
                .uri(new URI(url + "/" + areaCode.get(9)))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseTwo = httpClientTwo.send(getRequestTwo, HttpResponse.BodyHandlers.ofString());

        DataTwo dataTwo = gson.fromJson(getResponseTwo.body(), DataTwo.class);

        ArrayList<String> codeArrayTwo = new ArrayList<>();
        for (Item item : dataTwo.getItems()) {
            codeArrayTwo.add(item.code);
        }
        System.out.println(codeArrayTwo);

        ArrayList<String> labelArray = new ArrayList<>();
        for (Item item : dataTwo.getItems()) {
            labelArray.add(item.label);
        }
        System.out.println(labelArray);
    }
}
