package naphon.ai.matrix;

public class MatrixUtils {
    public static double[][] zero(int a, int b) {
        double[][] zero = new double[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                zero[i][j] = 0;
            }
        }
        return zero;
    }
}
