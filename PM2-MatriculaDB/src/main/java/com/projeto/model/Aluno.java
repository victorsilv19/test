package com.projeto.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String ra;
    private String cpf;
    private String email;
    private String telefone;

    @ManyToMany(mappedBy = "alunos")
    private Set<Curso> cursos = new HashSet<>();

    public Aluno() {}

    public Aluno(String nome, String ra, String cpf, String email, String telefone) {
        this.nome = nome;
        this.ra = ra;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getRa() { return ra; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }

    public void setNome(String nome) { this.nome = nome; }
    public void setRa(String ra) { this.ra = ra; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Set<Curso> getCursos() { return cursos; }
}