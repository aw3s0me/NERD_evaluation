package utils;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by akorovin on 22.02.2017.
 * Loading resources files
 */
public class ResourceHelper {
    public final static List<String> getResourceFiles(String path) throws IOException {
        return IOUtils.readLines(ResourceHelper.class.getClassLoader()
                .getResourceAsStream(path), Charsets.UTF_8);
    }
}
