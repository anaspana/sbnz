package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Preporucena akcija softverske intervencije - cinjenica koju ubacuju
 * pravila Nivo 2 (Forward Chaining).
 * Npr. "EGR_OFF_RECOMMENDED", "DPF_OFF_RECOMMENDED".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TuningAction implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tip akcije (npr. "EGR_OFF_RECOMMENDED", "DPF_OFF_RECOMMENDED", "ECO_MAP_RECOMMENDED") */
    private String actionType;

    /** Opis za korisnika */
    private String description;

    /** ID vozila */
    private String vehicleId;
}
