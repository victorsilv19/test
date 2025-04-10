package com.projeto.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany
    private Set<Aluno> alunos = new HashSet<>();

    public Curso() {}

    public Curso(String nome) {
        this.nome = nome;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Set<Aluno> getAlunos() { return alunos; }
    public void setNome(String nome) { this.nome = nome; }
}