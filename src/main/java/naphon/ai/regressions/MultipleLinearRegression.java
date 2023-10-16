package naphon.ai.regressions;

import naphon.ai.ai.exceptions.AIException;
import naphon.ai.ai.exceptions.MatrixException;
import naphon.ai.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class MultipleLinearRegression {
    private List<double[]> keys;
    private List<Double> values;
    private int amountOfX;
    private List<Double> beta = new ArrayList<>();
    private double intercept;

    private double error = 0;

    public void train(List<double[]> keys, List<Double> values) throws AIException {
        this.keys = keys;
        this.values = values;
        int kSize = keys.size();
        int vSize = values.size();
        if (kSize != vSize) {
            throw new AIException("Found missing data in data set. Count of data in keys is " + kSize + " but Count of data in values is " + vSize);
        }
        if (!allArrayIsEqualLength(keys)) {
            throw new AIException("Found missing data in data set.Data in Key is missing");
        }
        this.amountOfX = keys.get(0).length;
        Matrix A = new Matrix(kSize, amountOfX + 1);
        Matrix B = new Matrix(vSize, 1);
        for (int i = 0; i < kSize; i++) {
            for (int j = 0; j <= amountOfX; j++) {
                if (j == 0) {
                    A.apply(i, j, 1);
                } else {
                    A.apply(i, j, keys.get(i)[j - 1]);
                }
            }
        }
        for (int i = 0; i < vSize; i++) {
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
        this.intercept = X.getElement(0, 0);
        for (int i = 1; i < X.getRowSize(); i++) {
            beta.add(X.getElement(i, 0));
        }
        error();
    }

    private void error() throws AIException {
        double epsilon = 0;
        for (int j = 0; j < keys.size(); j++) {
            double realV = values.get(j);
            double predicted = predict(keys.get(j));
            epsilon += (realV - predicted);
        }
        this.error = epsilon / keys.size();
    }

    public double predict(double[] key) throws AIException {
        if (key.length != amountOfX) {
            throw new AIException("Key Data is incomplete");
        }
        double y = intercept;
        for (int i = 0; i < amountOfX; i++) {
            y += beta.get(i) * key[i];
        }
        return y + error;
    }

    public boolean allArrayIsEqualLength(List<double[]> list) {
        int firstLength = list.get(0).length;

        for (double[] d : list) {
            if (d.length != firstLength) {
                return false;
            }
        }

        return true;


    }
}
