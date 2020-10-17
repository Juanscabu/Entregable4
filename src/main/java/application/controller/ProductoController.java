package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.Producto;
import application.repository.ProductoRepository;

@RestController
@RequestMapping("productos")
public class ProductoController {
	
    @Qualifier("productoRepository")
    @Autowired
    private final ProductoRepository repository;

    public ProductoController(@Qualifier("productoRepository") ProductoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public Iterable<Producto> getProductos() {
        return repository.findAll();
    }

    @PostMapping("/")
    public Producto newProducto(@RequestBody Producto c) {
        return repository.save(c);
    }

    @GetMapping("/{id}")
    Optional<Producto> one(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    Producto replaceProducto(@RequestBody Producto newProducto, @PathVariable Long id) {
        return repository.findById(id)
                .map(Producto -> {
                    Producto.setNombre(newProducto.getNombre());
                    return repository.save(Producto);
                })
                .orElseGet(() -> {
                    newProducto.setId(id);
                    return repository.save(newProducto);
                });
    }

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
