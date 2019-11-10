package guru.springframework.matrix.utils;

import guru.springframework.matrix.domain.MatrixResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils implements Serializable {
    private static final long serialVersionUID = -5054749880960511861L;
    private static String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/";


    public static void uploadFileToResourceFolder(MatrixResponse matrixResponse) {
        File uploadRootDir = new File(UPLOAD_DIR);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        String filename = matrixResponse.getFile().getOriginalFilename();
        if (filename != null && filename.length() > 0) {
            try {
                String path = uploadRootDir.getAbsolutePath() + File.separator + filename;
                File serverFile = new File(path);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(matrixResponse.getFile().getBytes());
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error upload file");
            }
        }
    }

    public static int[][] parseCSVFileAndFillMatrix(String fileName) {
        List<String[]> array = new ArrayList<>();
        int[][] matrix;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                FileUtils.class.getResourceAsStream("/static/" + fileName)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                array.add(values);
            }
            matrix = new int[array.size()][array.get(0).length];
            for (int row = 0; row < array.size(); row++) {
                for (int col = 0; col < array.get(row).length; col++) {
                    matrix[row][col] = Integer.parseInt(array.get(row)[col]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException("Error Parse File " + e.getMessage());
        }
        return matrix;
    }

    public static void exportResultMatrixToCsvFile(int[][] result) {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                builder.append(result[row][col]);
                if (col < result[row].length - 1)
                    builder.append(",");
            }
            builder.append("\n");
        }
        writeResultMatrixInCsv(builder);

    }

    public static void exportResultGaussToCsvFile(double[] result) {
        StringBuilder builder = new StringBuilder();

        for (int col = 0; col < result.length; col++) {
            builder.append("x" + (col + 1));
            builder.append(",");
        }
        builder.append("\n");

        for (int col = 0; col < result.length; col++) {
            builder.append(result[col]);
            builder.append(",");
        }

        writeResultMatrixInCsv(builder);

    }

    private static void writeResultMatrixInCsv(StringBuilder builder) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(UPLOAD_DIR + "result.csv"));
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
