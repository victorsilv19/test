package com.projeto.controller;

import com.projeto.model.Aluno;
import com.projeto.model.Curso;
import com.projeto.model.Matricula;
import com.projeto.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

public class MatriculaController {

    public static void matricular(Scanner scanner) {
        EntityManager em = JPAUtil.getEntityManager();

        AlunoController.listarAlunos();
        System.out.print("Digite o ID do aluno: ");
        Long alunoId = scanner.nextLong();
        scanner.nextLine();

        CursoController.listarCursos();
        System.out.print("Digite o ID do curso: ");
        Long cursoId = scanner.nextLong();
        scanner.nextLine();

        Aluno aluno = em.find(Aluno.class, alunoId);
        Curso curso = em.find(Curso.class, cursoId);

        if (aluno == null || curso == null) {
            System.out.println("Aluno ou curso não encontrado.");
            em.close();
            return;
        }

        TypedQuery<Matricula> query = em.createQuery(
                "SELECT m FROM Matricula m WHERE m.aluno = :aluno AND m.curso = :curso", Matricula.class);
        query.setParameter("aluno", aluno);
        query.setParameter("curso", curso);

        if (!query.getResultList().isEmpty()) {
            System.out.println("Este aluno já está matriculado neste curso.");
            em.close();
            return;
        }

        em.getTransaction().begin();
        Matricula matricula = new Matricula(aluno, curso);
        em.persist(matricula);
        em.getTransaction().commit();
        em.close();

        System.out.println("Aluno matriculado com sucesso!");
    }

    public static void listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Matricula> matriculas = em.createQuery("FROM Matricula", Matricula.class).getResultList();

        System.out.println("\n--- MATRÍCULAS ---");
        for (Matricula m : matriculas) {
            System.out.println("ID: " + m.getId()
                    + " | Aluno: " + m.getAluno().getNome()
                    + " | Curso: " + m.getCurso().getNome());
        }
        em.close();
    }

    public static void excluir(Scanner scanner) {
        EntityManager em = JPAUtil.getEntityManager();
        listar();
        System.out.print("Digite o ID da matrícula que deseja excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Matricula matricula = em.find(Matricula.class, id);

        if (matricula != null) {
            em.getTransaction().begin();
            em.remove(matricula);
            em.getTransaction().commit();
            System.out.println("Matrícula excluída com sucesso!");
        } else {
            System.out.println("Matrícula não encontrada.");
        }
        em.close();
    }

    public static void buscarPorCpf(Scanner scanner) {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.print("Digite o CPF do aluno: ");
        String cpf = scanner.nextLine();

        TypedQuery<Aluno> query = em.createQuery("FROM Aluno a WHERE a.cpf = :cpf", Aluno.class);
        query.setParameter("cpf", cpf);

        List<Aluno> resultado = query.getResultList();

        if (resultado.isEmpty()) {
            System.out.println("Aluno com CPF informado não encontrado.");
            em.close();
            return;
        }

        Aluno aluno = resultado.get(0);
        System.out.println("Aluno encontrado: " + aluno.getNome());

        List<Matricula> matriculas = em.createQuery(
                "FROM Matricula m WHERE m.aluno = :aluno", Matricula.class)
                .setParameter("aluno", aluno)
                .getResultList();

        if (matriculas.isEmpty()) {
            System.out.println("Este aluno não possui matrículas.");
        } else {
            System.out.println("Matrículas do aluno:");
            for (Matricula m : matriculas) {
                System.out.println("- Curso: " + m.getCurso().getNome());
            }
        }
        em.close();
    }

    public static void limparTudo() {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Matricula").executeUpdate();
        em.createQuery("DELETE FROM Curso").executeUpdate();
        em.createQuery("DELETE FROM Aluno").executeUpdate();
        em.getTransaction().commit();
        em.close();
        System.out.println("Todos os dados foram excluídos com sucesso.");
    }
}
