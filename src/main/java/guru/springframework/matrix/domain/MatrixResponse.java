package guru.springframework.matrix.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class MatrixResponse {
    private int[][] arr;
    private String typeMatrix;
    private MultipartFile file;


    public MatrixResponse() {
    }

    public MatrixResponse(int[][] arr, MultipartFile file) {
        this.arr = arr;
        this.file = file;
    }

    public MatrixResponse(int[][] arr, String typeMatrix, MultipartFile file) {
        this.arr = arr;
        this.typeMatrix = typeMatrix;
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int[][] getArr() {
        return arr;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }


    public String getTypeMatrix() {
        return typeMatrix;
    }

    public void setTypeMatrix(String typeMatrix) {
        this.typeMatrix = typeMatrix;
    }

    @Override
    public String toString() {
        return "MatrixResponse{" +
                "arr=" + Arrays.toString(arr) +
                ", typeMatrix='" + typeMatrix + '\'' +
                ", file=" + file +
                '}';
    }
}
