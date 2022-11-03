import com.google.gson.Gson;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public abstract class Utils {

    static String url = "https://api.business.govt.nz/services/v1/tenancy-services/market-rent";
    static String areaPath = "/area-definitions";
    static String statsPath = "/statistics";

    public static String[] getPropertiesData() throws IOException {
        String[] propertiesData = new String[4];
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("src/api.properties");
        defaultProps.load(in);
        in.close();

        String token = defaultProps.getProperty("TOKEN");

        propertiesData[0] = url;
        propertiesData[1] = token;
        propertiesData[2] = areaPath;
        propertiesData[3] = statsPath;

        return propertiesData;
    }

    public static Data runMonthlyRequest(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {

        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2022-06&num-months=1&area-definition=REGC2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(getResponseThree.body(), Data.class);

        return data;
    }

    public static Data runQuarterlyRequest(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2022-06&num-months=3&area-definition=TA2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(getResponseThree.body(), Data.class);

        return data;
    }

    public static Data runQuarterlyAucklandRequest(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2022-06&num-months=3&area-definition=WARD2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(getResponseThree.body(), Data.class);

        return data;
    }
}