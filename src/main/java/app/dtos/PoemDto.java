package app.dtos;

import app.entities.Poem;

public record PoemDto(Integer id, String poem, String author, Integer year) {

    public PoemDto(Poem poem) {
        this(poem.getId(), poem.getPoem(), poem.getAuthor(), poem.getYear());
    }

}
