package application.model;
import lombok.Data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Data
public class Producto {
	 @Id
	    private Long id;
	    @Column
	    private String nombre;
	    @Column
	    private int precio;
	    @ManyToMany(mappedBy="Cliente")
	    private List<Cliente> clientes;
		
	    public Producto() {		
		}

		public Producto(Long id,String nombre, int precio) {
			this.id = id;
			this.nombre = nombre;
			this.precio = precio;

		}
	
	
	    
}
