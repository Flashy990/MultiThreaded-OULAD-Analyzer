package concurrentSolution;

import static general.Constants.BLOCKING_QUEUE_SIZE;
import static general.Constants.PROCESSING_INTERRUPTED;

import general.StudentVleEntry;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Processes the studentVle.csv file concurrently.
 */
public class ConStudentVleProcessor {
    /**
     * Processes the studentVle.csv file using concurrent threads.
     *
     * @param vleFilename The path to studentVle.csv.
     * @param aggManager  The AggregationManager instance.
     */
    public void processConcurrently(String vleFilename, ConAggregationManager aggManager) {
        int numConsumers = Runtime.getRuntime().availableProcessors(); // Number of consumer threads
        BlockingQueue<StudentVleEntry> queue = new LinkedBlockingQueue<>(BLOCKING_QUEUE_SIZE); // Adjust capacity as needed

        // Start the producer thread
        ConVleProducer producer = new ConVleProducer(vleFilename, queue, numConsumers);
        producer.start();

        // Start the consumer threads
        ConVleConsumer[] consumers = new ConVleConsumer[numConsumers];
        for (int i = 0; i < numConsumers; i++) {
            consumers[i] = new ConVleConsumer(queue, aggManager);
            consumers[i].start();
        }

        // Wait for the producer and consumers to finish
        try {
            producer.join();
            for (ConVleConsumer consumer : consumers) {
                consumer.join();
            }
        }

        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(PROCESSING_INTERRUPTED);
        }
    }
}