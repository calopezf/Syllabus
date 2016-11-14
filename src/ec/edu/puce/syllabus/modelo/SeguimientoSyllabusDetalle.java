package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;

@Entity
@Table(name = "SEGUIMIENTO_SYLLABUS_DETALLE")
@TableGenerator(table = "SECUENCIAS", name = "GEN_SEGUIMIENTO_SYLLABUS_DETALLE", pkColumnName = "NOMBRE", pkColumnValue = "SYLLABUS", valueColumnName = "VALOR", allocationSize = 1)
public class SeguimientoSyllabusDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_SEGUIMIENTO_SYLLABUS_DETALLE")
	private Long id;

	@Column(name = "tema", nullable = false, length = 200)
	private String tema;

	@Column(name = "descripcion", nullable = false, length = 4000)
	private String descripcion;

	@Column(name = "check_profesor", nullable = true)
	private Boolean checkProfesor;

	@Column(name = "comentario_profesor", length = 4000, nullable = true)
	private String comentarioProfesor;

	@Column(name = "check_alumno", nullable = true)
	private Boolean checkAlumno;

	@Column(name = "comentario_alumno", length = 4000, nullable = true)
	private String comentarioAlumno;

	@Column(name = "fecha_profesor", nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaProfesor;

	@Column(name = "fecha_alumno", nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaAlumno;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "codigo_seguimiento", referencedColumnName = "id")
	private SeguimientoSyllabus seguimiento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getCheckProfesor() {
		return checkProfesor;
	}

	public void setCheckProfesor(Boolean checkProfesor) {
		this.checkProfesor = checkProfesor;
	}

	public String getComentarioProfesor() {
		return comentarioProfesor;
	}

	public void setComentarioProfesor(String comentarioProfesor) {
		this.comentarioProfesor = comentarioProfesor;
	}

	public Boolean getCheckAlumno() {
		return checkAlumno;
	}

	public void setCheckAlumno(Boolean checkAlumno) {
		this.checkAlumno = checkAlumno;
	}

	public String getComentarioAlumno() {
		return comentarioAlumno;
	}

	public void setComentarioAlumno(String comentarioAlumno) {
		this.comentarioAlumno = comentarioAlumno;
	}

	public Date getFechaProfesor() {
		return fechaProfesor;
	}

	public void setFechaProfesor(Date fechaProfesor) {
		this.fechaProfesor = fechaProfesor;
	}

	public Date getFechaAlumno() {
		return fechaAlumno;
	}

	public void setFechaAlumno(Date fechaAlumno) {
		this.fechaAlumno = fechaAlumno;
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

	public SeguimientoSyllabus getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(SeguimientoSyllabus seguimiento) {
		this.seguimiento = seguimiento;
	}

}