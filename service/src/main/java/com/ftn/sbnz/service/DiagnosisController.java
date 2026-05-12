package com.ftn.sbnz.service;

import com.ftn.sbnz.model.dto.DiagnosisRequest;
import com.ftn.sbnz.model.dto.DiagnosisResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagnosis")
@CrossOrigin
public class DiagnosisController {

    private static final Logger log = LoggerFactory.getLogger(DiagnosisController.class);

    private final DiagnosisService diagnosisService;

    @Autowired
    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    /**
     * POST /api/diagnosis
     * PROVERI KAKO MOZE DA SE ODRADI VFc
     * Prima podatke o vozilu i DTC kodove, pokrece Drools sesiju
     * i vraca dijagnostiku, preporucene akcije i alate.
     *
     * Primer request body-ja:
     * {
     *   "vehicleId": "VW-001",
     *   "ecuType": "BOSCH_EDC17",
     *   "vehicleBrand": "Volkswagen",
     *   "mileage": 180000,
     *   "maxGearboxTorque": 400,
     *   "dtcCodes": ["P0401", "P242F"]
     * }
     */
    @PostMapping
    public ResponseEntity<DiagnosisResponse> diagnose(@RequestBody DiagnosisRequest request) {
        log.info("Primljen zahtev za dijagnostiku vozila: {}", request.getVehicleId());
        DiagnosisResponse response = diagnosisService.diagnose(request);
        return ResponseEntity.ok(response);
    }
}
