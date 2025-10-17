package models;

import entities.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class PessoaModel {
    public void create(Pessoa p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação (Pessoa)");
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            System.out.println("Pessoa criada com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao criar pessoa !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando transação (Pessoa)");
        }
    }

    public void update(Pessoa p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando atualização (Pessoa)");
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            System.out.println("Pessoa atualizada com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar pessoa !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando atualização (Pessoa)");
        }
    }

    public void delete(Pessoa p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando exclusão (Pessoa)");
            em.getTransaction().begin();
            Pessoa pessoa = em.find(Pessoa.class, p.getId());
            if (pessoa != null) {
                em.remove(pessoa);
                System.out.println("Pessoa removida com sucesso !!!");
            } else {
                System.out.println("Pessoa não encontrada para exclusão !!!");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ao excluir pessoa !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando exclusão (Pessoa)");
        }
    }

    public Pessoa findById(Pessoa p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        Pessoa pessoa = null;

        try {
            pessoa = em.find(Pessoa.class, p.getId());
        } catch (Exception e) {
            System.err.println("Erro ao buscar pessoa !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return pessoa;
    }

    public List<Pessoa> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        List<Pessoa> pessoas = new ArrayList<>();

        try {
            pessoas = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar pessoas !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return pessoas;
    }

}
