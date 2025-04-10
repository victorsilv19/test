package com.projeto;

import com.projeto.controller.AlunoController;
import com.projeto.controller.CursoController;
import com.projeto.controller.MatriculaController;
import com.projeto.util.JPAUtil;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos");
            System.out.println("3. Cadastrar curso");
            System.out.println("4. Listar cursos");
            System.out.println("5. Matricular aluno em curso");
            System.out.println("6. Listar matrículas");
            System.out.println("7. Editar nome de aluno");
            System.out.println("8. Excluir matrícula");
            System.out.println("9. Buscar matrícula por CPF");
            System.out.println("10. Limpar todos os dados");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> AlunoController.cadastrarAluno(scanner);
                case 2 -> AlunoController.listarAlunos();
                case 3 -> CursoController.cadastrarCurso(scanner);
                case 4 -> CursoController.listarCursos();
                case 5 -> MatriculaController.matricular(scanner);
                case 6 -> MatriculaController.listar();
                case 7 -> AlunoController.editarNome(scanner);
                case 8 -> MatriculaController.excluir(scanner);
                case 9 -> MatriculaController.buscarPorCpf(scanner);
                case 10 -> MatriculaController.limparTudo();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        JPAUtil.close();
    }
}


