package application.controller;

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

import application.model.Producto;
import application.repository.ProductoRepository;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
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
    public ResponseEntity<List<Producto>> getProductos() {
    	 List<Producto> listaProductos = repository.findAll();
    	 if (!listaProductos.isEmpty())
    		 return ResponseEntity.ok().body(listaProductos);
    		 else {
    		throw new ProductoNotFoundException("No existen productos");
    			 }
    		 }

    @PostMapping("/")
    public Producto newProducto(@RequestBody Producto c) {
        return repository.save(c);
    }

    @GetMapping("/{id}")
    ResponseEntity<Producto> getProducto(@PathVariable Long id) {
    	  Optional<Producto> p = repository.findById(id);
          if (p.isPresent()) 
         	 return ResponseEntity.ok().body(p.get());
          else {
         	 throw new ProductoNotFoundException("El producto de ese id no existe: " + id);
          }
    }

    @PutMapping("/{id}")
    ResponseEntity<Producto> replaceProducto(@RequestBody Producto newProducto, @PathVariable Long id) {
    	  Optional<Producto> p = repository.findById(id);
    	  if (p.isPresent()) {
        			return  ResponseEntity.ok().body(p.map(Producto -> {
                    Producto.setNombre(newProducto.getNombre());
                    Producto.setPrecio(newProducto.getPrecio());
                    return repository.save(Producto);
                })
                .orElseGet(() -> {
                    newProducto.setId(id);
                    return repository.save(newProducto);
                }));
    	  } 
    	  else {
    		  throw new ProductoNotFoundException("El producto a modificar con ese id no existe: " + id);
    	  }
    	  
    }

    @DeleteMapping("/{id}")
    void deleteProducto(@PathVariable Long id) {
    	  Optional<Producto> p = repository.findById(id);
    	  if (p.isPresent())
    		  repository.deleteById(id);
    	  else 
    		  throw new ProductoNotFoundException("El producto a eliminar con ese id no existe: " + id);
    	  
    }
    
    @SuppressWarnings("serial")
    @ResponseStatus(HttpStatus.NOT_FOUND)    
    public static class ProductoNotFoundException extends RuntimeException {
    		private String message;
    	  public ProductoNotFoundException(String exception) {
    	    super(exception);
    	    this.message = exception;
    	  } 	  
    	    public String message() {
    	        return message;
    	    }
    	} 
      
}
