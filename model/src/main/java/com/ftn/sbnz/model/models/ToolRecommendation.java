package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Preporuceni alat i protokol za rad sa ECU-om - cinjenica koju ubacuju
 * pravila Nivo 3 (Forward Chaining).
 * Npr. PCMflash via Bench mode za EDC17, KESSv2 via OBD za EDC16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolRecommendation implements Serializable {

    private static final long serialVersionUID = 1L;

    // Tip akcije za koju je alat preporucen (npr. "EGR_OFF_RECOMMENDED")
    private String actionType;

    // Naziv alata (npr. "PCMflash", "KESSv2", "daVinci")
    private String toolName;

    // Protokol/nacin rada (npr. "Bench mode", "OBD port")
    private String protocol;

    // Dodatne napomene
    private String notes;

    // ID vozila
    private String vehicleId;
}