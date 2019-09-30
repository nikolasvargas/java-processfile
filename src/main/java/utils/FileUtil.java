package utils;

import java.io.File;

public class FileUtil {
    /**
     * Create a path if it doesn't exists
     * @param path Path to be created
     * @return File object that represents the path created
     * @throws Exception
     */
    public static File createPath(String path) throws Exception {
        File pathDir = new File(path);
        boolean pathNotExists = !pathDir.exists();
        boolean cantCreateDir = !pathDir.mkdirs();
        boolean pathIsNotDir = !pathDir.isDirectory();

        if (pathNotExists && cantCreateDir) {
            throw new Exception("Could not create processed path: " + path);
        }

        if (pathIsNotDir) {
            throw new Exception("Processed Path is not a directory: " + path);
        }

        return pathDir;
    }
}
