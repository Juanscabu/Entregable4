package application.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import application.model.Cliente;
import application.repository.ClienteRepository;

import java.util.Optional;

@RestController
@RequestMapping("clientes")
	public class ClienteController {
    @Qualifier("clienteRepository")
    @Autowired
    private final ClienteRepository repository;

    public ClienteController(@Qualifier("clienteRepository") ClienteRepository repository) {
        this.repository = repository;
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
    
    @PutMapping("/{id}/{id}")
    Cliente replaceCliente(@RequestBody Cliente newCliente, @PathVariable Long id,@PathVariable Long idP) {
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
}

