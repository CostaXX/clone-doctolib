package com.example.api_medecin.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class AffecterId implements Serializable {

    private Long medecinId;
    private Long cabinetId;

    public AffecterId() {
    }

    public AffecterId(Long medecinId, Long cabinetId) {
        this.medecinId = medecinId;
        this.cabinetId = cabinetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AffecterId)) return false;
        AffecterId that = (AffecterId) o;
        return Objects.equals(medecinId, that.medecinId) &&
               Objects.equals(cabinetId, that.cabinetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medecinId, cabinetId);
    }
}
