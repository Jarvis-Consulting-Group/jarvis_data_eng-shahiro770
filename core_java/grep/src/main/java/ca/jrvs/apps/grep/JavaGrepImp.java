package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

         for (File f in )
    }

    /**
     * Traverse a given directory and return all files
     *
     * @param rootDir input directory
     * @return all files under the rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) {
        return null;
    }

    /**
     * Reads a file and return all the lines
     * <p>
     * Explain FileReader, BufferReader, and chararcter encoding
     *
     * @param inputFile
     * @return lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    @Override
    public List<String> readLines(File inputFile) {
        return null;
    }

    /**
     * Check if a line contains the regex pattern (passed by the user)
     *
     * @param line input string
     * @return true if there is a match
     * @throws IOException
     */
    @Override
    public boolean containsPattern(String line) {
        return false;
    }

    /**
     * Write lines to a file
     * <p>
     * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter
     *
     * @param lines matched line
     * @throws IOException if write failed
     */
    @Override
    public void writeToFile(List<String> lines) throws IOException {

    }

    /**
     * @return
     */
    @Override
    public String getRootPath() {
        return null;
    }

    /**
     * @param rootPath
     */
    @Override
    public void setRootPath(String rootPath) {

    }

    /**
     * @return
     */
    @Override
    public String getRegex() {
        return null;
    }

    /**
     * @param regex
     */
    @Override
    public void setRegex(String regex) {

    }

    /**
     * @return
     */
    @Override
    public String getOutFile() {
        return null;
    }

    /**
     * @param outFile
     */
    @Override
    public void setOutFile(String outFile) {

    }
}
