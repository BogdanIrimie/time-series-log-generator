import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) throws IOException {
        Generator generator = new Generator();
        generator.generateFixIntervalRecurringEvents(1000, 5000);
        generator.generateLowToHighFrequencyEvents(0, 5000, 0, 2000);
    }
}
