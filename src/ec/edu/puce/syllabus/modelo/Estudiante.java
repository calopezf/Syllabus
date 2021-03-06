package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name = "ESTUDIANTE")
@TableGenerator(table = "SECUENCIAS", name = "GEN_ESTUDIANTE", pkColumnName = "NOMBRE", pkColumnValue = "ESTUDIANTE", valueColumnName = "VALOR", allocationSize = 1)
public class Estudiante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_ESTUDIANTE")
	private Long id;
	@Column(name = "primer_nombre", nullable = false, length = 200)
	private String primerNombre;
	@Column(name = "segundo_nombre", length = 200)
	private String segundoNombre;
	@Column(name = "primer_apellido", nullable = false, length = 200)
	private String primerApellido;
	@Column(name = "segundo_apellido", length = 200)
	private String segundoApellido;
	@Column(name = "identificacion", length = 10)
	private String identificacion;

	@Transient
	private String nombreCompleto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombreCompleto() {
		nombreCompleto = getPrimerNombre() + " " + getPrimerApellido();
		return nombreCompleto.toUpperCase();
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	@Override
	public String toString() {
		return "Persona{" + "id=" + id + ", primerNombre=" + primerNombre
				+ ", segundoNombre=" + segundoNombre + ", primerApellido="
				+ primerApellido + ", segundoApellido=" + segundoApellido
				+ ", identificacion=" + identificacion + '}';
	}

}