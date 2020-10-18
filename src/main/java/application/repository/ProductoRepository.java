package application.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Producto;
public interface ProductoRepository extends JpaRepository<Producto, Long> {
 
	
}
