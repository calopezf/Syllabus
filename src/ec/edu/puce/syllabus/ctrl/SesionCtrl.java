/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.syllabus.ctrl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ec.edu.puce.syllabus.modelo.Usuario;
import ec.edu.puce.syllabus.servicio.ServicioUsuario;

/**
 *
 * @author cristian
 */
@ManagedBean(name = "sesionCtrl")
@SessionScoped
public class SesionCtrl extends BaseCtrl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private ServicioUsuario usuarioServicio;
	private Usuario usuarioLogueado;

	public void idleListener() {
		System.out.println("invalida la sesion");
		// invalidate session
		getHttpServletRequest().getSession(false).invalidate();
	}

	public Usuario getUsuarioLogueado() {
		if (usuarioLogueado == null) {
			usuarioLogueado = usuarioServicio
					.obtieneUsuarioXCedula(getRemoteUser());
		}
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
}