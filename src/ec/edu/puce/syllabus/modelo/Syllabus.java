package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ec.edu.puce.syllabus.constantes.EnumEstado;

@Entity
@Table(name = "SYLLABUS")
public class Syllabus implements Serializable {

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
	@ManyToOne(optional = false)
	@JoinColumn(name = "codigo_materia", referencedColumnName = "codigo")
	private Materia materia;

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

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	

}