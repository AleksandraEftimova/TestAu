package webp.testau.service.implementations;

import org.springframework.stereotype.Service;
import webp.testau.model.Category;
import webp.testau.model.Manufacturer;
import webp.testau.model.Product;
import webp.testau.model.exceptions.CategoryNotFoundException;
import webp.testau.model.exceptions.ManufacturerNotFoundException;
import webp.testau.repository.InMemoryCategoryRepository;
import webp.testau.repository.InMemoryManufacturerRepository;
import webp.testau.repository.InMemoryProductRepository;
import webp.testau.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    //zavisnosti, inject the repository
    private final InMemoryProductRepository productRepository;
    private final InMemoryCategoryRepository categoryRepository;
    private final InMemoryManufacturerRepository manufacturerRepository;

    public ProductServiceImpl(InMemoryProductRepository productRepository, InMemoryCategoryRepository categoryRepository, InMemoryManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Optional<Product> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        //vidi koja kategorija go ima toa id i koj proizvoditel toa id,
        //togas vrati metod save

        //pravime exception vo slucaj da ne ja najde kategorijata/proizvoditelot
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        //ako se pomine kako sto treba togas samo gi dodavame
        return this.productRepository.save(name, price, quantity, category, manufacturer);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
