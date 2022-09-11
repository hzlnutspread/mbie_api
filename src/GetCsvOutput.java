import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public interface GetCsvOutput {

    public void createCsv(Data data) throws IOException;
}


