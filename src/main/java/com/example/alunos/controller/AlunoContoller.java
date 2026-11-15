package com.example.alunos.controller;

import com.example.alunos.dto.AlunoRequest;
import com.example.alunos.dto.AlunoResponse;
import com.example.alunos.dto.MatriculaDTO;
import com.example.alunos.service.Alunoservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alunos")
public class AlunoContoller {

    private Alunoservice alunoService;

    public AlunoContoller(Alunoservice alunoService) {
        this.alunoService = alunoService;

    }

    @PostMapping()
    public ResponseEntity<AlunoResponse> criar (@RequestBody AlunoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.salvar(request));


    }
    @GetMapping()
    public List<AlunoResponse> listarTodos(){
        return alunoService.listaTodos();
    }
    @GetMapping("{id}/matriculas")
    public List<MatriculaDTO> listarMatriculas(@PathVariable Long id){
      return alunoService.listarMatriculas(id);

    }
    @PutMapping("{id}")
public ResponseEntity<AlunoResponse> atualizar(@PathVariable Long id, @RequestBody AlunoRequest request){
    return ResponseEntity.ok(alunoService.atualizar(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        alunoService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/matriculas")
    public ResponseEntity<MatriculaDTO> matricularAluno(@PathVariable Long id, @RequestBody MatriculaDTO request) {
        // O seu alunoService vai buscar o aluno pelo ID, criar a matrícula com os dados do request,
        // vincular ao aluno e salvar no banco de dados.
        MatriculaDTO novaMatricula = alunoService.matricular(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMatricula);
    }
}
