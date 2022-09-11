import com.google.gson.Gson;
import jdk.jshell.execution.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class RunAreaCode {
    public static void main(String[] args) throws Exception {

        // -------------------------------------- Load Properties File --------------------------------------
        String[] propertiesData = Utils.getPropertiesData();

        // -------------------------------------- First API request --------------------------------------
        ArrayList<String> areaCodes = getAreaCodes(propertiesData[0], propertiesData[2], propertiesData[1]);

        // -------------------------------------- Second API request --------------------------------------
        getAreaCodeData(propertiesData[0], propertiesData[2], areaCodes, propertiesData[1]);

    }


    public static ArrayList<String> getAreaCodes(String url, String areaPath, String token) throws URISyntaxException, IOException, InterruptedException {
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
        return areaCodes;
    }

    public static void getAreaCodeData(String url, String areaPath, ArrayList<String> areaCodes, String token) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClientTwo = HttpClient.newHttpClient();
        HttpRequest getRequestTwo = HttpRequest.newBuilder()
                .uri(new URI(url + areaPath + "/" + areaCodes.get(9)))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> getResponseTwo = httpClientTwo.send(getRequestTwo, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
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

    }

}