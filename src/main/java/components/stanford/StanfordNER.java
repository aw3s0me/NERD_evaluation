package components.stanford;

import components.NER;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
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
        // TODO: run calculation of metrics

        return interpretOutput(document);
    }

    private List<String> interpretOutput(Annotation document) {
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        List<String> res = new ArrayList<String>();

        for (CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                System.out.println("Current word: " + word);
                System.out.println("NER label: " + ne);
                System.out.println("POS tag of token: " + pos);

                res.add(ne);
            }

            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
        }

        return res;
    }
}
