package application.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import application.model.Cliente;
import application.model.Producto;
import application.repository.ClienteRepository;
import application.repository.ProductoRepository;

import java.util.Optional;

@RestController
@RequestMapping("clientes")
	public class ClienteController {
    @Qualifier("clienteRepository")
    @Autowired

    private final ClienteRepository repository;
    private final ProductoRepository repositoryP;

    public ClienteController(@Qualifier("clienteRepository") ClienteRepository repository,@Qualifier("productoRepository") ProductoRepository repositoryP) {
        this.repository = repository;
        this.repositoryP = repositoryP;
        
    }

    @GetMapping("/")
    public Iterable<Cliente> getClientes() {
        return repository.findAll();
    }

    @PostMapping("/")
    public Cliente newCliente(@RequestBody Cliente c) {
        return repository.save(c);
    }

    @GetMapping("/{id}")
    Optional<Cliente> one(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    Cliente replaceCliente(@RequestBody Cliente newCliente, @PathVariable Long id) {
        return repository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(newCliente.getNombre());
                    return repository.save(cliente);
                })
                .orElseGet(() -> {
                    newCliente.setId(id);
                    return repository.save(newCliente);
                });
    }

    @DeleteMapping("/{id}")
    void deleteCliente(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
    @PutMapping("/{id}/comprar")
    Cliente comprar(@RequestBody Cliente newCliente, @PathVariable Long id)  { //intentar que reciba el id del producto
    	Long idP = (long) 1234;
    	Producto producto = repositoryP.findById(idP).get(); //agregar if
        return repository.findById(id)
                .map(cliente -> {
                    cliente.comprar(producto);
                    return repository.save(cliente);
                })
                .orElseGet(() -> {
                    newCliente.setId(id); //agregar exception
                    return repository.save(newCliente);
                });
    }
}

