package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@OneToMany(mappedBy = "syllabus",cascade=CascadeType.ALL)
	private List<SyllabusDetalle> detalles;
	@Transient
	private Materia materia;

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

	public List<SyllabusDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<SyllabusDetalle> detalles) {
		this.detalles = detalles;
	}

}