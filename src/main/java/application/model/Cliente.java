package application.model;

import lombok.Data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Data
public class Cliente {
	@Id
    private Long id;
    @Column
    private String nombre;
    @ManyToMany
    private List<Producto> productos;
    
	public Cliente() {
	}

	public Cliente(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public void comprar (Producto p) {
		productos.add(p);
	}
	
}
