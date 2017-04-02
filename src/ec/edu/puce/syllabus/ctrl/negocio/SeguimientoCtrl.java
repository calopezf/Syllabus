package ec.edu.puce.syllabus.ctrl.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.xml.soap.Detail;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.constantes.EnumTipoParametro;
import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.ctrl.BaseCtrl;
import ec.edu.puce.syllabus.modelo.Materia;
import ec.edu.puce.syllabus.modelo.Parametro;
import ec.edu.puce.syllabus.modelo.Rol;
import ec.edu.puce.syllabus.modelo.SeguimientoSyllabus;
import ec.edu.puce.syllabus.modelo.SeguimientoSyllabusDetalle;
import ec.edu.puce.syllabus.modelo.Semestre;
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
	private SeguimientoSyllabus seguimientoFiltroCoordinador;
	private SeguimientoSyllabus seguimientoFiltroDirector;
	private List<SeguimientoSyllabus> seguimientoLista;
	private List<SeguimientoSyllabus> seguimientoListaCoordinador;
	private List<SeguimientoSyllabus> seguimientoListaDirector;
	private List<SeguimientoSyllabusDetalle> seguimientoListaDetalle;
	private List<Materia> materiaLista;
	private List<Parametro> semestreLista;
	private List<Parametro> carreraLista;
	private List<Usuario> profesorLista;
	private List<Usuario> alumnoLista;
	private List<Usuario> coordinadorLista;
	private List<Usuario> directorLista;

	@PostConstruct
	public void postConstructor() {
		this.seguimientoFiltro = new SeguimientoSyllabus();
		this.seguimientoFiltro.setMateria(new Materia());
		this.seguimientoFiltro.setSemestre(new Parametro());
		this.seguimientoFiltro.setCarrera(new Parametro());
		this.seguimientoFiltro.setAlumno(new Usuario());
		this.seguimientoFiltro.setProfesor(new Usuario());
		this.seguimientoFiltro.setDirector(new Usuario());
		this.seguimientoFiltro.setCoordinador(new Usuario());

		this.seguimientoFiltroCoordinador = new SeguimientoSyllabus();
		this.seguimientoFiltroCoordinador.setMateria(new Materia());
		this.seguimientoFiltroCoordinador.setSemestre(new Parametro());
		this.seguimientoFiltroCoordinador.setCarrera(new Parametro());
		this.seguimientoFiltroCoordinador.setAlumno(new Usuario());
		this.seguimientoFiltroCoordinador.setProfesor(new Usuario());
		this.seguimientoFiltroCoordinador.setDirector(new Usuario());
		this.seguimientoFiltroCoordinador.setCoordinador(new Usuario());
		
		this.seguimientoFiltroDirector = new SeguimientoSyllabus();
		this.seguimientoFiltroDirector.setMateria(new Materia());
		this.seguimientoFiltroDirector.setSemestre(new Parametro());
		this.seguimientoFiltroDirector.setCarrera(new Parametro());
		this.seguimientoFiltroDirector.setAlumno(new Usuario());
		this.seguimientoFiltroDirector.setProfesor(new Usuario());
		this.seguimientoFiltroDirector.setDirector(new Usuario());
		this.seguimientoFiltroDirector.setCoordinador(new Usuario());
		
		if (isAlumno()) {
			this.seguimientoFiltro.setAlumno(getUsuarioLogueado());
		} else {
			this.seguimientoFiltro.setAlumno(new Usuario());
		}
		if (isProfesor()) {
			this.seguimientoFiltro.setProfesor(getUsuarioLogueado());
		} else {
			this.seguimientoFiltro.setProfesor(new Usuario());
		}
//		if (isCoordinador() && !isDirector() && !isAdministrador()) {
//			this.seguimientoFiltro.setCoordinador(getUsuarioLogueado());
//		} else {
//			this.seguimientoFiltro.setCoordinador(new Usuario());
//		}
//		if (isDirector() && !isAdministrador()) {
//			this.seguimientoFiltro.setDirector(getUsuarioLogueado());
//		} else {
//			this.seguimientoFiltro.setDirector(new Usuario());
//		}
		if (isCoordinador()) {			
			//this.seguimientoFiltro.setCoordinador(getUsuarioLogueado());
			this.seguimientoFiltroCoordinador.setCoordinador(getUsuarioLogueado());
		}
		if (isDirector()) {	
			//this.seguimientoFiltro.setDirector(getUsuarioLogueado());
			this.seguimientoFiltroDirector.setDirector(getUsuarioLogueado());
		}
		
		
		this.profesorLista = new ArrayList<Usuario>();
		this.alumnoLista = new ArrayList<Usuario>();
		this.coordinadorLista = new ArrayList<Usuario>();
		this.directorLista = new ArrayList<Usuario>();
		List<Usuario> usuarioLista = servicioCrud.findOrder(new Usuario(),"apellido", "nombre");
		for (Usuario usu : usuarioLista) {
			for (Rol rol : usu.getRoles()) {
				if (rol.getId().equals("PROFESOR")) {
					this.profesorLista.add(usu);
				}
				if (rol.getId().equals("ALUMNO")) {
					this.alumnoLista.add(usu);
				}
				if (rol.getId().equals("COORDINADOR")) {
					this.coordinadorLista.add(usu);
				}
				if (rol.getId().equals("DIRECTOR")) {
					this.directorLista.add(usu);
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
				this.seguimiento.setCoordinador(new Usuario());
				this.seguimiento.setDirector(new Usuario());
				this.seguimiento.setSemestre(new Parametro());
				this.seguimiento.setFechaCreacion(getCurrentDateObj());
				this.seguimiento.setCarrera(new Parametro());
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
				Syllabus syllabus = servicioCrud.findById(this.seguimiento
						.getMateria().getCodigo(), Syllabus.class);
				if(syllabus==null){
					addErrorMessage("cedula",
							getBundleMensajes("materiaSinContenido", null), "");
					return null;
				}
				SyllabusDetalle syllabusDetalleFiltro = new SyllabusDetalle();
				syllabusDetalleFiltro.setSyllabus(syllabus);
				List<SyllabusDetalle> listaDeta=servicioCrud.findOrder(syllabusDetalleFiltro);
				if(listaDeta==null || listaDeta.isEmpty()){
					addErrorMessage("cedula",
							getBundleMensajes("materiaSinContenido", null), "");
					return null;
				}
				this.seguimiento = servicioCrud.insertReturn(seguimiento);
			
				for (SyllabusDetalle detalle : servicioCrud
						.findOrder(syllabusDetalleFiltro)) {
					SeguimientoSyllabusDetalle seguimientoDetalle = new SeguimientoSyllabusDetalle();
					seguimientoDetalle.setUnidad(detalle.getUnidad());
					seguimientoDetalle.setClase(detalle.getClase());
					seguimientoDetalle.setContenido(detalle.getContenido());
					seguimientoDetalle.setActividad(detalle.getActividad());
					seguimientoDetalle.setTrabajo(detalle.getTrabajo());
					seguimientoDetalle.setEvidencia(detalle.getEvidencia());
					seguimientoDetalle.setSeguimiento(seguimiento);
					seguimientoDetalle.setCheckAlumno(Boolean.FALSE);
					seguimientoDetalle.setCheckProfesor(Boolean.FALSE);
					seguimientoDetalle.setTipo(detalle.getTipo());
					seguimientoDetalle.setBimestre(detalle.getBimestre());
					servicioCrud.insert(seguimientoDetalle);
				}

			} else {
				this.seguimiento = servicioCrud.update(seguimiento);
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

	public void copiarClase() {
		SeguimientoSyllabusDetalle detalleData = (SeguimientoSyllabusDetalle) getExternalContext()
				.getRequestMap().get("item");
		SeguimientoSyllabusDetalle detalleNuevo = new SeguimientoSyllabusDetalle();
		detalleNuevo.setUnidad(detalleData.getUnidad());
		detalleNuevo.setClase(detalleData.getClase());
		detalleNuevo.setContenido(detalleData.getContenido());
		detalleNuevo.setActividad(detalleData.getActividad());
		detalleNuevo.setTrabajo(detalleData.getTrabajo());
		detalleNuevo.setEvidencia(detalleData.getEvidencia());
		detalleNuevo.setTipo(detalleData.getTipo());
		detalleNuevo.setBimestre(detalleData.getBimestre());
		detalleNuevo.setSeguimiento(detalleData.getSeguimiento());
		detalleNuevo.setCheckAlumno(Boolean.FALSE);
		detalleNuevo.setCheckProfesor(Boolean.FALSE);
		this.servicioCrud.insert(detalleNuevo);
		this.seguimientoListaDetalle = null;
	}

	public List<SeguimientoSyllabusDetalle> getSeguimientoListaDetalle() {
		if (seguimientoListaDetalle == null) {
			this.seguimientoListaDetalle = new ArrayList<SeguimientoSyllabusDetalle>();
			SeguimientoSyllabusDetalle detalleFiltro = new SeguimientoSyllabusDetalle();
			detalleFiltro.setSeguimiento(seguimiento);
			this.seguimientoListaDetalle = servicioCrud.findOrder(
					detalleFiltro, "clase");

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
					.findOrder(this.seguimientoFiltro,"carrera.nombre", "materia.nombre");
		}
		return seguimientoLista;
	}

	public void setSeguimientoLista(List<SeguimientoSyllabus> seguimientoLista) {
		this.seguimientoLista = seguimientoLista;
	}

	public List<SeguimientoSyllabus> getSeguimientoListaCoordinador() {
		if (this.seguimientoListaCoordinador == null) {
			seguimientoListaCoordinador = this.servicioCrud
					.findOrder(this.seguimientoFiltroCoordinador,"carrera.nombre", "materia.nombre");
		}
		return seguimientoListaCoordinador;
	}

	public void setSeguimientoListaCoordinador(
			List<SeguimientoSyllabus> seguimientoListaCoordinador) {
		this.seguimientoListaCoordinador = seguimientoListaCoordinador;
	}

	public List<SeguimientoSyllabus> getSeguimientoListaDirector() {
		if (this.seguimientoListaDirector == null) {
			seguimientoListaDirector = this.servicioCrud
					.findOrder(this.seguimientoFiltroDirector,"carrera.nombre", "materia.nombre");
		}
		return seguimientoListaDirector;
	}

	public void setSeguimientoListaDirector(
			List<SeguimientoSyllabus> seguimientoListaDirector) {
		this.seguimientoListaDirector = seguimientoListaDirector;
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

	public List<Parametro> getSemestreLista() {
		if (semestreLista == null) {
			Parametro semestreFiltro = new Parametro();
			semestreFiltro.setTipo(EnumTipoParametro.SEMESTRE);
			if (this.seguimientoFiltro != null
					&& this.seguimientoFiltro.getSemestre() != null) {
				semestreFiltro.setCodigo(this.seguimientoFiltro.getSemestre()
						.getCodigo());
			}
			if (this.seguimiento != null
					&& this.seguimiento.getSemestre() != null) {
				semestreFiltro.setCodigo(this.seguimiento.getSemestre()
						.getCodigo());
			}
			semestreLista = servicioCrud.findOrder(semestreFiltro);
		}
		return semestreLista;
	}

	public void setSemestreLista(List<Parametro> semestreLista) {
		this.semestreLista = semestreLista;
	}

	public List<Parametro> getCarreraLista() {
		if (carreraLista == null) {
			Parametro semestreFiltro = new Parametro();
			semestreFiltro.setTipo(EnumTipoParametro.CARRERA);
			if (this.seguimientoFiltro != null
					&& this.seguimientoFiltro.getCarrera() != null) {
				semestreFiltro.setCodigo(this.seguimientoFiltro.getCarrera()
						.getCodigo());
			}
			if (this.seguimiento != null
					&& this.seguimiento.getCarrera() != null) {
				semestreFiltro.setCodigo(this.seguimiento.getCarrera()
						.getCodigo());
			}
			carreraLista = servicioCrud.findOrder(semestreFiltro, "nombre");
		}
		return carreraLista;
	}

	public void setCarreraLista(List<Parametro> carreraLista) {
		this.carreraLista = carreraLista;
	}

	public List<Materia> getMateriaLista() {
		if (materiaLista == null) {
			materiaLista = new ArrayList<Materia>();
			Materia materiaFiltro = new Materia();
			materiaFiltro.setEstado(EnumEstado.ACT);
			if (this.seguimientoFiltro != null
					&& this.seguimientoFiltro.getCarrera().getCodigo() != null) {
				materiaFiltro.setCarrera(this.seguimientoFiltro.getCarrera());
				materiaLista = servicioCrud.findOrder(materiaFiltro,"nombre");
			}
			if (this.seguimiento != null
					&& this.seguimiento.getCarrera().getCodigo() != null) {
				materiaFiltro.setCarrera(this.seguimiento.getCarrera());
				materiaLista = servicioCrud.findOrder(materiaFiltro,"nombre");
			}

		}
		return materiaLista;
	}

	public void guardaCalificacion(AjaxBehaviorEvent event) {
		SeguimientoSyllabus seguimientoData = (SeguimientoSyllabus) getExternalContext()
				.getRequestMap().get("item");
		servicioCrud.update(seguimientoData);
		String m = getBundleMensajes("registro.guardado.correctamente", null);
		addInfoMessage(m, m);
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

	public List<Usuario> getCoordinadorLista() {
		return coordinadorLista;
	}

	public void setCoordinadorLista(List<Usuario> coordinadorLista) {
		this.coordinadorLista = coordinadorLista;
	}

	public List<Usuario> getDirectorLista() {
		return directorLista;
	}

	public void setDirectorLista(List<Usuario> directorLista) {
		this.directorLista = directorLista;
	}

	public SeguimientoSyllabus getSeguimientoFiltroCoordinador() {
		return seguimientoFiltroCoordinador;
	}

	public void setSeguimientoFiltroCoordinador(
			SeguimientoSyllabus seguimientoFiltroCoordinador) {
		this.seguimientoFiltroCoordinador = seguimientoFiltroCoordinador;
	}

	public SeguimientoSyllabus getSeguimientoFiltroDirector() {
		return seguimientoFiltroDirector;
	}

	public void setSeguimientoFiltroDirector(
			SeguimientoSyllabus seguimientoFiltroDirector) {
		this.seguimientoFiltroDirector = seguimientoFiltroDirector;
	}
	
	

}
