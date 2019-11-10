package guru.springframework.matrix.service.impl;


import guru.springframework.matrix.service.MatrixService;
import guru.springframework.matrix.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MatrixServiceImpl implements MatrixService {


    private boolean isIdentityMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                if (row == col && matrix[row][col] != 1)
                    return false;
                else if (row != col && matrix[row][col] != 0)
                    return false;
            }
        }
        return true;
    }


    private boolean isSparseMatrix(int[][] matrix) {
        int zeros = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 0) {
                    zeros++;
                }
            }
        }
        int matrixColumn = matrix[0].length;
        return zeros > (matrix.length * matrixColumn) / 2;
    }

    private boolean checkCorrectMatrixByType(int[][] matrix, String matrixType) {
        if (matrixType.equals("identity")) {
            return isIdentityMatrix(matrix);
        } else if (matrixType.equals("sparse")) {
            return isSparseMatrix(matrix);
        } else {
            return true;
        }
    }

    @Override
    public int[][] generateIdentityMatrix(int size) {
        int[][] matrix = new int[size][size];
        int row, col;
        for (row = 0; row < size; row++) {
            for (col = 0; col < size; col++) {
                if (row == col) {
                    matrix[row][col] = 1;
                } else {
                    matrix[row][col] = 0;
                }
            }
        }
        return matrix;
    }

    @Override
    public int[][] parseCsvFileAndFillMatrix(String filename) {
        return FileUtils.parseCSVFileAndFillMatrix(filename);
    }

    @Override
    public void multMatrixByTypeAndResultExportToCsvFile(int[][] matrix, int[][] dynamicMatrix, String matrixType) {
        if (!checkCorrectMatrixByType(dynamicMatrix, matrixType)) {
            throw new RuntimeException("Incorrect type of matrix: " + matrixType);
        }
        int mRowLength = matrix.length;
        int mColLength = matrix[0].length;
        int identRowLength = dynamicMatrix.length;
        int identColLength = dynamicMatrix[0].length;

        if (mColLength != identRowLength) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        int[][] result = new int[mColLength][identColLength];
        for (int i = 0; i < mRowLength; i++) {
            for (int j = 0; j < identColLength; j++) {
                result[i][j] = 0;
                for (int k = 0; k < mColLength; k++) {
                    result[i][j] += matrix[i][k] * dynamicMatrix[k][j];
                }
            }
        }
        FileUtils.exportResultMatrixToCsvFile(result);
    }

}
