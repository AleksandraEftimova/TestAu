package webp.testau.service;


import webp.testau.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    //za da ne go koristime celiot objekt kako parametar, odime po identificator birame od lista
    Optional<Product> save(String name, Double price, Integer quantity,
                           Long categoryId, Long manufacturerId);

    void deleteById(Long id);
}
