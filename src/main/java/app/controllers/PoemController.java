package app.controllers;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

import app.daos.PoemDao;
import app.dtos.PoemDto;

public class PoemController {

    private static PoemDao poemDao = PoemDao.getInstance();

    public static void addRoutes(String resource, Javalin app) {
        app.get(resource + "/", ctx -> getAll(ctx));
        app.get(resource + "/{id}", ctx -> getById(ctx));
        app.post(resource + "/", ctx -> create(ctx));
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

    private static void create(Context ctx) {

    }

}
