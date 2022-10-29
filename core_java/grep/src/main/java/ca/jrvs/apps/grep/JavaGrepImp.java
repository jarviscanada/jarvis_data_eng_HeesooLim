package ca.jrvs.apps.grep;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class JavaGrepImp implements JavaGrep {
    @Override
    public void process() throws IOException {
        Stream<String> matchingLines = listFiles(rootPath)
                .flatMap(this::readLines)
                .filter(this::containsPattern);

        writeToFile(matchingLines);
    }

    @Override
    public Stream<File> listFiles(String rootDir) {
        List<File> allFiles = null;

        try (Stream<Path> stream = Files.walk(Paths.get(rootDir))) {
            allFiles = stream.filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        }
        catch (IOException e) {
            logger.error("Error: I/O error is thrown when accessing the starting file", e);
        }
        catch (Exception e) {
            logger.error("Error: Cannot list files", e);
        }


        if (allFiles == null || allFiles.size() == 0)
            return null;
        return allFiles.stream();
    }

    @Override
    public Stream<String> readLines(File inputFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            return reader.lines();
        } catch (FileNotFoundException e) {
            logger.error("Error: File is not found", e);
        }
        return null;
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    @Override
    public void writeToFile(Stream<String> lines) throws IOException {
        // create an OutputStream
        FileOutputStream file = new FileOutputStream(outFile);

        // create an OutputStreamWriter and BufferedWriter
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file));

        lines.forEach(line -> {
            try {
                writer.write(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        writer.close();
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3)
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");

        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception e) {
            javaGrepImp.logger.error("Error: Unable to process", e);
        }
    }
}
