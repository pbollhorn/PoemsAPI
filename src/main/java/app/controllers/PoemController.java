package app.controllers;

import app.dtos.PoemDto;
import io.javalin.Javalin;
import io.javalin.http.Context;

import app.daos.PoemDao;

import java.util.List;

public class PoemController {

    private static PoemDao poemDao = PoemDao.getInstance();

    public static void addRoutes(String endpoint, Javalin app) {
        app.get(endpoint + "/", ctx -> getAll(ctx));
        app.get(endpoint + "/{id}", ctx -> getById(ctx));

    }

    private static void getAll(Context ctx) {
        List<PoemDto> poemDtos = poemDao.readAll();
        ctx.json(poemDtos);
    }

    private static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        PoemDto poemDto = poemDao.readById(id);
        ctx.json(poemDto);
    }

}
