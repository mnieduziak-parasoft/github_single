package examples.mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


/**
 * Example to show how to stub static method
 */
public class PathUtil {

    public static long getFileLength(String sPath) throws IOException {
    	Path path = Paths.get(sPath);
        return Files.size(path);
    }
}
