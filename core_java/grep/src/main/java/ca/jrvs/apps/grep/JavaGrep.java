package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public interface JavaGrep {
    /**
     * Top level search workflow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and return the list of files
     * @param rootDir directory to list files
     * @return list of files under the target directory
     */
    Stream<File> listFiles(String rootDir);

    /**
     * Read a file and return all the lines.
     * It reads the contents of a file as a stream of characters using the FileReader class and
     * buffers the characters in order to simplify reading text from a character input stream using the BufferedReader class,
     *
     * @param inputFile file to get the String lines
     * @return List of the lines in the file
     * @throws IllegalArgumentException if a given inputFile is not a valid file
     */
    Stream<String> readLines(File inputFile);

    /**
     * check if a line contains the regex pattern passed as an argument
     * @param line line to check the regex pattern
     * @return true if there is a match in the line
     */
    boolean containsPattern(String line);

    /**
     * Write a file as a stream of bytes using the FileOutputStream class,
     * write characters to a file, encoded as e.g. UTF-8 or UTF-16 using the OutputStreamWriter class,
     * and provides buffering to Writer instances to speed up IO using the BufferedWriter class
     * @param lines
     * @throws IOException
     */
    void writeToFile(Stream<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}
