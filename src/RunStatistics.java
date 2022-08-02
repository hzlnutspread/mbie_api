import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Properties;


public class RunStatistics {
    public static void main(String[] args) throws Exception {

        // -------------------------------------- Load Properties File --------------------------------------
        String[] propertiesData = getPropertiesData();

        // -------------------------------------- Run API request --------------------------------------
        System.out.println("Running Statistics API request");
        runStatistics(propertiesData[0], propertiesData[3], propertiesData[1]);

    }

    public static String[] getPropertiesData() throws IOException {
        String[] propertiesData = new String[4];
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("src/api.properties");
        defaultProps.load(in);
        in.close();

        String url = defaultProps.getProperty("URL");
        String token = defaultProps.getProperty("TOKEN");
        String areaPath = defaultProps.getProperty("AREA_PATH");
        String statsPath = defaultProps.getProperty("STATS_PATH");

        propertiesData[0] = url;
        propertiesData[1] = token;
        propertiesData[2] = areaPath;
        propertiesData[3] = statsPath;

        return propertiesData;
    }

    public static void runStatistics(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2022-01&num-months=3&area-definition=TA2019&include-aggregates=false"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data dataThree = gson.fromJson(getResponseThree.body(), Data.class);

        ArrayList<String> dwellingArray = new ArrayList<>();
        for (Item statsItem : dataThree.getItems()) {
            dwellingArray.add(statsItem.dwell);
        }
        System.out.println(dwellingArray);

        ArrayList<String> areaArray = new ArrayList<>();
        for (Item statsItem : dataThree.getItems()) {
            areaArray.add(statsItem.area);
        }
        System.out.println(areaArray);

        ArrayList<Integer> medArray = new ArrayList<>();
        for (Item statsItem : dataThree.getItems()) {
            medArray.add(statsItem.med);
        }
        System.out.println(medArray);

    }
}
