package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import ec.edu.puce.syllabus.constantes.EnumCarrera;
import ec.edu.puce.syllabus.constantes.EnumEstado;

@Entity
@Table(name = "SEGUIMIENTO_SYLLABUS")
@TableGenerator(table = "SECUENCIAS", name = "GEN_SEGUIMIENTO_SYLLABUS", pkColumnName = "NOMBRE", pkColumnValue = "SYLLABUS", valueColumnName = "VALOR", allocationSize = 1)
public class SeguimientoSyllabus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GEN_SEGUIMIENTO_SYLLABUS")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "semestre", referencedColumnName = "semestre")
	private Semestre semestre;

	@Column(name = "carrera")
	@Enumerated(EnumType.STRING)
	private EnumCarrera carrera;

	@ManyToOne(optional = false)
	@JoinColumn(name = "codigo_materia", referencedColumnName = "codigo")
	private Materia materia;

	@ManyToOne(optional = false)
	@JoinColumn(name = "identificacion_profesor", referencedColumnName = "identificacion")
	private Usuario profesor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "identificacion_alumno", referencedColumnName = "identificacion")
	private Usuario alumno;

	@Column(name = "fecha_creacion", length = 200)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@Column(name = "descripcion", nullable = false, length = 2000)
	private String descripcion;

	@OneToMany(mappedBy = "seguimiento", cascade = CascadeType.ALL)
	private List<SeguimientoSyllabusDetalle> detalles;
	@Transient
	private int alumnoPorcentaje;
	@Transient
	private int profesorPorcentaje;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public EnumCarrera getCarrera() {
		return carrera;
	}

	public void setCarrera(EnumCarrera carrera) {
		this.carrera = carrera;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Usuario getProfesor() {
		return profesor;
	}

	public void setProfesor(Usuario profesor) {
		this.profesor = profesor;
	}

	public Usuario getAlumno() {
		return alumno;
	}

	public void setAlumno(Usuario alumno) {
		this.alumno = alumno;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<SeguimientoSyllabusDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<SeguimientoSyllabusDetalle> detalles) {
		this.detalles = detalles;
	}

	public int getAlumnoPorcentaje() {
		return alumnoPorcentaje;
	}

	public void setAlumnoPorcentaje(int alumnoPorcentaje) {
		this.alumnoPorcentaje = alumnoPorcentaje;
	}

	public int getProfesorPorcentaje() {
		return profesorPorcentaje;
	}

	public void setProfesorPorcentaje(int profesorPorcentaje) {
		this.profesorPorcentaje = profesorPorcentaje;
	}
	
	

}