package application.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import application.model.Cliente;
import application.model.Producto;
public interface ProductoRepository extends JpaRepository<Producto, Long> {

	
}
