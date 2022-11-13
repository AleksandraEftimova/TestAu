package webp.testau.model;

import lombok.Data;

//za da generirame geteri i seteri
@Data
public class Category {
    private Long id;
    private String name;
    private String description;

    public Category(String name, String description) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
    }
}
