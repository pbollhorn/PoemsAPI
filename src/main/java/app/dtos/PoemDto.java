package app.dtos;

import app.entities.Poem;

public record PoemDto(Integer id, String name, String text, String author, Integer year) {

    public PoemDto(Poem poem) {
        this(poem.getId(), poem.getName(), poem.getText(), poem.getAuthor(), poem.getYear());
    }

}
