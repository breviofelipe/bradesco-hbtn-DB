package models;

import entities.Aluno;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class AlunoModel {

    private static final String PERSISTENCE_UNIT = "gestao-cursos-jpa";

    public void create(Aluno aluno) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno criado com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao criar um aluno !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Aluno findById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();
        Aluno aluno = null;

        try {
            aluno = em.find(Aluno.class, id);
            if (aluno != null) {
                System.out.println("Aluno encontrado: " + aluno.getNome());
            } else {
                System.out.println("Aluno não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return aluno;
    }

    public List<Aluno> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();
        List<Aluno> alunos = new ArrayList<>();

        try {
            alunos = em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
            System.out.println("Total de alunos encontrados: " + alunos.size());
        } catch (Exception e) {
            System.err.println("Erro ao listar alunos !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return alunos;
    }

    public void update(Aluno aluno) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando atualização do aluno");
            em.getTransaction().begin();
            em.merge(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno atualizado com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar aluno !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando atualização");
        }
    }

    public void delete(Aluno aluno) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando exclusão de aluno");
            em.getTransaction().begin();
            Aluno a = em.find(Aluno.class, aluno.getId());
            if (a != null) {
                em.remove(a);
                System.out.println("Aluno removido com sucesso !!!");
            } else {
                System.out.println("Aluno não encontrado para exclusão !!!");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ao excluir aluno !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando exclusão");
        }
    }
}
