package guru.springframework.matrix.domain;

import java.util.Arrays;

public class GaussMethodResponse {
    private double[][] matrix;
    private double[] values;

    public GaussMethodResponse() {
    }

    public GaussMethodResponse(double[][] matrix, double[] values) {
        this.matrix = matrix;
        this.values = values;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "GaussMethodResponse{" +
                "matrix=" + Arrays.toString(matrix) +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
