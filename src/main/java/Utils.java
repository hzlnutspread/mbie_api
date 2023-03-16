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
                .uri(new URI(url + statsPath + "?period-ending=2022-07&num-months=1&area-definition=REGC2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(
                getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(
                getResponseThree.body(), Data.class);

        return data;
    }

    public static Data runDistrictQuarterlyRents(
            String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {

        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2022-12&num-months=3&area-definition=TA2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(
                getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(
                getResponseThree.body(), Data.class);

        return data;
    }

    public static Data runWardQuarterlyRents(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2022-12&num-months=3&area-definition=WARD2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(
                getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(
                getResponseThree.body(), Data.class);

        return data;
    }

    public static Data runCbdQuarterlyRents(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2020-12" +
                        "&num-months=1" +
                        "&area-definition=user-defined" +
                        "&include-aggregates=true" +
                        "&user-defined-area-mappings=MyCBD(130200%2C131800%2C131300%2C133300%2C133200%2C134100%2C133700" +
                        "%2C134500%2C135700%2C134800%2C132400%2C132700" +
                        "%2C133400%2C133800%2C135100%2C135300%2C135900%2C136100%2C134300)"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data data = gson.fromJson(getResponseThree.body(), Data.class);

        return data;
    }
}

//130200,131800,131300,133300,133200,134100,133700,134500,135700,134800,132400,132700,133400,133800,135100,135300,135900,136100,134300