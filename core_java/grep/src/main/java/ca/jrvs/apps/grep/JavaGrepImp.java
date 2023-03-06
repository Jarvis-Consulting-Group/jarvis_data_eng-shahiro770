package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

    final static Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

    private String rootPath;
    private String regex;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    /**
     * Top level search workflow
     *
     * @throws IOException
     */
    @Override
    public void process() throws IOException {
        ArrayList<String> matchedLines = new ArrayList<String>();

        for (File f : listFiles(getRootPath())) {
            for (String line : readLines(f)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }

        writeToFile(matchedLines);
    }

    /**
     * Traverse a given directory and return all files
     *
     * @param rootDir input directory
     * @return all files under the rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) {
        File directoryPath = new File(rootDir);
        File[] directoryFiles = directoryPath.listFiles();
        return Arrays.asList(directoryFiles);
    }

    /**
     * Reads a file and return all the lines
     *
     * Explain FileReader, BufferReader, and chararcter encoding
     *
     * FileReader: Java class that reads contents of a files, inheriting from InputStreamReader
     * but reading files using the system's default character set, such as UTF-16
     * (inputStreamReader is far more advanced)
     *
     * BufferReader: Buffers the characters read from another reader to make reading a file easier
     * and more efficient. It minimizes the number of I/O operations by reading chunks from a file
     * and storing them in its buffer. It uses the decorator pattern on-top of a reader (i.e.
     * takes a reader and adds behaviour to it). Competes with Scanner for its use case, but among
     * the large number of differences, bufferReader is synchronized and has a larger buffer size).
     *
     * Character Encoding: Also called a character set, a form of representing characters by mapping
     * them to bytes (e.g. with 8 bits we can represent 256 unique characters, which is what
     * UTF-8 uses).
     *
     * @param inputFile
     * @return lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    @Override
    public List<String> readLines(File inputFile) {
        if (inputFile == null || inputFile.isFile() == false) {
            throw new IllegalArgumentException("ERROR: File must not be a directory or null");
        }
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            logger.warn("Reading {} incomplete due to IOException", inputFile);
        }

        return lines;
    }

    /**
     * Check if a line contains the regex pattern (passed by the user)
     *
     * @param line input string
     * @return true if there is a match
     */
    @Override
    public boolean containsPattern(String line) {
        return Pattern.compile(getRegex())
            .matcher(line).matches();
    }

    /**
     * Write lines to a file
     *
     * FileOutputStream: Outputstream used to write bytes to a file/fileDescriptor. Best used
     * for image data and using a different API for character writing.
     *
     * OutputStreamWriter: Writer that converts a character stream into a byte stream use a
     * charset (e.g. UTF-8, UTF-16)
     *
     * BufferedWriter: Writes text to a character output stream, bufferring for efficiency,
     * similar to bufferReader. It is also a decorator, so it adds functionality to another writer.
     *
     * @param lines matched line
     * @throws IOException if write failed
     */
    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(getOutFile()));

        for (int i = 0; i < lines.size(); i++) {
            writer.write(lines.get(i));
            writer.newLine();
        }
        writer.close();
    }

    /**
     * @return rootPath
     */
    @Override
    public String getRootPath() {
        return rootPath;
    }

    /**
     * @param rootPath
     */
    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * @return regex
     */
    @Override
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex
     */
    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * @return outFile
     */
    @Override
    public String getOutFile() {
        return outFile;
    }

    /**
     * @param outFile
     */
    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
