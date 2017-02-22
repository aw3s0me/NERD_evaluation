import utils.ResourceHelper;

import java.io.IOException;
import java.util.List;

/**
 * Created by akorovin on 21.02.2017.
 */
public class EvaluatorImpl implements Evaluator {
    private final static String resPath = "slides/";

    @Override
    public void evaluate() {
        // TODO: get all slide strings
        try {
            List<String> slides = ResourceHelper.getResourceFiles(resPath);
        } catch (IOException e) {
            System.out.println("Could not load slides at path: " + resPath);
        }


    }
}
