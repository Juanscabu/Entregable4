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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import application.controller.ProductoController.ProductoNotFoundException;
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
    public ResponseEntity<Iterable<ReporteMontoTotal>> getClientesMonto() {
        List<Cliente> clientes = repository.findAll();
        if (!clientes.isEmpty()) {
        List<ReporteMontoTotal> reportes = new ArrayList<ReporteMontoTotal>();
        for(Cliente c: clientes) {
        	float montoTotal = 0;
        	for (ProductoCliente pc: c.getProductos()) {
        		Producto p = repositoryP.findById(pc.getProducto()).get();
        		montoTotal += p.getPrecio();
        	}
        	reportes.add(new ReporteMontoTotal(c.getId(), c.getNombre(), montoTotal));
        }
        return ResponseEntity.ok().body(reportes);
        }
        throw new ClienteNotFoundException("No existen clientes");
    }

    @PostMapping("/")
    public Cliente newCliente(@RequestBody Cliente c) {
    	Cliente cliente = new Cliente();
    	cliente.setId(c.getId());
    	cliente.setNombre(c.getNombre());
    	cliente.setProductos(new ArrayList<ProductoCliente>());
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
   public ResponseEntity<Cliente> replaceCliente(@RequestBody Cliente newCliente, @PathVariable Long id) {
	   Optional<Cliente> c = repository.findById(id);
 	  if (c.isPresent()) {
     			return  ResponseEntity.ok().body(c.map(Cliente -> {
                 Cliente.setNombre(newCliente.getNombre());
                 return repository.save(Cliente);
             })
             .orElseGet(() -> {
                 newCliente.setId(id);
                 return repository.save(newCliente);
             }));
 	  } 
 	  else {
 		  throw new ClienteNotFoundException("El cliente a modificar con ese id no existe: " + id);
 	  }
   }
    @DeleteMapping("/{id}")
    void deleteCliente(@PathVariable Long id) {
    	  Optional<Cliente> c = repository.findById(id);
    	  if (c.isPresent())
    		  repository.deleteById(id);
    	  else 
    		  throw new ProductoNotFoundException("El cliente a eliminar con ese id no existe: " + id);
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



