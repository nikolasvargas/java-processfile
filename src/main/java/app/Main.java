package app;

import utils.FileUtil;
import java.io.File;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    private static boolean LOOP = true;

    public static int run() {
        String homePath = System.getProperty("user.home");
        String inputPath = homePath + "/data/in";
        File inputPathDir = new File(inputPath);
        boolean homePathNotExists = !inputPathDir.exists();
        boolean homePathIstNotDirectory = !inputPathDir.isDirectory();
        boolean homePathDirIsNotReadable = !inputPathDir.canRead();

        if (homePathNotExists) {
            processError("Path does not exists: " + inputPath);
        } else if (homePathIstNotDirectory) {
            processError("Path is not a directory: " + inputPath);
        } else if (homePathDirIsNotReadable) {
            processError("Could not read path: " + inputPath);
        }

        File outputPathDir = null;
        File processedPathDir = null;

        try {
            // Create output files directory
            String outputPath = homePath + "/data/out";
            outputPathDir = FileUtil.createPath(outputPath);

            // Create processed files directory
            String processedPath = homePath + "/data/processed";
            processedPathDir = FileUtil.createPath(processedPath);

        } catch (Exception e) {
            processError(e.getMessage());
        }

        // Loop to check files and process
        do {
            File[] fileList = inputPathDir.listFiles(pathName ->
                    pathName.isFile() && pathName.getName().endsWith(".dat"));
            boolean fileListIsNotEmpty = fileList.length > 0;

            if (fileListIsNotEmpty) {
                File finalOutputPathDir = outputPathDir;
                File finalProcessedPathDir = processedPathDir;
                Arrays.stream(fileList)
                        .map(FileProcess::new)
                        .forEach(f -> {
                            f.process();
                            f.createOutputFile(finalOutputPathDir);
                            f.moveProcessedFile(finalProcessedPathDir);
                        });
            } else {
                System.out.println("No new files found");
            }

            if (LOOP) {
                try {
                    System.out.println("Waiting 10s to next verification...");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(LOOP);

        return 0;
    }

    public static void processError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        run();
    }
}