import com.google.gson.Gson;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;


public class RunStatistics {
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

    //Example urls
    //?period-ending=2022-03&num-months=1&area-definition=REGC2019&include-aggregates=true&dwelling-type=Apartment&area-codes=02
    //?period-ending=2022-03&num-months=1&area-definition=REGC2019&include-aggregates=false
    public static Data runStatistics(String url, String statsPath, String token) throws IOException, InterruptedException, URISyntaxException {

        HttpClient httpClientThree = HttpClient.newHttpClient();
        HttpRequest getRequestThree = HttpRequest.newBuilder()
                .uri(new URI(url + statsPath + "?period-ending=2022-06&num-months=1&area-definition=REGC2019&include-aggregates=true"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseThree = httpClientThree.send(getRequestThree, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Data dataThree = gson.fromJson(getResponseThree.body(), Data.class);


        ArrayList<Integer> medArray = new ArrayList<>();
        ArrayList<String> dwellingArray = new ArrayList<>();
        ArrayList<Object> nbedrmsArray = new ArrayList<>();
        ArrayList<String> areaArray = new ArrayList<>();
        ArrayList<Integer> nlodgedArray = new ArrayList<>();

        for (Item statsItem : dataThree.getItems()) {
            if (Objects.equals(statsItem.dwell, "ALL") & Objects.equals(statsItem.nBedrms, "ALL")) {
                medArray.add(statsItem.med);
                dwellingArray.add(statsItem.dwell);
                nbedrmsArray.add(statsItem.nBedrms);
                areaArray.add(statsItem.area);
                nlodgedArray.add(statsItem.nLodged);
            }
        }

        System.out.println(medArray);
        System.out.println(nlodgedArray);
        System.out.println(areaArray);
        System.out.println(dwellingArray);
        System.out.println(nbedrmsArray);

        return dataThree;
    }

    public static void createCsv(Data data) throws IOException {
        File csvFile = new File("test_create.csv");
        FileWriter fwOut = new FileWriter(csvFile, true);
        PrintWriter pwOut = new PrintWriter(fwOut);
        for (Item statsItem : data.getItems()) {

            if (Objects.equals(statsItem.dwell, "ALL") & Objects.equals(statsItem.nBedrms, "ALL")) {
                pwOut.printf("%s, %d, %d\n", statsItem.area, statsItem.med, statsItem.nLodged);

            }
        }
        pwOut.close();
    }
}


//        ArrayList<Integer> medArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            medArray.add(statsItem.med);
//        }
//        System.out.println(medArray);
//
//
//        ArrayList<String> dwellingArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            dwellingArray.add(statsItem.dwell);
//        }
//        System.out.println(dwellingArray);

//        ArrayList<Float> lmeanArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            lmeanArray.add(statsItem.lmean);
//        }
//        System.out.println(lmeanArray);


//        ArrayList<Object> nbedrmsArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            nbedrmsArray.add(statsItem.nBedrms);
//        }
//        System.out.println(nbedrmsArray);


//        ArrayList<Integer> uqArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            uqArray.add(statsItem.uq);
//        }
//        System.out.println(uqArray);


//        ArrayList<Integer> sdArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            sdArray.add(statsItem.sd);
//        }
//        System.out.println(sdArray);


//        ArrayList<Integer> nclosedArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            nclosedArray.add(statsItem.nClosed);
//        }
//        System.out.println(nclosedArray);


//        ArrayList<Float> brrArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            brrArray.add(statsItem.brr);
//        }
//        System.out.println(brrArray);


//        ArrayList<Integer> slqArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            slqArray.add(statsItem.slq);
//        }
//        System.out.println(slqArray);


//        ArrayList<Integer> suqArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            suqArray.add(statsItem.suq);
//        }
//        System.out.println(suqArray);


//        ArrayList<Integer> ncurrArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            ncurrArray.add(statsItem.nCurr);
//        }
//        System.out.println(ncurrArray);


//        ArrayList<String> areaArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            areaArray.add(statsItem.area);
//        }
//        System.out.println(areaArray);


//        ArrayList<Integer> meanArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            meanArray.add(statsItem.mean);
//        }
//        System.out.println(meanArray);


//        ArrayList<Float> lsdArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            lsdArray.add(statsItem.lsd);
//        }
//        System.out.println(lsdArray);


//        ArrayList<Integer> nlodgedArray = new ArrayList<>();
//        for (Item statsItem : dataThree.getItems()) {
//            nlodgedArray.add(statsItem.nLodged);
//        }
//        System.out.println(nlodgedArray);