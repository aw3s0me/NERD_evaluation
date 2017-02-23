import components.NER;
import components.dbpedia.DBpediaNER;
import entities.Slide;
import utils.ResourceHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akorovin on 21.02.2017.
 */
public class EvaluatorImpl implements Evaluator {
    private final static String slidePath = "slides/";
    private final static String tagPath = "tags/";
    private Map<String, NER> nerTools = new HashMap<>();

    public EvaluatorImpl() {
        this.nerTools = initNER();
    }

    private HashMap<String, NER> initNER() {
        return new HashMap<String, NER>() {{
            put("dbpedia", new DBpediaNER());
            // put("alchemy", new AlchemyNERD());
            //put("stanford", new StanfordNER());
        }};
    }

    @Override
    public void evaluate() {
        // TODO: get all slide strings
        Map<String, Slide> slideFileContents;
        try {
            // TODO: reimplement it to use map
            slideFileContents = ResourceHelper.getResourceFiles(slidePath, tagPath);
            for (Map.Entry<String, Slide> slide: slideFileContents.entrySet()) {
                Slide curSlide = slide.getValue();
                System.out.println(slide.getKey());
                for (NER nerTool: this.nerTools.values()) {
                    System.out.println("START NER: ");
                    List<String> entities = nerTool.getEntities(curSlide.getContent());
                    for (String entity: entities) {
                        System.out.println(entity);
                    }
                    curSlide.compute(entities);
                    System.out.println(curSlide.getMetrics().toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load slides at path: " + slidePath);
        }
    }

    public static void main(String[] args) {
        Evaluator evaluator = new EvaluatorImpl();
        evaluator.evaluate();
    }
}
