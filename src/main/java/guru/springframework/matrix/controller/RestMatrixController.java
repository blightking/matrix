package guru.springframework.matrix.controller;


import guru.springframework.matrix.domain.MatrixResponse;
import guru.springframework.matrix.service.MatrixService;
import guru.springframework.matrix.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RestMatrixController {
    private final MatrixService matrixService;

    public RestMatrixController(MatrixService matrixService) {
        this.matrixService = matrixService;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFileToResourceAndSaveFileNameInSession(
            @ModelAttribute MatrixResponse matrixResponse,
            HttpServletRequest request
    ) {
        FileUtils.uploadFileToResourceFolder(matrixResponse);
        request.getSession().setAttribute("filename", matrixResponse.getFile().getOriginalFilename());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @RequestMapping(value = "/matrix", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> multDifferenceMatrix(
            @RequestBody MatrixResponse mResponseBody, HttpServletRequest request
    ) {
        String matrixType = mResponseBody.getTypeMatrix();
        int[][] matrix = mResponseBody.getArr();
        if (matrix == null) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        switch (matrixType) {
            case "identity":
                int[][] identityMatrix = matrixService.generateIdentityMatrix(matrix.length);
                matrixService.multMatrixByTypeAndResultExportToCsvFile(matrix, identityMatrix, matrixType);
                break;
            case "sparse":
                int[][] sparseMatrix = matrixService.parseCsvFileAndFillMatrix("sparse.csv");
                matrixService.multMatrixByTypeAndResultExportToCsvFile(matrix, sparseMatrix, matrixType);
                break;
            case "stat":
                String filename = request.getSession().getAttribute("filename").toString().trim();
                int[][] statMatrixInFile = matrixService.parseCsvFileAndFillMatrix(filename);
                matrixService.multMatrixByTypeAndResultExportToCsvFile(matrix, statMatrixInFile, matrixType);
                break;

            default:
                throw new RuntimeException(matrixType + " is not exist");

        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
