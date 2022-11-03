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

        System.out.println("Please choose (1) for monthly data, (2) for quarterly data or (3) for quarterly Auckland data: ");
        int timeFrame = scanner.nextInt();

        if (timeFrame == 1) {
            System.out.println("You have chosen to get the monthly data report: ");
            RunStatisticsMonthly monthlyData = new RunStatisticsMonthly();
            try {
                monthlyData.getMonthlyData();
                System.out.println("Data received: 'monthly_data.csv'");
            } catch(JsonSyntaxException | IOException | URISyntaxException | InterruptedException e) {
                System.out.println("Looks like MBIE has let us down for the millionth time");
                e.printStackTrace();
            }
        } else if (timeFrame == 2) {
            System.out.println("You have chosen to get the quarterly data report: ");
            RunStatisticsQuarterly quarterlyData = new RunStatisticsQuarterly();
            quarterlyData.getQuarterlyData();
            System.out.println("Data received: 'quarterly_data.csv'");
        } else if (timeFrame == 3) {
            System.out.println("You have chosen to get the quarterly Auckland data report: ");
            RunStatisticsQuarterlyAuckland quarterlyData = new RunStatisticsQuarterlyAuckland();
            quarterlyData.getQuarterlyData();
            System.out.println("Data received: 'quarterly_data_Auckland.csv'");
        } else {
            System.out.println("Fuck you pick 1, 2 or 3!");
            run();
        }

    }
}