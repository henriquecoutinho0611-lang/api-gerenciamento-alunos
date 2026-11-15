package com.example.alunos.mapper;

import com.example.alunos.dto.AlunoRequest;
import com.example.alunos.dto.AlunoResponse;

import com.example.alunos.entity.Aluno;


import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface Alunomapper {

   Aluno toEntity(AlunoRequest request);

   AlunoResponse toResponse(Aluno aluno);

}
