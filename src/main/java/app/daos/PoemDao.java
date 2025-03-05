package app.daos;

import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import app.dtos.PoemDto;
import app.entities.Poem;

public class PoemDao {

    private static PoemDao instance;
    private static EntityManagerFactory emf;

    private PoemDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static PoemDao getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new PoemDao(emf);
        }
        return instance;
    }

    public static PoemDao getInstance() {
        return instance;
    }


    public PoemDto create(PoemDto poemDto) {
        try (EntityManager em = emf.createEntityManager()) {
            Poem poem = new Poem(poemDto); // ignores id in the PoemDto

            em.getTransaction().begin();
            em.persist(poem);
            em.getTransaction().commit();

            return new PoemDto(poem); // this PoemDto has been given an id
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }

    }


    public List<PoemDto> readAll() {
        try (EntityManager em = emf.createEntityManager()) {

            String jpql = "SELECT p FROM Poem p ORDER BY id";
            TypedQuery<Poem> query = em.createQuery(jpql, Poem.class);
            List<Poem> poems = query.getResultList();

            List<PoemDto> poemDtos = new LinkedList<>();
            for (Poem p : poems) {
                poemDtos.add(new PoemDto(p));
            }
            return poemDtos;

        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }


}
