package com.example.api_medecin.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class RendezVousId implements Serializable {

    private Long medecinId;
    private Long patientId;

    public RendezVousId() {
    }

    public RendezVousId(Long medecinId, Long patientId) {
        this.medecinId = medecinId;
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RendezVousId)) return false;
        RendezVousId that = (RendezVousId) o;
        return Objects.equals(medecinId, that.medecinId) &&
               Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medecinId, patientId);
    }

    // Constructeurs, equals(), hashCode()
}