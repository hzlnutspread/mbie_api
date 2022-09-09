import com.google.gson.Gson;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Properties;


public class RunStatisticsQuarterly {
    public static void main(String[] args) throws Exception {

        // -------------------------------------- Load Properties File --------------------------------------
        String[] propertiesData = getPropertiesData();

        // -------------------------------------- Run API request --------------------------------------
        System.out.println("Running Statistics API request");
        Data data = runStatistics(propertiesData[0], propertiesData[3], propertiesData[1]);

        // -------------------------------------- Write to CSV --------------------------------------
        createCsv(data);


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

    public static Data runStatistics(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2021-06&num-months=3&area-definition=TA2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data dataThree = gson.fromJson(getResponseThree.body(), Data.class);

        return dataThree;
    }

    public static void createCsv(Data data) throws IOException {

        String[] regionList = {"Whangarei District", "Hamilton City", "Tauranga City", "Whakatane District",
                "Rotorua District", "Taupo District", "Napier City", "Hastings District", "New Plymouth District",
                "Whanganui District", "Palmerston North City", "Kapiti Coast District", "Porirua City",
                "Upper Hutt City", "Lower Hutt City", "Wellington City", "Masterton District", "Nelson City",
                "Marlborough District", "Waimakariri District", "Christchurch City", "Selwyn District",
                "Ashburton District", "Timaru District", "Queenstown-lakes District", "Dunedin City",
                "Invercargill City", "ALL"};

        File csvFile = new File("test_create_quarterly.csv");
        PrintWriter pwOut = new PrintWriter(csvFile);

//        for (String region : regionList) {
//            System.out.println(region);
//            for (Item statsItem : data.getItems()) {
//               if (statsItem.area.equals(region)) {
//                   if (Objects.equals(statsItem.dwell, "ALL") & Objects.equals(statsItem.nBedrms, "ALL")) {
//                       System.out.println("All property types");
//                       pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
//                   }
//
//                   if (Objects.equals(statsItem.dwell, "Apartment") & Objects.equals(statsItem.nBedrms, 1.0)) {
//                       System.out.println("1 bedroom apartment");
//                       pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
//                   }
//
//                   if (Objects.equals(statsItem.dwell, "Apartment") & Objects.equals(statsItem.nBedrms, 2.0)) {
//                       System.out.println("2 bedroom apartment");
//                       pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
//                   }
//
//                    if (Objects.equals(statsItem.dwell, "House") & Objects.equals(statsItem.nBedrms, 3.0)) {
//                        System.out.println("3 bedroom house");
//                        pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
//                    }
//
//                }
//            }
//        }

        for (Item statsItem : data.getItems()) {
            if (statsItem.area.equals("ALL")) {
                pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);

            }
        }
        pwOut.close();
    }
}