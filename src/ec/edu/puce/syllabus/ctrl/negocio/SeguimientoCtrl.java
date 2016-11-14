package ec.edu.puce.syllabus.ctrl.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.ctrl.BaseCtrl;
import ec.edu.puce.syllabus.modelo.Materia;
import ec.edu.puce.syllabus.modelo.Rol;
import ec.edu.puce.syllabus.modelo.SeguimientoSyllabus;
import ec.edu.puce.syllabus.modelo.SeguimientoSyllabusDetalle;
import ec.edu.puce.syllabus.modelo.Syllabus;
import ec.edu.puce.syllabus.modelo.SyllabusDetalle;
import ec.edu.puce.syllabus.modelo.Usuario;

@ManagedBean(name = "seguimientoCtrl")
@ViewScoped
public class SeguimientoCtrl extends BaseCtrl {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;
	// TODO serializable de la clase: Usuario
	@EJB
	private ServicioCrud servicioCrud;
	private SeguimientoSyllabus seguimiento;
	private SeguimientoSyllabus seguimientoFiltro;
	private List<SeguimientoSyllabus> seguimientoLista;
	private List<SeguimientoSyllabusDetalle> seguimientoListaDetalle;
	private List<Materia> materiaLista;
	private List<Usuario> profesorLista;
	private List<Usuario> alumnoLista;

	@PostConstruct
	public void postConstructor() {
		this.seguimientoFiltro = new SeguimientoSyllabus();
		this.seguimientoFiltro.setMateria(new Materia());
		this.seguimientoFiltro.setAlumno(new Usuario());
		this.seguimientoFiltro.setProfesor(new Usuario());
		this.profesorLista = new ArrayList<Usuario>();
		this.alumnoLista = new ArrayList<Usuario>();
		List<Usuario> usuarioLista = servicioCrud.findOrder(new Usuario());
		for (Usuario usu : usuarioLista) {
			for (Rol rol : usu.getRoles()) {
				if (rol.getId().equals("PROFESOR")) {
					this.profesorLista.add(usu);
				}
				if (rol.getId().equals("ALUMNO")) {
					this.alumnoLista.add(usu);
				}
			}

		}
	}

	public SeguimientoSyllabus getSeguimiento() {
		if (seguimiento == null) {
			String seguimientoId = getHttpServletRequestParam("idSeguimiento");
			if (seguimientoId == null) {
				this.seguimiento = new SeguimientoSyllabus();
				this.seguimiento.setMateria(new Materia());
				this.seguimiento.setAlumno(new Usuario());
				this.seguimiento.setProfesor(new Usuario());
				this.seguimiento.setFechaCreacion(getCurrentDateObj());
				seguimiento.setEstado(EnumEstado.ACT);
			} else {
				seguimiento = servicioCrud.findById(
						Long.parseLong(seguimientoId),
						SeguimientoSyllabus.class);
			}
		}
		return seguimiento;
	}

	public void setSeguimiento(SeguimientoSyllabus seguimiento) {
		this.seguimiento = seguimiento;
	}

