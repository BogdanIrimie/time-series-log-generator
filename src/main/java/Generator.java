import org.apache.commons.math3.distribution.AbstractIntegerDistribution;
import org.apache.commons.math3.distribution.BinomialDistribution;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;

public class Generator {

    public void generateFixIntervalRecurringEvents(final int RPS, final int TIME_INTERVAL) {
        double timePerRequest = 1.0 / RPS;
        double timeInLog = 0;

        Path path = Paths.get("generatedTraces/constant.log");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            while (timeInLog <= TIME_INTERVAL) {
                timeInLog += timePerRequest;
                writer.append(0 + " " + String.format("%.5f", timeInLog) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateLowToHighFrequencyEvents(final int START_RPS, final int END_RPS, final int START_TIME, final int END_TIME) {
        int timeInterval = END_TIME - START_TIME;
        double rps_increment = (END_RPS - START_RPS) / timeInterval;
        double timeInLog = START_TIME;
        double rps = START_RPS + rps_increment;
        double timePerRequest;

        Path path = Paths.get("generatedTraces/ascending.log");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = START_TIME ; i < END_TIME; i++) {
                timePerRequest = 1.0 / rps;
                while (timeInLog <= i + 1) {
                    timeInLog += timePerRequest;
                    writer.append(0 + " " + String.format("%.5f", timeInLog) + "\n");
                }
                rps += rps_increment;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateHighToLowFrequencyEvents(final int START_RPS, final int END_RPS, final int START_TIME, final int END_TIME) {
        int timeInterval = END_TIME - START_TIME;
        double rps_decrement = (START_RPS - END_RPS) / timeInterval;
        double timeInLog = START_TIME;
        double rps = START_RPS;
        double timePerRequest;

        Path path = Paths.get("generatedTraces/descending.log");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = START_TIME ; i < END_TIME; i++) {
                timePerRequest = 1.0 / rps;
                while (timeInLog <= i + 1) {
                    timeInLog += timePerRequest;
                    writer.append(0 + " " + String.format("%.5f", timeInLog) + "\n");
                }
                rps -= rps_decrement;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateBinomial(final int TIME_INTERVAL) {

        int rps;
        double timePerRequest;
        double timeInLog = 0;

        Path path = Paths.get("generatedTraces/binomial.log");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AbstractIntegerDistribution distribution = new BinomialDistribution(10, 0.5);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = 1; i <= 11; i++) {
                rps = (int) (distribution.probability(i - 1) * 10000);
                System.out.println("Generating " + rps + " rps");
                timePerRequest = 1.0 / rps;
                while (timeInLog <= (TIME_INTERVAL / 11 * i)) {
                    timeInLog += timePerRequest;
                    writer.append(0 + " " + String.format("%.5f", timeInLog) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
