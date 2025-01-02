package sequentialSolution;

import static general.Constants.CODE_MODULE;
import static general.Constants.CODE_PRESENTATION;
import static general.Constants.VLE_CLICKS;
import static general.Constants.VLE_DATE;

import general.CSVParser;
import general.StudentVleEntry;
import java.io.IOException;

/**
 * Processes the studentVle.csv file.
 */
public class SeqStudentVleProcessor {
    /**
     * Processes the studentVle.csv file sequentially.
     *
     * @param vleFilename The path to studentVle.csv.
     * @param aggManager  The sequentialSolution.AggregationManager instance.
     * @throws IOException If an I/O error occurs.
     */
    public void processSequentially(String vleFilename, SeqAggregationManager aggManager) throws IOException {
        CSVParser parser = new CSVParser(vleFilename);
        parser.readHeader();

        String[] record;
        while ((record = parser.readNextRecord()) != null) {
            String codeModule = record[parser.getColumnIndex(CODE_MODULE)];
            String codePresentation = record[parser.getColumnIndex(CODE_PRESENTATION)];
            int date = Integer.parseInt(record[parser.getColumnIndex(VLE_DATE)]);
            int sumClicks = Integer.parseInt(record[parser.getColumnIndex(VLE_CLICKS)]);

            StudentVleEntry entry = new StudentVleEntry(codeModule, codePresentation, date, sumClicks);
            aggManager.aggregate(entry);
        }

        parser.close();
    }
}