	public void eliminarSeguimiento() {
		try {
			SeguimientoSyllabus seguimientoData = (SeguimientoSyllabus) getExternalContext()
					.getRequestMap().get("item");

			SeguimientoSyllabusDetalle filtro = new SeguimientoSyllabusDetalle();
			filtro.setSeguimiento(seguimientoData);
			for (SeguimientoSyllabusDetalle detalle : servicioCrud
					.findOrder(filtro)) {
				servicioCrud.remove(detalle.getId(),
						SeguimientoSyllabusDetalle.class);
			}
			servicioCrud.remove(seguimientoData.getId(),
					SeguimientoSyllabus.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.seguimientoLista = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			if (this.seguimiento.getId() == null) {
				this.seguimiento = servicioCrud.insertReturn(seguimiento);
				Syllabus syllabus = servicioCrud.findById(this.seguimiento
						.getMateria().getCodigo(), Syllabus.class);
				SyllabusDetalle syllabusDetalleFiltro = new SyllabusDetalle();
				syllabusDetalleFiltro.setSyllabus(syllabus);
				for (SyllabusDetalle detalle : servicioCrud
						.findOrder(syllabusDetalleFiltro)) {
					SeguimientoSyllabusDetalle seguimientoDetalle = new SeguimientoSyllabusDetalle();
					seguimientoDetalle.setTema(detalle.getTema());
					seguimientoDetalle.setDescripcion(detalle.getDescripcion());
					seguimientoDetalle.setSeguimiento(seguimiento);
					seguimientoDetalle.setCheckAlumno(Boolean.FALSE);
					seguimientoDetalle.setCheckProfesor(Boolean.FALSE);
					servicioCrud.insert(seguimientoDetalle);
				}

			} else {

				SeguimientoSyllabusDetalle filtro = new SeguimientoSyllabusDetalle();
				filtro.setSeguimiento(seguimiento);
				for (SeguimientoSyllabusDetalle detalle : servicioCrud
						.findOrder(filtro)) {
					this.servicioCrud.remove(detalle.getId(),
							SeguimientoSyllabusDetalle.class);
				}
				this.seguimiento.setDetalles(seguimientoListaDetalle);
				this.seguimiento = servicioCrud.update(seguimiento);
			}
			this.seguimientoListaDetalle = null;
			String m = getBundleMensajes("registro.guardado.correctamente",
					null);
			addInfoMessage(m, m);

		} catch (Exception e) {
			// e.printStackTrace();
			String m = getBundleMensajes("registro.noguardado.exception",
					new Object[] { e.getMessage() });
			addErrorMessage(m, m, null);
			return null;
		}

		return "/paginas/seguimiento/seguimientoLista";
	}

	public String editar() {
		SeguimientoSyllabus seguimientoData = (SeguimientoSyllabus) getExternalContext()
				.getRequestMap().get("item");
		return "/paginas/seguimiento/seguimiento?faces-redirect=true&idSeguimiento="
				+ seguimientoData.getId();
	}

	public String navegarSeguimientoSyllabusDetalle() {
		SeguimientoSyllabus seguimientoData = (SeguimientoSyllabus) getExternalContext()
				.getRequestMap().get("item");
		return "/paginas/seguimiento/seguimiento?faces-redirect=true&idMateria="
				+ seguimientoData.getId();
	}

	public List<SeguimientoSyllabusDetalle> getSeguimientoListaDetalle() {
		if (seguimientoListaDetalle == null) {
			this.seguimientoListaDetalle = new ArrayList<SeguimientoSyllabusDetalle>();
			SeguimientoSyllabusDetalle detalleFiltro = new SeguimientoSyllabusDetalle();
			detalleFiltro.setSeguimiento(seguimiento);
			this.seguimientoListaDetalle = servicioCrud
					.findOrder(detalleFiltro);

		}
		return seguimientoListaDetalle;
	}

	public void setSeguimientoListaDetalle(
			List<SeguimientoSyllabusDetalle> seguimientoListaDetalle) {
		this.seguimientoListaDetalle = seguimientoListaDetalle;
	}

	public void buscar() {
		this.seguimientoLista = null;
	}

	public List<SeguimientoSyllabus> getSeguimientoLista() {
		if (this.seguimientoLista == null) {
			seguimientoLista = this.servicioCrud
					.findOrder(this.seguimientoFiltro);
		}
		return seguimientoLista;
	}

	public void setSeguimientoLista(List<SeguimientoSyllabus> seguimientoLista) {
		this.seguimientoLista = seguimientoLista;
	}

	public SeguimientoSyllabus getSeguimientoFiltro() {
		return seguimientoFiltro;
	}

	public void setSeguimientoFiltro(SeguimientoSyllabus seguimientoFiltro) {
		this.seguimientoFiltro = seguimientoFiltro;
	}

	public void cambiaCarrera(AjaxBehaviorEvent event) {
		this.materiaLista = null;
	}

	public List<Materia> getMateriaLista() {
		if (materiaLista == null) {
			Materia materiaFiltro = new Materia();
			if (this.seguimientoFiltro != null
					&& this.seguimientoFiltro.getCarrera() != null) {
				materiaFiltro.setCarrera(this.seguimientoFiltro.getCarrera());
			}
			if (this.seguimiento != null
					&& this.seguimiento.getCarrera() != null) {
				materiaFiltro.setCarrera(this.seguimiento.getCarrera());
			}
			materiaLista = servicioCrud.findOrder(materiaFiltro);
		}
		return materiaLista;
	}

	public void setMateriaLista(List<Materia> materiaLista) {
		this.materiaLista = materiaLista;
	}

	public List<Usuario> getProfesorLista() {
		return profesorLista;
	}

	public void setProfesorLista(List<Usuario> profesorLista) {
		this.profesorLista = profesorLista;
	}

	public List<Usuario> getAlumnoLista() {
		return alumnoLista;
	}

	public void setAlumnoLista(List<Usuario> alumnoLista) {
		this.alumnoLista = alumnoLista;
	}

}
