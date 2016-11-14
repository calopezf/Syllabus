package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "SYLLABUS_DETALLE")
@TableGenerator(table = "SECUENCIAS", name = "GEN_SYLLABUS_DETALLE", pkColumnName = "NOMBRE", pkColumnValue = "SYLLABUS", valueColumnName = "VALOR", allocationSize = 1)
public class SyllabusDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_SYLLABUS_DETALLE")
	private Long id;

	@Column(name = "tema", nullable = false, length = 200)
	private String tema;

	@Column(name = "descripcion", nullable = false, length = 4000)
	private String descripcion;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "codigo_syllabus", referencedColumnName = "codigo")
	private Syllabus syllabus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}