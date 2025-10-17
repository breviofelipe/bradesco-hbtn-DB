package models;

import entities.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class ProdutoModel {
    public void create(Produto p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            System.out.println("Produto criado com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao criar o produto !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public void update(Produto p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando atualização");
            em.getTransaction().begin();
            em.merge(p); // Atualiza o produto no banco
            em.getTransaction().commit();
            System.out.println("Produto atualizado com sucesso !!!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o produto !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando atualização");
        }
    }

    public void delete(Produto p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando exclusão");
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, p.getId());
            if (produto != null) {
                em.remove(produto);
                System.out.println("Produto removido com sucesso !!!");
            } else {
                System.out.println("Produto não encontrado para exclusão !!!");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ao remover o produto !!! " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando exclusão");
        }
    }

    public Produto findById(Produto p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        Produto produto = null;

        try {
            produto = em.find(Produto.class, p.getId());
        } catch (Exception e) {
            System.err.println("Erro ao buscar o produto !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return produto;
    }

    public List<Produto> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        List<Produto> produtos = new ArrayList<Produto>();
        try {
            produtos = em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar os produtos !!! " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        return produtos;
    }
}
