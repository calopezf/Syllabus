package ec.edu.puce.syllabus.ctrl.negocio;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.ctrl.BaseCtrl;
import ec.edu.puce.syllabus.modelo.Materia;
import ec.edu.puce.syllabus.modelo.Usuario;
import ec.edu.puce.syllabus.servicio.ServicioRol;
import ec.edu.puce.syllabus.servicio.ServicioUsuario;

@ManagedBean(name = "materiaCtrl")
@ViewScoped
public class MateriaCtrl extends BaseCtrl {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;
	// TODO serializable de la clase: Usuario
	@EJB
	private ServicioUsuario usuarioServicio;
	@EJB
	private ServicioCrud servicioCrud;
	@EJB
	private ServicioRol rolServicio;
	private Materia materia;
	private Materia materiaFiltro;
	private List<Materia> materias;

	@PostConstruct
	public void postConstructor() {
		this.materiaFiltro = new Materia();
	}

	public Materia getMateria() {
		if (materia == null) {
			String materiaId = getHttpServletRequestParam("idMateria");
			if (materiaId == null) {
				materia = new Materia();
				materia.setEstado(EnumEstado.ACT);
			} else {
				materia = servicioCrud.findById(materiaId, Materia.class);
			}
		}
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public void eliminarMateria() {
		try {
			Materia materiaData = (Materia) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(materiaData.getCodigo(), Materia.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.materias = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			Materia materiaEnBase = servicioCrud.findById(
					this.materia.getCodigo(), Materia.class);
			if (materiaEnBase == null) {
				servicioCrud.insert(materia);
			} else {
				servicioCrud.update(materia);
			}
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

		return "/paginas/materia/materiaLista";
	}

	public String editar() {
		Materia materiaData = (Materia) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/materia/materia?faces-redirect=true&idMateria="
				+ materiaData.getCodigo();
	}
	
	public String navegarSyllabus() {
		Materia materiaData = (Materia) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/syllabus/syllabus?faces-redirect=true&idMateria="
				+ materiaData.getCodigo();
	}

	public void buscar() {
		this.materias = null;
	}

	public List<Materia> getMaterias() {
		if (this.materias == null) {
			materias = this.servicioCrud.findOrder(this.materiaFiltro);
		}
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public Materia getMateriaFiltro() {
		return materiaFiltro;
	}

	public void setMateriaFiltro(Materia materiaFiltro) {
		this.materiaFiltro = materiaFiltro;
	}

}
