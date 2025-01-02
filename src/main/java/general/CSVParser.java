package general;

import static general.Constants.BACKSLASH;
import static general.Constants.COL_EXCEPTION_P1;
import static general.Constants.COL_EXCEPTION_P2;
import static general.Constants.COMMA;
import static general.Constants.FILE_EMPTY;
import static general.Constants.NOTHING;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for parsing CSV files with header rows.
 */
public class CSVParser implements AutoCloseable{
    private BufferedReader reader;
    private Map<String, Integer> headerMap;

    /**
     * Constructs a general.CSVParser for the specified file.
     *
     * @param filename The path to the CSV file.
     * @throws IOException If an I/O error occurs.
     */
    public CSVParser(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        headerMap = new HashMap<>();
    }

    /**
     * Reads the header row and maps column names to indices.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void readHeader() throws IOException {
        String headerLine = reader.readLine();

        if (headerLine != null) {
            String[] headers = headerLine.replace(BACKSLASH, NOTHING).split(COMMA);
            for (int i = 0; i < headers.length; i++) {
                headerMap.put(headers[i].trim(), i);
            }
        }

        else {
            throw new IOException(FILE_EMPTY);
        }
    }

    /**
     * Reads the next record from the CSV file.
     *
     * @return An array of field values, or null if end of file.
     * @throws IOException If an I/O error occurs.
     */
    public String[] readNextRecord() throws IOException {
        String line = reader.readLine();

        if (line != null) {
            return line.replace(BACKSLASH, NOTHING).split(COMMA);
        }

        return null;
    }

    /**
     * Gets the index of a column based on its name.
     *
     * @param columnName The name of the column.
     * @return The index of the column.
     * @throws IllegalArgumentException If the column is not found.
     */
    public int getColumnIndex(String columnName) {
        if (this.headerMap.containsKey(columnName)) {
            return this.headerMap.get(columnName);
        }

        else {
            throw new IllegalArgumentException(COL_EXCEPTION_P1 + columnName + COL_EXCEPTION_P2);
        }
    }

    /**
     * Closes the general.CSVParser and releases resources.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }
}