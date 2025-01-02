package concurrentSolution;

import static general.Constants.POISON;
import static general.Constants.QUEUE_TEMP_POISON;
import static general.Constants.VLE_CLICKS;
import static general.Constants.CODE_MODULE;
import static general.Constants.CODE_PRESENTATION;
import static general.Constants.VLE_DATE;

import general.CSVParser;
import general.StudentVleEntry;

import java.io.Console;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * Producer thread that reads studentVle.csv and puts entries into a queue.
 */
public class ConVleProducer extends Thread {
    private String filename;
    private BlockingQueue<StudentVleEntry> queue;
    private int numConsumers;

    /**
     * Constructs a concurrentSolution.VleProducer.
     *
     * @param filename     The path to studentVle.csv.
     * @param queue        The shared queue for producer-consumer communication.
     * @param numConsumers The number of consumer threads.
     */
    public ConVleProducer(String filename, BlockingQueue<StudentVleEntry> queue, int numConsumers) {
        this.filename = filename;
        this.queue = queue;
        this.numConsumers = numConsumers;
    }

    @Override
    public void run() {
        try {
            CSVParser parser = new CSVParser(filename);
            parser.readHeader();

            String[] record;
            while((record = parser.readNextRecord()) != null) {
                String codeModule = record[parser.getColumnIndex(CODE_MODULE)];
                String codePresentation = record[parser.getColumnIndex(CODE_PRESENTATION)];
                int date = Integer.parseInt(record[parser.getColumnIndex(VLE_DATE)]);
                int sumClicks = Integer.parseInt(record[parser.getColumnIndex(VLE_CLICKS)]);

                StudentVleEntry entry = new StudentVleEntry(codeModule, codePresentation, date, sumClicks);
                queue.put(entry); // Blocks if queue is full

            }

            parser.close();

            for (int i = 0; i < numConsumers; i++) {
                queue.put(new StudentVleEntry(POISON, POISON, QUEUE_TEMP_POISON, QUEUE_TEMP_POISON));
            }
        }

        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConVleProducer that = (ConVleProducer) o;
        return numConsumers == that.numConsumers &&
                Objects.equals(filename, that.filename) &&
                Objects.equals(queue, that.queue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, queue, numConsumers);
    }

    @Override
    public String toString() {
        return "VleProducer{" +
                "filename='" + filename + '\'' +
                ", queue=" + queue +
                ", numConsumers=" + numConsumers +
                '}';
    }
}