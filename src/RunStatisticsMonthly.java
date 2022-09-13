import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Objects;

public class RunStatisticsMonthly implements GetCsvOutput {
    public void getMonthlyData() throws IOException, URISyntaxException, InterruptedException {

        // -------------------------------------- Load Properties File --------------------------------------
        String[] propertiesData = Utils.getPropertiesData();

        // -------------------------------------- Run API request --------------------------------------
        System.out.println("Running Statistics API request");
        Data data = Utils.runMonthlyRequest(propertiesData[0], propertiesData[3], propertiesData[1]);

        // -------------------------------------- Write to CSV --------------------------------------
        createCsv(data);

    }

    @Override
    public void createCsv(Data data) throws IOException {

        String[] regionList = {"Northland Region", "Auckland Region", "Waikato Region", "Bay of Plenty Region",
                "Gisborne Region", "Hawke's Bay Region", "Taranaki Region", "Manawatu-Wanganui Region",
                "Wellington Region", "West Coast Region", "Tasman Region", "Nelson Region", "Marlborough Region",
                "Canterbury Region", "Otago Region", "Southland Region", "ALL"};

        String pathName = "monthly_data.csv";
        File csvFile = new File(pathName);
        PrintWriter pwOut = new PrintWriter(csvFile);
        for (String region : regionList) {
            System.out.println(region);
            for (Item statsItem : data.getItems()) {
                if (statsItem.area.equals(region) & Objects.equals(statsItem.dwell, "ALL") & Objects.equals(statsItem.nBedrms, "ALL")) {
                    System.out.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                    pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                }

            }
        }
        pwOut.close();
    }
}






