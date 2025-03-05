package app.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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

}
