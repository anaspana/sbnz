package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.DiagnosticResult;
import com.ftn.sbnz.model.models.ToolRecommendation;
import com.ftn.sbnz.model.models.TuningAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO koji servis vraca klijentu nakon pokretanja Drools sesije.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisResponse {

    private String vehicleId;

    // Dijagnosticki rezultati (status komponenti)
    private List<DiagnosticResult> diagnosticResults;

    // Preporucene akcije softverske intervencije
    private List<TuningAction> tuningActions;

    // Preporuceni alati
    private List<ToolRecommendation> toolRecommendations;
}