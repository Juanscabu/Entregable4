package application.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import application.model.ProductoCliente;
import application.model.ProductoClienteId;
public interface ProductoClienteRepository extends JpaRepository<ProductoCliente, ProductoClienteId>{
	
	  @Query("SELECT pc FROM ProductoCliente pc where pc.fechaCompra = :d")
	 public List<ProductoCliente> findAllByFechaCompra(LocalDate d);
	  
	  @Query("SELECT pc FROM ProductoCliente pc GROUP BY pc.producto ORDER BY SUM(cantidad) DESC")
		 public List<ProductoCliente> findByMasVendido();
	 
}

