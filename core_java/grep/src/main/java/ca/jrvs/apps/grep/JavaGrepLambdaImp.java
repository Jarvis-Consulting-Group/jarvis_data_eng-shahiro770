package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepLambdaImp extends JavaGrepImp {

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            javaGrepLambdaImp.logger.error("Error: Unable to process", ex);
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

        listFiles(getRootPath()).stream()
            .forEach(file -> readLines(file).stream()
                .forEach(line -> {
                    if (containsPattern(line)) {
                        matchedLines.add(line);
                    };
                }));

        writeToFile(matchedLines);
    }

    /**
     * Traverse a given directory and return all files using lambdas
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
     * Reads a file and return all the lines using a lambda
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

            reader.lines().forEach(line -> {
                lines.add(line);
            });
            reader.close();
        }
        catch (IOException e) {
            logger.warn("Reading {} incomplete due to IOException", inputFile);
        }

        return lines;
    }

    /**
     * Write lines to a file using a lambda
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

        Stream<String> lineStream = lines.stream();
        lineStream.forEach(line -> {
            try {
                writer.write(line);
                writer.newLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.close();
    }
}
