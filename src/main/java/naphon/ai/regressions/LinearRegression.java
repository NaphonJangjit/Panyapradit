package naphon.ai.regressions;

import naphon.ai.ai.exceptions.AIException;
import naphon.ai.ai.exceptions.MatrixException;
import naphon.ai.matrix.Matrix;

import java.util.List;

public class LinearRegression {
    private List<Double> keys;
    private List<Double> values;

    private double slope;
    private double intercept;

    public void train(List<Double> keys, List<Double> values) throws AIException {
        this.keys = keys;
        this.values = values;
        if (keys.size() != values.size()) {
            throw new AIException("Found missing data in data set. Count of data in keys is " + keys.size() + " but Count of data in values is " + values.size());
        }
        Matrix A = new Matrix(this.keys.size(), 2);
        Matrix B = new Matrix(this.values.size(), 1);
        for (int i = 0; i < this.keys.size(); i++) {
            A.apply(i, 0, keys.get(i));
            A.apply(i, 1, 1);
            B.apply(i, 0, values.get(i));
        }
        Matrix X = null;
        Matrix AT = A.transpose();
        Matrix ATA;
        Matrix IATA;
        Matrix ATB;
        try {
            ATA = AT.multiply(A);
            IATA = ATA.inverse();
            ATB = AT.multiply(B);
            X = IATA.multiply(ATB);
        } catch (MatrixException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        assert X != null;
        this.slope = X.getElement(0, 0);
        this.intercept = X.getElement(1, 0);
    }

    public double predict(double key) {
        return slope * key + intercept;
    }
}
