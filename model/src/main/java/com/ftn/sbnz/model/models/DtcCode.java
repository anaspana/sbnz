package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Dijagnosticki kod greske (DTC) koji vozilo prijavljuje.
 * Npr. P0401 = EGR nedovoljan protok, P242F = DPF preopterecen pepelom.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtcCode implements Serializable {

    private static final long serialVersionUID = 1L;

    // Kod greske (npr. "P0401")
    private String code;

    // ID vozila na koje se odnosi ovaj DTC
    private String vehicleId;
}
