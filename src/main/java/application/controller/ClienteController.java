package application.controller;
import java.util.ArrayList;
import java.util.List;

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

import application.model.Cliente;
import application.model.Producto;
import application.model.ProductoCliente;
import application.model.ReporteMontoTotal;
import application.repository.ClienteRepository;
import application.repository.ProductoRepository;

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
    Cliente one(@PathVariable Long id) throws Exception {
         Cliente c = repository.findById(id).get();
       //  if (c != null) 
        	 return c;
        	 /*
         else {
          return Response.setStatus(return Response Status(484).entity("No se encuentra esta carrera"). type(MediaType.TEXT PLAIN).building;

return Response.status (700).entity(c.build(););

         }
         */
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
    
    
   
    
}

