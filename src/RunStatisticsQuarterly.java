import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Objects;


public class RunStatisticsQuarterly implements GetCsvOutput {
    public void getQuarterlyData() throws IOException, URISyntaxException, InterruptedException {

        // -------------------------------------- Load Properties File --------------------------------------
        String[] propertiesData = Utils.getPropertiesData();

        // -------------------------------------- Run API request --------------------------------------
        System.out.println("Running Statistics API request");
        Data data = Utils.runQuarterlyRequest(propertiesData[0], propertiesData[3], propertiesData[1]);

        // -------------------------------------- Write to CSV --------------------------------------
        createCsv(data);
    }

    @Override
    public void createCsv(Data data) throws IOException {

        String[] regionList = {"Whangarei District", "Hamilton City", "Tauranga City", "Whakatane District",
                "Rotorua District", "Taupo District", "Napier City", "Hastings District", "New Plymouth District",
                "Whanganui District", "Palmerston North City", "Kapiti Coast District", "Porirua City",
                "Upper Hutt City", "Lower Hutt City", "Wellington City", "Masterton District", "Nelson City",
                "Marlborough District", "Waimakariri District", "Christchurch City", "Selwyn District",
                "Ashburton District", "Timaru District", "Queenstown-lakes District", "Dunedin City",
                "Invercargill City", "ALL"};

        String pathName = "quarterly_data.csv";
        File csvFile = new File(pathName);
        PrintWriter pwOut = new PrintWriter(csvFile);
        for (String region : regionList) {
            System.out.println(region);
            for (Item statsItem : data.getItems()) {
               if (statsItem.area.equals(region)) {
                   if (Objects.equals(statsItem.dwell, "ALL") & Objects.equals(statsItem.nBedrms, "ALL")) {
                       System.out.println("All property types");
                       pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                   }
                   if (Objects.equals(statsItem.dwell, "Apartment") & Objects.equals(statsItem.nBedrms, 1.0)) {
                       System.out.println("1 bedroom apartment");
                       pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                   }
                   if (Objects.equals(statsItem.dwell, "Apartment") & Objects.equals(statsItem.nBedrms, 2.0)) {
                       System.out.println("2 bedroom apartment");
                       pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                   }
                    if (Objects.equals(statsItem.dwell, "House") & Objects.equals(statsItem.nBedrms, 3.0)) {
                        System.out.println("3 bedroom house");
                        pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                    }
                }
            }
        }
        pwOut.close();
    }
}