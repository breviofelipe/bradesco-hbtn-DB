package models;

import entities.Professor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class ProfessorModel {

    private static final String PERSISTENCE_UNIT = "gestao-cursos-jpa";

    public void create(Professor professor) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(professor);
            em.getTransaction().commit();
            System.out.println("Professor criado com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao criar professor !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Professor findById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();
        Professor professor = null;

        try {
            professor = em.find(Professor.class, id);
            if (professor != null) {
                System.out.println("Professor encontrado: " + professor.getNomeCompleto());
            } else {
                System.out.println("Professor não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar professor !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return professor;
    }

    public List<Professor> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();
        List<Professor> professores = new ArrayList<>();

        try {
            professores = em.createQuery("SELECT p FROM Professor p", Professor.class).getResultList();
            System.out.println("Total de professores encontrados: " + professores.size());
        } catch (Exception e) {
            System.err.println("Erro ao listar professores !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return professores;
    }

    public void update(Professor professor) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando atualização do professor");
            em.getTransaction().begin();
            em.merge(professor);
            em.getTransaction().commit();
            System.out.println("Professor atualizado com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar professor !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando atualização");
        }
    }

    public void delete(Professor professor) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando exclusão de professor");
            em.getTransaction().begin();
            Professor p = em.find(Professor.class, professor.getId());
            if (p != null) {
                em.remove(p);
                System.out.println("Professor removido com sucesso !!!");
            } else {
                System.out.println("Professor não encontrado para exclusão !!!");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ao excluir professor !!! " + e.getMessage());
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
