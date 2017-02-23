package utils;

import entities.Slide;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by akorovin on 22.02.2017.
 * Loading resources files
 */
public final class ResourceHelper {
    public static Map<String, Slide> getResourceFiles(String pathSlides, String pathTags) throws IOException {
        Set<String> fileNamesSlides = new HashSet<>(IOUtils.readLines(ResourceHelper.class.getClassLoader().getResourceAsStream(pathSlides), Charsets.UTF_8));
        Map<String, Slide> fileContents = new HashMap<>();
        for (String fileName: fileNamesSlides) {
            String content = ResourceHelper.readSingleFile(pathSlides + fileName);
            String tagContent = ResourceHelper.readSingleFile(pathTags + fileName);
            Set<String> tags = ResourceHelper.getTagsForSlide(tagContent);

            fileContents.put(fileName, new Slide(fileName, content, tags));
        }
        return fileContents;
    }

    private static Set<String> getTagsForSlide(String tagFileContent) {
        return new HashSet<>(Arrays.asList(tagFileContent.split("\\r?\\n")));
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
