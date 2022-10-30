package webp.testau.bootstrap;

import org.springframework.stereotype.Component;
import webp.testau.model.Category;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

//ja pravime komponent za da moze pri app start da bide instancirana
@Component
public class DataHolder {
    //lista od kategorii sto ke gi cuvame
    public static List<Category> categories = new ArrayList<>();

    @PostConstruct
    public void init(){
        categories.add(new Category("Books", "Books categories"));
        categories.add(new Category("Movies", "Movies categories"));
    }
}
