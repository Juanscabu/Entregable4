package application.model;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Producto {
	 @Id
	    private Long id;
	    @Column
	    private String nombre;
	    @Column
	    private float precio;
	    @JsonIgnore
	    @OneToMany(mappedBy="cliente")
	    private List<ProductoCliente> clientes;
		
	    public Producto() {		
		}

		public Producto(Long id,String nombre, int precio) {
			this.id = id;
			this.nombre = nombre;
			this.precio = precio;
		}
	
	
	    
}
