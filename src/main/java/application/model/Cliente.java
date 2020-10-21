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
public class Cliente {
	@Id
    private Long id;
    @Column
    private String nombre;
    @JsonIgnore
    @OneToMany(mappedBy="producto")
    private List<ProductoCliente> productos;
    
	public Cliente() {
	}

	public Cliente(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public void agregar(ProductoCliente pc) {
		productos.add(pc);
	}
	
}
