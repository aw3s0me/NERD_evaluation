package utils;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akorovin on 22.02.2017.
 * Loading resources files
 */
public class ResourceHelper {
    public static List<String> getResourceFiles(String path) throws IOException {
        List<String> fileNames = IOUtils.readLines(ResourceHelper.class.getClassLoader().getResourceAsStream(path), Charsets.UTF_8);
        List<String> fileContents = new ArrayList<>();
        for (String fileName: fileNames) {
            fileContents.add(ResourceHelper.readSingleFile(path + fileName));
        }
        return fileContents;
    }

    private static String readSingleFile(String path) {
        String result = "";
        try {
            result = IOUtils.toString(ResourceHelper.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
