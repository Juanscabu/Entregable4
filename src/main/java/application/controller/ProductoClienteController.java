package application.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.Cliente;
import application.model.Producto;
import application.model.ProductoCliente;
import application.repository.ClienteRepository;
import application.repository.ProductoClienteRepository;
import application.repository.ProductoRepository;

@RestController
@RequestMapping("compra")
public class ProductoClienteController {
	  @Qualifier("productoClienteRepository")
	   @Autowired
	   	private final ProductoClienteRepository repository;
	  	private final ClienteRepository repositoryC;
	    private final ProductoRepository repositoryP;
	  
	  public ProductoClienteController(@Qualifier("productoClienteRepository") ProductoClienteRepository repository,@Qualifier("productoRepository") ProductoRepository repositoryP,@Qualifier("clienteRepository") ClienteRepository repositoryC) {
	        this.repository = repository;
	        this.repositoryP = repositoryP;
	        this.repositoryC = repositoryC;
	    }
	  
	  
	  @PostMapping("/{id}/{idP}")
	    ProductoCliente comprar(@RequestBody Cliente newCliente, @PathVariable(name = "id",required = true) Long id,  @PathVariable(name = "idP",required = true) Long idP )  { 
	    	Producto producto = repositoryP.findById(idP).get(); 
	    	Cliente cliente = repositoryC.findById(id).get();//agregar if
	    	LocalDate fecha = LocalDate.now();
	    	ProductoCliente pc = new ProductoCliente(producto.getId(),cliente.getId(),fecha);
	    	return repository.save(pc);
	    }
	  
	  @GetMapping("/{fecha}")
	  Iterable<ProductoCliente> ReporteFecha(@PathVariable String fecha) {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		  
		  LocalDate localDate = LocalDate.parse(fecha, formatter);
		 
	        return repository.findAllByFechaCompra(localDate);
	  }    
	  
	  @GetMapping("/mas-vendido")
	  Producto productoMasVendido() {
		  return repository.findByMasVendido();
	  }
	  
}
