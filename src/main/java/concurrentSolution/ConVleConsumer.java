package concurrentSolution;

import static general.Constants.POISON;

import general.StudentVleEntry;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * Consumer thread that processes entries from the queue.
 */
public class ConVleConsumer extends Thread {
    private BlockingQueue<StudentVleEntry> queue;
    private ConAggregationManager aggManager;

    /**
     * Constructs a concurrentSolution.VleConsumer.
     *
     * @param queue      The shared queue for producer-consumer communication.
     * @param aggManager The sequentialSolution.AggregationManager instance.
     */
    public ConVleConsumer(BlockingQueue<StudentVleEntry> queue, ConAggregationManager aggManager) {
        this.queue = queue;
        this.aggManager = aggManager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                StudentVleEntry entry = queue.take();
                if (POISON.equals(entry.getCodeModule())) {
                    break;
                }
                aggManager.aggregate(entry);
            }
        }

        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConVleConsumer that = (ConVleConsumer) o;
        return Objects.equals(queue, that.queue) &&
                Objects.equals(aggManager, that.aggManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queue, aggManager);
    }

    @Override
    public String toString() {
        return "VleConsumer{" +
                "queue=" + queue +
                ", aggManager=" + aggManager +
                '}';
    }
}