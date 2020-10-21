package application.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import application.model.Cliente;
import application.model.Producto;
import application.model.ProductoCliente;
import application.model.ReporteMontoTotal;
import application.repository.ClienteRepository;
import application.repository.ProductoRepository;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
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
    public ResponseEntity<List<Cliente>> getClientes() {
    	 List<Cliente> listaClientes = repository.findAll();
    	 if (!listaClientes.isEmpty())
    		 return ResponseEntity.ok().body(listaClientes);
    		 else {
    		throw new ClienteNotFoundException("No existen clientes");
    			 }
    		 }
    
    @GetMapping("/reporte-monto")
    public Iterable<ReporteMontoTotal> getClientesMonto() {
        List<Cliente> clientes = repository.findAll();
        List<ReporteMontoTotal> reportes = new ArrayList<ReporteMontoTotal>();
        for(Cliente c: clientes) {
        	float montoTotal = 0;
        	for (ProductoCliente pc: c.getProductos()) {
        		Producto p = repositoryP.findById(pc.getProducto()).get();
        		montoTotal += p.getPrecio();
        	}
        	reportes.add(new ReporteMontoTotal(c.getId(), c.getNombre(), montoTotal));
        }
        return reportes;
    }

    @PostMapping("/")
    public Cliente newCliente(@RequestBody Cliente c) {
        return repository.save(c);
    }

    @GetMapping("/{id}")
     public ResponseEntity<Cliente> getCliente(@PathVariable Long id) throws Exception { 
         Optional<Cliente> c = repository.findById(id);
         if (c.isPresent()) 
        	 return ResponseEntity.ok().body(c.get());
         else {
        	 throw new ClienteNotFoundException("El cliente de ese id no existe: " + id);
         }
    }


   @PutMapping("/{id}")
   public Cliente replaceCliente(@RequestBody Cliente newCliente, @PathVariable Long id) {
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
    
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)    
public static class ClienteNotFoundException extends RuntimeException {
		private String message;
	  public ClienteNotFoundException(String exception) {
	    super(exception);
	    this.message = exception;
	  }
	  
	    public String message() {
	        return message;
	    }
	} 



    
}



