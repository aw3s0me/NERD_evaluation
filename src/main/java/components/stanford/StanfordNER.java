package components.stanford;

import components.NER;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;
import java.util.Properties;

/**
 * Created by akorovin on 20.02.2017.
 */
public class StanfordNER implements NER {
    private StanfordCoreNLP pipeline;

    public StanfordNER() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        // Create a new pipeline
        this.pipeline = new StanfordCoreNLP(props);
    }

    public List<String> getEntities(String slide) {
        // Create an empty annotation just with the given text
        Annotation document = new Annotation(slide);
        // Run the stanford annotator on question
        pipeline.annotate(document);
        // TODO: analyze info. run calculation of metrics

        return null;
    }
}
