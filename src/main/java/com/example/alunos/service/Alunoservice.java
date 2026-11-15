package com.example.alunos.service;

import com.example.alunos.dto.AlunoRequest;
import com.example.alunos.dto.AlunoResponse;
import com.example.alunos.dto.MatriculaDTO;
import com.example.alunos.entity.Aluno;
import com.example.alunos.entity.Matricula;
import com.example.alunos.mapper.Alunomapper;
import com.example.alunos.repository.AlunoRepository;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Alunoservice {
   private final AlunoRepository alunoRepository;
   private final Alunomapper alunomapper;

    public Alunoservice(AlunoRepository alunoRepository, Alunomapper alunomapper) {
       this.alunoRepository = alunoRepository;
       this.alunomapper = alunomapper;

   }
   public AlunoResponse salvar(AlunoRequest request) {
       Aluno aluno = alunomapper.toEntity(request);
       alunoRepository.save(aluno);
       return alunomapper.toResponse(aluno);

   }
   public List<AlunoResponse> listaTodos(){
       return alunoRepository.findAll().stream().map(alunomapper::toResponse).toList();
   }
   public List<MatriculaDTO> listarMatriculas(Long id){
       Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("aluno nao encontado"));
       return aluno.getMatriculas().stream().map(m -> new MatriculaDTO(m.getId(), m.getCodigoMatricula(), m.getNomeCurso(),m.getDataInicio())).toList();

   }
   public void remover(Long id){
       if(!alunoRepository.findById(id).isPresent()){
           throw new EntityNotFoundException("aluno nao encontrado");
       }
       alunoRepository.deleteById(id);
   }
 public  AlunoResponse atualizar(long id, AlunoRequest request){
     Aluno a = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("aluno nao encontado"));
     a.setNome(request.nome());
     a.setTelefone(request.telefone());
     a.setDataNascimento(request.dataNascimento());

     for( MatriculaDTO m : request.matriculas()){
         Matricula matricula = new Matricula();
         matricula.setCodigoMatricula(m.codigoMatricula());
         matricula.setNomeCurso(m.nomeCurso());
         matricula.setDataInicio(m.dataInicio());
         a.getMatriculas().add(matricula);
     }

     return alunomapper.toResponse(alunoRepository.save(a));



 }
    public MatriculaDTO matricular(Long id, MatriculaDTO request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        Matricula novaMatricula = new Matricula();
        novaMatricula.setNomeCurso(request.nomeCurso()); // Pega exatamente do DTO


        LocalDate dataInicio = request.dataInicio() != null ? request.dataInicio() : LocalDate.now();
        novaMatricula.setDataInicio(dataInicio);


        aluno.getMatriculas().add(novaMatricula);
        alunoRepository.save(aluno);
        // Como o Aluno salva a Matrícula por Cascade, ela ganhará um ID no banco aqui

        return new MatriculaDTO(novaMatricula.getId(), novaMatricula.getCodigoMatricula(), novaMatricula.getNomeCurso(), novaMatricula.getDataInicio());
    }
}
