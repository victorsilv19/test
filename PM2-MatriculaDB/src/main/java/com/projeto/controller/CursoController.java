package com.projeto.controller;

import com.projeto.model.Curso;
import com.projeto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Scanner;

public class CursoController {

    public static void cadastrarCurso(Scanner scanner) {
        EntityManager em = JPAUtil.getEntityManager();

        String nome;
        do {
            System.out.print("Nome do curso (apenas letras): ");
            nome = scanner.nextLine();
        } while (!nome.matches("[a-zA-ZÀ-ÿ ]+"));

        em.getTransaction().begin();
        em.persist(new Curso(nome));
        em.getTransaction().commit();
        em.close();

        System.out.println("Curso cadastrado com sucesso!");
    }

    public static void listarCursos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Curso> cursos = em.createQuery("FROM Curso", Curso.class).getResultList();
        cursos.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
        em.close();
    }
}
