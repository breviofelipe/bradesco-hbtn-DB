package models;

import entities.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class CursoModel {

    private static final String PERSISTENCE_UNIT = "gestao-cursos-jpa";

    public void create(Curso curso) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
            System.out.println("Curso criado com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao criar o curso !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Curso findById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();
        Curso curso = null;

        try {
            curso = em.find(Curso.class, id);
            if (curso != null) {
                System.out.println("Curso encontrado: " + curso.getNome());
            } else {
                System.out.println("Curso não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar curso !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return curso;
    }

    public List<Curso> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();
        List<Curso> cursos = new ArrayList<>();

        try {
            cursos = em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
            System.out.println("Total de cursos encontrados: " + cursos.size());
        } catch (Exception e) {
            System.err.println("Erro ao listar cursos !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return cursos;
    }

    public void update(Curso curso) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando atualização do curso");
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
            System.out.println("Curso atualizado com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar curso !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando atualização");
        }
    }

    public void delete(Curso curso) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando exclusão do curso");
            em.getTransaction().begin();
            Curso c = em.find(Curso.class, curso.getId());
            if (c != null) {
                em.remove(c);
                System.out.println("Curso removido com sucesso !!!");
            } else {
                System.out.println("Curso não encontrado para exclusão !!!");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ao excluir curso !!! " + e.getMessage());
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
