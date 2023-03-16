import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        run();

    }
    static void run() throws IOException, URISyntaxException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose (1) for regional monthly data, (2) for District quarterly data, (3) for Ward quarterly Auckland data or (4) for Auckland CBD monthly data: ");
        int timeFrame = scanner.nextInt();

        if (timeFrame == 1) {
            System.out.println("You have chosen to get the monthly data report: ");
            RunRegionMonthlyRents monthlyData = new RunRegionMonthlyRents();
            try {
                monthlyData.getMonthlyData();
                System.out.println("Data received: 'region_monthly_rents.csv'");
            } catch(JsonSyntaxException | IOException | URISyntaxException | InterruptedException e) {
                System.out.println("Looks like MBIE has let us down for the millionth time");
                e.printStackTrace();
            }
        } else if (timeFrame == 2) {
            System.out.println("You have chosen to get the quarterly data report: ");
            RunDistrictQuarterlyRents quarterlyData = new RunDistrictQuarterlyRents();
            quarterlyData.getQuarterlyData();
            System.out.println("Data received: 'district_quarterly_rents.csv'");
        } else if (timeFrame == 3) {
            System.out.println("You have chosen to get the quarterly Auckland data report: ");
            RunWardQuarterlyRents quarterlyData = new RunWardQuarterlyRents();
            quarterlyData.getQuarterlyData();
            System.out.println("Data received: 'ward_quarterly_rents.csv'");
        } else if (timeFrame == 4) {
            System.out.println("You have chosen to get the monthly Auckland CBD data report: ");
            RunCbdMonthlyAndQuarterlyRents monthlyData = new RunCbdMonthlyAndQuarterlyRents();
            monthlyData.getMonthlyOrQuarterlyData();
            System.out.println("Data received: 'CBD_monthly_rents.csv'");
        } else {
            System.out.println("Fuck you pick 1, 2 or 3!");
            run();
        }

    }
}
