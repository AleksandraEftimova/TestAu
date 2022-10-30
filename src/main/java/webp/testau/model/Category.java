package webp.testau.model;

import lombok.Data;

//za da generirame geteri i seteri
@Data
public class Category {
    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
