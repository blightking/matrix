package guru.springframework.matrix.controller;


import guru.springframework.matrix.domain.GaussMethodResponse;
import guru.springframework.matrix.service.GaussMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestGaussMethodController {
    private final GaussMethodService gaussMethodService;

    public RestGaussMethodController(GaussMethodService gaussMethodService) {
        this.gaussMethodService = gaussMethodService;
    }

    @PostMapping("/gauss/solve")
    public ResponseEntity<?> solveGaussMethod(
            @RequestBody GaussMethodResponse gaussMethodResponse
    ) {
        double[][] matrix = gaussMethodResponse.getMatrix();
        double[] values = gaussMethodResponse.getValues();
        gaussMethodService.solveGuessMethodAndResultExportToCsvFile(matrix, values);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
