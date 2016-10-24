package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.constantes.EnumCarrera;

@Entity
@Table(name = "MATERIA")
public class Materia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "codigo", length = 20)
	private String codigo;// o username
	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;
	@Column(name = "carrera")
	@Enumerated(EnumType.STRING)
	private EnumCarrera carrera;

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EnumCarrera getCarrera() {
		return carrera;
	}

	public void setCarrera(EnumCarrera carrera) {
		this.carrera = carrera;
	}

}