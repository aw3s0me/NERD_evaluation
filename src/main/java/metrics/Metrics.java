package metrics;

/**
 * Created by akorovin on 20.02.2017.
 */
public class Metrics {
    private final static Double DEFAULT = 0.0;
    private Double precision;
    private Double recall;
    private Double fMeasure;

    public Metrics(Double precision, Double recall, Double fMeasure) {
        this.precision = precision;
        this.recall = recall;
        this.fMeasure = fMeasure;
    }

    public Metrics() {
        this.precision = DEFAULT;
        this.recall = DEFAULT;
        this.fMeasure = DEFAULT;
    }

    public void reset() {
        this.precision = DEFAULT;
        this.recall = DEFAULT;
        this.fMeasure = DEFAULT;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public Double getRecall() {
        return recall;
    }

    public void setRecall(Double recall) {
        this.recall = recall;
    }

    public Double getfMeasure() {
        return fMeasure;
    }

    public void setfMeasure(Double fMeasure) {
        this.fMeasure = fMeasure;
    }

    public void calculateFMeasure() {
        if (precision == 0 && recall == 0) {
            fMeasure = 0.0;
        } else {
            fMeasure = (2 * precision * recall) / (precision + recall);
        }
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "precision=" + precision +
                ", recall=" + recall +
                ", fMeasure=" + fMeasure +
                '}';
    }
}
