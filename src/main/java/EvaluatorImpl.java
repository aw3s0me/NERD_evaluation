import components.NER;
import components.alchemy.AlchemyNERD;
import components.dbpedia.DBpediaNER;
import components.stanford.StanfordNER;
import entities.Slide;
import utils.ResourceHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akorovin on 21.02.2017.
 */
public class EvaluatorImpl implements Evaluator {
    private final static String resPath = "slides/";
    private Map<String, NER> nerTools = new HashMap<>();

    public EvaluatorImpl() {
        this.nerTools = initNER();
    }

    private HashMap<String, NER> initNER() {
        return new HashMap<String, NER>() {{
            put("dbpedia", new DBpediaNER());
            put("alchemy", new AlchemyNERD());
            put("stanford", new StanfordNER());
        }};
    }

    @Override
    public void evaluate() {
        // TODO: get all slide strings
        List<Slide> slides = new ArrayList<>();
        try {
            // TODO: reimplement it to use map
            List<String> slideFileContents = ResourceHelper.getResourceFiles(resPath);
            List<String> slideExpectedAnswers = null;
            for (String slide: slideFileContents) {
                slides.add(new Slide(slide, null));
            }
        } catch (IOException e) {
            System.out.println("Could not load slides at path: " + resPath);
        }

        for (Slide slide : slides) {
            // TODO: get expected entities from somewhere
            for (NER nerTool: this.nerTools.values()) {
                List<String> entities = nerTool.getEntities(slide.getContent());
                // slide.compute(entities);
                // System.out.println(slide.getMetrics().toString());
            }
        }
    }
}
