package guru.springframework.matrix.service.impl;


import guru.springframework.matrix.service.GaussMethodService;
import guru.springframework.matrix.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class GaussMethodServiceImpl implements GaussMethodService {
    /*
    Description: Нули ниже главной диагонали
     */
    private static final double EPSILON = 1e-10;

    private double[] backSubstitution(double[][] matrix, double[] values, int n) {
        double[] result = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * result[j];
            }
            result[i] = (values[i] - sum) / matrix[i][i];
        }
        return result;
    }

    @Override
    public void solveGuessMethodAndResultExportToCsvFile(double[][] matrix, double[] values) {
        if (matrix.length != values.length) {
            throw new RuntimeException("Error, matrix and values must equals");
        }
        int vLength = values.length;
        for (int p = 0; p < vLength; p++) {
            // Find pivot row and swap
            int pivot = p;
            for (int i = p + 1; i < vLength; i++) {
                if (Math.abs(matrix[i][p]) > Math.abs(matrix[pivot][p])) {
                    pivot = i;
                }
            }
            double[] temp = matrix[p];
            matrix[p] = matrix[pivot];
            matrix[pivot] = temp;
            double swap = values[p];
            values[p] = values[pivot];
            values[pivot] = swap;

            // singular or nearly singular
            if (Math.abs(matrix[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within Matrix(A) and Values(p)
            for (int i = p + 1; i < vLength; i++) {
                double alpha = matrix[i][p] / matrix[p][p];
                values[i] -= alpha * values[p];
                for (int j = p; j < vLength; j++) {
                    matrix[i][j] -= alpha * matrix[p][j];
                }
            }
        }
        double[] result = backSubstitution(matrix, values, vLength);
        FileUtils.exportResultGaussToCsvFile(result);
    }
}
