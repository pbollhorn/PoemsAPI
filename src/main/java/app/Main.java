package app;

import app.config.HibernateConfig;
import app.daos.PoemDao;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        PoemDao poemDao = PoemDao.getInstance(emf);





        System.out.println("Hello, World!");




        emf.close();

    }

}