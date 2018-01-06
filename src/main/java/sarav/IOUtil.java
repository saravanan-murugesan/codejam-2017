package sarav;

import java.io.*;

public class IOUtil {
    public final String inputFile;
    public final String outputFile;
    public final int numberOfTestCases;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private int numberOfTestCasesRemaining;

    public String getNextTestCase() {
        if(numberOfTestCasesRemaining > 0) {
            try {
                String line = reader.readLine();
                numberOfTestCasesRemaining--;
                return line;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Consumed all test cases");
        }
    }

    public boolean hasMoreTestCases() {
        return numberOfTestCasesRemaining > 0;
    }

    public void writeCase(int number, String value) {
        try {
            writer.append("Case #" + number + ": " + value + "");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String line) {
        try {
            writer.append(line);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishWritingOutput() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IOUtil(final String inputFile, final String outputFile) throws IOException {
        String rootPath = "/Users/saravm/Work/git/codejam2017/src/main/resources/";
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.reader = new BufferedReader(new FileReader(rootPath + inputFile));
        this.numberOfTestCases = Integer.valueOf(this.reader.readLine());
        this.numberOfTestCasesRemaining = numberOfTestCases;
        this.writer =  new BufferedWriter(new FileWriter(rootPath + outputFile));

    }
}
