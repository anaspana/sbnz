package com.ftn.sbnz.service;

import com.ftn.sbnz.model.dto.DiagnosisRequest;
import com.ftn.sbnz.model.dto.DiagnosisResponse;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.enums.EcuType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosisService {

    private static final Logger log = LoggerFactory.getLogger(DiagnosisService.class);

    private final KieContainer kieContainer;

    @Autowired
    public DiagnosisService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public DiagnosisResponse diagnose(DiagnosisRequest request) {
        log.info("Pokretanje dijagnostike za vozilo: {}", request.getVehicleId());

        KieSession kieSession = kieContainer.newKieSession("forwardKsession");

        try {
            // Fabrickii limiti po ECU tipu
            VehicleProfile profile = buildProfile(request);
        profile.setVehicleId(request.getVehicleId());
            kieSession.insert(profile);

            // Ubacujemo sve DTC kodove
            for (String code : request.getDtcCodes()) {
                kieSession.insert(new DtcCode(code, request.getVehicleId()));
            }

            kieSession.fireAllRules();

            // Prikupljamo rezultate iz radne memorije
            Collection<?> facts = kieSession.getObjects();

            List<DiagnosticResult> diagnosticResults = facts.stream()
                    .filter(f -> f instanceof DiagnosticResult)
                    .map(f -> (DiagnosticResult) f)
                    .collect(Collectors.toList());

            List<TuningAction> tuningActions = facts.stream()
                    .filter(f -> f instanceof TuningAction)
                    .map(f -> (TuningAction) f)
                    .collect(Collectors.toList());

            List<ToolRecommendation> toolRecommendations = facts.stream()
                    .filter(f -> f instanceof ToolRecommendation)
                    .map(f -> (ToolRecommendation) f)
                    .collect(Collectors.toList());

            log.info("Dijagnostika zavrsena: {} rezultata, {} akcija, {} alata",
                    diagnosticResults.size(), tuningActions.size(), toolRecommendations.size());

            return new DiagnosisResponse(
                    request.getVehicleId(),
                    diagnosticResults,
                    tuningActions,
                    toolRecommendations
            );

        } finally {
            kieSession.dispose();
        }
    }

    /**
     * Kreira VehicleProfile i postavlja fabricke limite na osnovu ECU tipa.
     */
    private VehicleProfile buildProfile(DiagnosisRequest request) {
        VehicleProfile profile = new VehicleProfile();
        profile.setEcuType(request.getEcuType());
        profile.setVehicleBrand(request.getVehicleBrand());
        profile.setMileage(request.getMileage());
        profile.setMaxGearboxTorque(request.getMaxGearboxTorque());

        // Fabrici limiti po ECU profilu (ovo ce u Domacem 4 biti Templates)
        if (request.getEcuType() == EcuType.BOSCH_EDC17) {
            profile.setMaxExhaustTemp(850.0);
            profile.setMaxBoostPressure(1.8);
        } else if (request.getEcuType() == EcuType.BOSCH_EDC16) {
            profile.setMaxExhaustTemp(800.0);
            profile.setMaxBoostPressure(1.5);
        } else {
            // Siemens SID807 i ostali
            profile.setMaxExhaustTemp(820.0);
            profile.setMaxBoostPressure(1.6);
        }

        return profile;
    }
}