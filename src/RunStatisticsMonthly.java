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
        String pathName = "monthly_data.csv";

        File csvFile = new File(pathName);
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






