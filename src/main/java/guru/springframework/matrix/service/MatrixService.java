package guru.springframework.matrix.service;

public interface MatrixService {

    int[][] parseCsvFileAndFillMatrix(String filename);

    int[][] generateIdentityMatrix(int size);

    void multMatrixByTypeAndResultExportToCsvFile(int[][] firstMatrix, int[][] secondMatrix, String matrixType);
}
