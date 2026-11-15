package com.example.alunos.dto;

import java.time.LocalDate;

public record MatriculaDTO(long id, String codigoMatricula, String nomeCurso, LocalDate dataInicio) {
}
