package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.constantes.EnumTipoParametro;

@Entity
@Table(name = "PARAMETRO")
public class Parametro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "nombre")
	private String nombre;// de ParametroEnum

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false, length = 15)
	private EnumTipoParametro tipo;

	@Column(name = "valor", nullable = false, length = 150)
	private String valor;

	@Column(name = "descripcion", nullable = false, length = 150)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false, length = 50)
	private EnumEstado estado;

	@Transient
	private boolean registroNuevo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EnumTipoParametro getTipo() {
		return tipo;
	}

	public void setTipo(EnumTipoParametro tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public boolean isRegistroNuevo() {
		return registroNuevo;
	}

	public void setRegistroNuevo(boolean registroNuevo) {
		this.registroNuevo = registroNuevo;
	}

	@Override
	public String toString() {
		return "Parametro{" + "nombre=" + nombre + ", tipo=" + tipo
				+ ", valor=" + valor + ", descripcion=" + descripcion
				+ ", estado=" + estado + '}';
	}

}