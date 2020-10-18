package application.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProductoClienteId implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long producto;
	private Long cliente;
	
	public ProductoClienteId(Long productoId, Long clienteId) {
		super();
		this.producto = productoId;
		this.cliente = clienteId;
	}
	public ProductoClienteId() {
		super();
	}
	public Long getProductoId() {
		return producto;
	}
	public void setProductoId(Long productoId) {
		this.producto = productoId;
	}
	public Long getClienteId() {
		return cliente;
	}
	public void setClienteId(Long clienteId) {
		this.cliente = clienteId;
	}
	
	
}
