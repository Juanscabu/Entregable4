package application.model;

import java.io.Serializable;

public class ReporteMontoTotal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private float monto;

	public ReporteMontoTotal(Long id, String nombre, float monto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.monto = monto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}
	
	 
	
}
