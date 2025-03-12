package br.unipar.programacaoweb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class EnderecoDAO {

    public static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("consomeCepPU");

    public void salvar(Endereco endereco) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(endereco);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Endereco buscarPorEndereco(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Endereco.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public Endereco buscarPorCep(String cep) {
        EntityManager em = emf.createEntityManager();
        try {
            Endereco endereco = em.createQuery("from Endereco where cep = :cep", Endereco.class).setParameter("cep", cep.replace("-","")).getSingleResult();
            return endereco;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Endereco> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("from Endereco", Endereco.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }


}
