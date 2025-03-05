package app.daos;

import jakarta.persistence.EntityManagerFactory;

import app.entities.Poem;

public class PoemDao extends AbstractDao<Poem, Integer> {

    private static PoemDao instance;

    private PoemDao(EntityManagerFactory emf) {
        super(Poem.class, emf);
    }

    public static PoemDao getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new PoemDao(emf);
        }
        return instance;
    }

}
