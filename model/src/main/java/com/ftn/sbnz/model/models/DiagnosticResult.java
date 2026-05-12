package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.ComponentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Rezultat dijagnostike - cinjenica koju pravila ubacuju u radnu memoriju
 * nakon analize DTC kodova (Forward Chaining Nivo 1).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticResult implements Serializable {

    private static final long serialVersionUID = 1L;

    // Status komponente (npr. EGR_CLOGGED, DPF_ASH_OVERLOADED)
    private ComponentStatus componentStatus;

    // ID vozila
    private String vehicleId;
}
