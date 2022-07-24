import com.google.gson.Gson;
import java.io.FileInputStream;
import java.lang.module.Configuration;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class RunApi {
    public static void main(String[] args) throws Exception {

        // load properties file
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("src/api.properties");
        defaultProps.load(in);
        in.close();

        String url = defaultProps.getProperty("URL");
        String token = defaultProps.getProperty("TOKEN");
        String areaPath = defaultProps.getProperty("AREA_PATH");
        String statsPath = defaultProps.getProperty("STATS_PATH");

        // First API request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest getAreaCodeReq = HttpRequest.newBuilder()
                .uri(new URI(url + areaPath))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "text/csv")
                .build();
        HttpResponse<String> getAreaCodeResp = httpClient.send(getAreaCodeReq, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data areaCodeData = gson.fromJson(getAreaCodeResp.body(), Data.class);

        ArrayList<String> areaCodes = new ArrayList<>();
        for (Item item : areaCodeData.getItems()) {
            areaCodes.add(item.code);
        }
        System.out.println(areaCodes);
        Thread.sleep(1000);

        // Second API request
        HttpClient httpClientTwo = HttpClient.newHttpClient();
        HttpRequest getRequestTwo = HttpRequest.newBuilder()
                .uri(new URI(url + areaPath + "/" + areaCodes.get(3)))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseTwo = httpClientTwo.send(getRequestTwo, HttpResponse.BodyHandlers.ofString());

        Data dataTwo = gson.fromJson(getResponseTwo.body(), Data.class);

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

        // 3rd get request
        String[] dates = {"2022-01", "2022-02", "2022-03"};

        Map<String, String> params = Map.of( //
                "period-ending", dates[0], //
                "num-months", "12", //
                "area-definition", "TA2019", //
                 "include-aggregates", "false", //
                 "statistics", "mean", //
                "dwelling-type", "BoardingHouse", //
                 "num-bedrooms", "2");

        ArrayList<String> ar = new ArrayList<>();   // Variable scope notes****

        for (Map.Entry<String, String> param : params.entrySet()) {
            String pa = param.getKey() + "=" + param.getValue();
            ar.add(pa);
        }

        String queryParams = String.join("&", ar);


        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?" + queryParams))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Data dataThree = gson.fromJson(getResponseTwo.body(), Data.class);


    }
}
