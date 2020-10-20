package application.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity
@Data
@IdClass(ProductoClienteId.class)
public class ProductoCliente {
	 	@Id
	    private Long producto;
	    @Id
	    private Long cliente;

	@Column
	private LocalDate fechaCompra;
	@Column
	private int cantidad;
	
	public ProductoCliente() {
		super();
	}

	public ProductoCliente(Long productoId, Long clienteId, LocalDate fechaCompra, int cantidad) {
		this.producto = productoId;
		this.cliente = clienteId;
		this.fechaCompra = fechaCompra;
		this.cantidad = cantidad;
	}
	
	
	
	

	
	
	
	
	
}
