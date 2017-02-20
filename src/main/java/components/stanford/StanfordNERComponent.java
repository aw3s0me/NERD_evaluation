package components.stanford;

import components.NERDComponent;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * Created by akorovin on 20.02.2017.
 */
public class StanfordNERComponent implements NERDComponent {
    private StanfordCoreNLP pipeline;

    public StanfordNERComponent() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        // Create a new pipeline
        this.pipeline = new StanfordCoreNLP(props);
    }

    public void process(String slide) {
        // Create an empty annotation just with the given text
        Annotation document = new Annotation(slide);
        // Run the stanford annotator on question
        pipeline.annotate(document);
        // TODO: analyze info. run calculation of metrics
    }
}
