import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Objects;


public class RunCbdMonthlyAndQuarterlyRents implements GetCsvOutput {
    public void getMonthlyOrQuarterlyData() throws IOException, URISyntaxException, InterruptedException {

        // -------------------------------------- Load Properties File --------------------------------------
        String[] propertiesData = Utils.getPropertiesData();

        // -------------------------------------- Run API request --------------------------------------
        System.out.println("Running Statistics API request");
        Data data = Utils.runCbdQuarterlyRents(propertiesData[0], propertiesData[3], propertiesData[1]);

        // -------------------------------------- Write to CSV --------------------------------------
        createCsv(data);
    }

    @Override
    public void createCsv(Data data) throws IOException {

        String pathName = "CBD_monthly_rents.csv";
        File csvFile = new File(pathName);
        PrintWriter pwOut = new PrintWriter(csvFile);

        for (Item statsItem : data.getItems()) {
            if (statsItem.area.equals("MyCBD")) {
                if (Objects.equals(statsItem.dwell, "Apartment") & Objects.equals(statsItem.nBedrms, "1")) {
                    System.out.println("One bedroom apartments: " + "\n" + statsItem.nLodged + "\n" + statsItem.med);
                    pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                }

                if (Objects.equals(statsItem.dwell, "Apartment") & Objects.equals(statsItem.nBedrms, "2")) {
                    System.out.println("Two bedroom apartments: " + "\n" + statsItem.nLodged + "\n" + statsItem.med);
                    pwOut.printf("%d, %d,", statsItem.nLodged, statsItem.med);
                }
            }
        }
        pwOut.close();
    }
}
