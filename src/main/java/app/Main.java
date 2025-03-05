package app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

import app.config.HibernateConfig;
import app.daos.PoemDao;
import app.dtos.PoemDto;


public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        PoemDao poemDao = PoemDao.getInstance(emf);

        System.out.println("Hello, World!");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            // Read JSON file into an array of PoemDto objects
            PoemDto[] poemDtos = objectMapper.readValue(new File("src/main/resources/poems.json"), PoemDto[].class);

            // Persits objects to database
            for (PoemDto p : poemDtos) {
                System.out.println(p);
                poemDao.create(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        emf.close();

    }

}