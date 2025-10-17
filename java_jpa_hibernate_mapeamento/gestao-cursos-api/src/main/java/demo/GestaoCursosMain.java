package demo;

import entities.*;
import models.AlunoModel;
import models.CursoModel;
import models.ProfessorModel;

import java.util.Arrays;
import java.util.Date;

public class GestaoCursosMain {

    public static void main(String[] args) {

        // --- Instanciando Models ---
        AlunoModel alunoModel = new AlunoModel();
        ProfessorModel professorModel = new ProfessorModel();
        CursoModel cursoModel = new CursoModel();

        // --- Criando um aluno ---
        Aluno aluno = new Aluno();
        aluno.setNome("Felipe Brevio");
        aluno.setNascimento(new Date());

        // Endereço do aluno
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua das Palmeiras");
        endereco.setNumero("123");
        endereco.setCidade("Florianópolis");
        endereco.setEstado("SC");
        endereco.setCep(88000000);
        endereco.setAluno(aluno); // vínculo bidirecional

        // Telefone do aluno
        Telefone telefone = new Telefone();
        telefone.setNumero("4899999-9999");
        telefone.setAluno(aluno); // vínculo bidirecional

        aluno.setEnderecos(Arrays.asList(endereco));
        aluno.setTelefones(Arrays.asList(telefone));

        alunoModel.create(aluno);

        // --- Criando um professor ---
        Professor professor = new Professor();
        professor.setNomeCompleto("João da Silva");
        professor.setEmail("teste@teste");
        professor.setMatricula("999999999");

        professorModel.create(professor);

        // --- Criando o material do curso ---
        MaterialCurso material = new MaterialCurso();
        material.setUrl("http://material.cursojava.com");

        // --- Criando o curso ---
        Curso curso = new Curso();
        curso.setNome("Curso de Java Completo");
        curso.setProfessor(professor);
        curso.setMaterial(material);
        curso.setAlunos(Arrays.asList(aluno));

        // Vinculando curso ao material (relação 1-1)
        material.setCurso(curso);

        cursoModel.create(curso);

        System.out.println("\n✅ População de dados concluída com sucesso!");
    }
}
