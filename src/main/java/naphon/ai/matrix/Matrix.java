package naphon.ai.matrix;

import naphon.ai.ai.exceptions.MatrixException;

public class Matrix {
    private double[][] matrix;
    private int rows;
    private int columns;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }

    public boolean apply(int row, int col, double data) {
        try {
            matrix[row][col] = data;
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public int getRowSize() {
        return matrix.length;
    }

    public int getColSize() {
        return matrix[0].length;
    }

    public boolean apply(double[][] data) {
        if (data.length == matrix.length && data[0].length == matrix[0].length) {
            matrix = data;
            return true;
        }
        return false;
    }

    public double getElement(int row, int col) {
        return matrix[row][col];
    }

    public Matrix transpose() {
        Matrix new_matrix = new Matrix(columns, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                new_matrix.apply(j, i, getElement(i, j));
            }
        }
        return new_matrix;
    }

    public Matrix multiply(Matrix mat) throws MatrixException {
        int thRows = this.getRowSize();
        int thCol = this.getColSize();
        int matCol = mat.getColSize();
        Matrix matr = new Matrix(thRows, matCol);
        if (thCol != mat.getRowSize()) {
            throw new MatrixException("Matrix cannot multiply because: " + thCol + " not equal to " + mat.getRowSize());
        }
        for (int i = 0; i < thRows; i++) {
            for (int j = 0; j < matCol; j++) {
                double o_data = matr.getElement(i, j);
                for (int k = 0; k < thCol; k++) {
                    o_data += this.getElement(i, k) * mat.getElement(k, j);
                }
                matr.apply(i, j, o_data);
            }
        }
        return matr;
    }

    public Matrix multiplyWithOneDimensionMatrix(Matrix mat) {
        int thRows = this.getRowSize();
        int thCol = this.getColSize();
        Matrix matr = new Matrix(thRows, 1);
        matr.apply(MatrixUtils.zero(thRows, 1));
        for (int i = 0; i < thRows; i++) {
            for (int j = 0; j < thCol; j++) {
                matr.apply(i, 0, matr.getElement(i, 0) + (this.getElement(i, j) * mat.getElement(j, 0)));
            }
        }
        return matr;
    }

    public Matrix multiply(double d) {
        int rows = this.getRowSize();
        int cols = this.getColSize();
        Matrix mat = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat.apply(i, j, getElement(i, j) * d);
            }
        }
        return mat;
    }

    public double determinant() throws MatrixException {
        if (getRowSize() == 1) {
            return getElement(0, 0);
        } else if (getRowSize() == 2) {
            return getElement(0, 0) * getElement(1, 1) - getElement(0, 1) * getElement(1, 0);
        } else {
            double det = 0d;
            for (int i = 0; i < getColSize(); i++) {
                det += getElement(0, i) * cofactor(0, i);
            }
            return det;
        }
    }

    public double cofactor(int i, int j) throws MatrixException {
        Matrix miner = this.miner(i, j);
        return Math.pow(-1, i + j) * miner.determinant();
    }

    public Matrix miner(int i, int j) {
        Matrix miner = new Matrix(getRowSize() - 1, getColSize() - 1);
        int r = 0, c = 0;
        for (int a = 0; a < getRowSize(); a++) {
            if (a == i) {
                continue;
            }
            c = 0;
            for (int b = 0; b < getColSize(); b++) {
                if (b == j) {
                    continue;
                }
                miner.apply(r, c, getElement(a, b));
                c++;
            }
            r++;
        }
        return miner;
    }

    public void display() {
        int numRows = getRowSize();
        int numCols = getColSize();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(getElement(i, j) + "\t");
            }
            System.out.println(); // Move to the next row
        }
    }

    public Matrix adjoint() throws MatrixException {
        Matrix c = new Matrix(getRowSize(), getColSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColSize(); j++) {
                c.apply(i, j, cofactor(i, j));
            }
        }

        return c.transpose();

    }


    public Matrix inverse() throws MatrixException {
        return adjoint().multiply((1 / determinant()));
    }

}
