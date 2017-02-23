package entities;


import metrics.Metrics;

import java.util.List;
import java.util.Set;

/**
 * Created by akorovin on 22.02.2017.
 */
public class Slide {
    private String content;
    private String fileName;
    private Set<String> expectedTags;
    private Metrics metrics;

    public Slide(String fileName, String content, Set<String> expectedTags) {
        this.content = content;
        this.expectedTags = expectedTags;
        this.fileName = fileName;
        this.metrics = new Metrics();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getExpectedTags() {
        return expectedTags;
    }

    public void setExpectedTags(Set<String> expectedTags) {
        this.expectedTags = expectedTags;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public void compute(List<String> nerdTags) {
        this.metrics.reset();
        //Compute the number of retrieved answers
        int correctRetrieved = 0;
        for (String s : nerdTags) {
            if (expectedTags.contains(s)) {
                correctRetrieved++;
            }
        }
        //Compute precision and recall following the evaluation metrics
        if (expectedTags.isEmpty()) {
            this.metrics.setPrecision(1.0);
            this.metrics.setRecall(1.0);
            this.metrics.setfMeasure(1.0);
        } else {
            if (nerdTags.size() == 0) {
                this.metrics.setRecall(0.0);
                this.metrics.setPrecision(1.0);
            } else {
                this.metrics.setRecall((double) correctRetrieved / expectedTags.size());
                this.metrics.setPrecision((double) correctRetrieved / nerdTags.size());
            }
            this.metrics.calculateFMeasure();
        }
    }
}
