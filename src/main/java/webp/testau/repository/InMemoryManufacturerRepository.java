package webp.testau.repository;

import org.springframework.stereotype.Repository;
import webp.testau.bootstrap.DataHolder;
import webp.testau.model.Manufacturer;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepository {

    //listanje na site proizvoditeli
    public List<Manufacturer> findAll() {
        return DataHolder.manufacturers;
    }

    //listanje na eden proizvoditel spored id
    public Optional<Manufacturer> findById(Long id){
        return DataHolder.manufacturers
                .stream()
                .filter(r->r.getId().equals(id))
                .findFirst();
    }
}
