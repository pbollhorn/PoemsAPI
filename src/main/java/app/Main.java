package app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

import app.config.HibernateConfig;
import app.daos.PoemDao;
import app.dtos.PoemDto;
import app.controllers.PoemController;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        PoemDao poemDao = PoemDao.getInstance(emf);

        // Read poems from JSON file and persists them in database
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            PoemDto[] poemDtos = objectMapper.readValue(new File("src/main/resources/poems.json"), PoemDto[].class);

            for (PoemDto p : poemDtos) {
                poemDao.create(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.router.contextPath = "/api";
        }).start(7070);

        PoemController.addRoutes("poem", app);

        // Close EntityManagerFactory when program shuts down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (emf != null && emf.isOpen()) {
                emf.close();
                System.out.println("EntityManagerFactory closed.");
            }
            app.stop(); // TODO: Necessary????????
        }));

    }

}