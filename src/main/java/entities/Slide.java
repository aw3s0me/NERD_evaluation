package entities;


import metrics.Metrics;

import java.util.List;

/**
 * Created by akorovin on 22.02.2017.
 */
public class Slide {
    private String content;
    private List<String> expectedTags;
    private Metrics metrics;

    public Slide(String content, List<String> expectedTags) {
        this.content = content;
        this.expectedTags = expectedTags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getExpectedTags() {
        return expectedTags;
    }

    public void setExpectedTags(List<String> expectedTags) {
        this.expectedTags = expectedTags;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public void compute(List<String> nerdTags) {
        //Compute the number of retrieved answers
        int correctRetrieved = 0;
        for (String s : nerdTags) {
            if (expectedTags.contains(s)) {
                correctRetrieved++;
            }
        }
        //Compute precision and recall following the evaluation metrics
        if (expectedTags.size() == 0) {
            this.metrics = expectedTags.isEmpty()? new Metrics(1.0, 1.0, 1.0): new Metrics(0.0, 0.0, 0.0);
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
