package app;

import app.config.HibernateConfig;
import app.daos.PoemDao;
import app.dtos.PoemDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        PoemDao poemDao = PoemDao.getInstance(emf);

        System.out.println("Hello, World!");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            // Read JSON file into an array of PoemDto objects
            PoemDto[] poems = objectMapper.readValue(new File("src/main/resources/poems.json"), PoemDto[].class);

            // Print result
            for (PoemDto p : poems) {
                System.out.println(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        emf.close();

    }

}