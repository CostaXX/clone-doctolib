package com.example.api_medecin.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RendezVousByDateDTO(LocalDate date, List<LocalTime> heures) {
    
}
