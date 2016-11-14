package ec.edu.puce.syllabus.ctrl.negocio;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.ctrl.BaseCtrl;
import ec.edu.puce.syllabus.modelo.Rol;
import ec.edu.puce.syllabus.modelo.Usuario;
import ec.edu.puce.syllabus.servicio.ServicioRol;
import ec.edu.puce.syllabus.servicio.ServicioUsuario;

@ManagedBean(name = "usuarioCtrl")
@ViewScoped
public class UsuarioCtrl extends BaseCtrl {

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
	private Usuario usuario;
	private Usuario usuarioFiltro;
	private Map<String, String> roles;
	private List<String> rolStringSeleccionados;
	private List<String> rolesSeleccionados;
	private List<Usuario> usuarios;

	@PostConstruct
	public void postConstructor() {
		this.usuarioFiltro = new Usuario();
	}
	
	/*
	 * @return the usuario
	 */
	public Usuario getUsuario() throws NoSuchAlgorithmException {
		if (usuario == null) {
			String usuarioId = getHttpServletRequestParam("idUsuario");
			if (usuarioId == null) {
				usuario = new Usuario();
				usuario.setRegistroNuevo(true);
				usuario.setRoles(new ArrayList<Rol>());
			} else {
				usuario = usuarioServicio.obtieneUsuarioXCedula(usuarioId);
				usuario.setRegistroNuevo(false);
				List<String> lista = new ArrayList<String>();
				for (Rol rol : usuario.getRoles()) {
					lista.add(rol.getId().toString());
				}
				setRolesSeleccionados(lista);
			}
		}

		return usuario;
	}
	
	/**
	 * @param to
	 *            setusuario.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void eliminarUsuario() {
		try {
			Usuario usuarioData = (Usuario) getExternalContext()
					.getRequestMap().get("item");
			servicioCrud.remove(usuarioData.getIdentificacion(), Usuario.class);
			addInfoMessage(
					getBundleMensajes("mensaje.informacion.elimina.exito", null),
					"");
			this.usuarios = null;
		} catch (Exception e) {
			addErrorMessage(null, e.getMessage(), "");
		}
	}

	public String guardar() {

		try {
			// pone los roles seleccionados
			List<Rol> rolesXUsuario = new ArrayList<Rol>();
			Rol rolNuevo;
			for (String id : getRolesSeleccionados()) {
				rolNuevo = servicioCrud.findById(id, Rol.class);
				rolesXUsuario.add(rolNuevo);
			}
			usuario.setRoles(rolesXUsuario);
			Usuario usuarioEnBase = usuarioServicio
					.obtieneUsuarioXCedula(usuario.getIdentificacion());
			if (usuarioEnBase == null) {
				this.usuario.setPassword(this.usuario.getIdentificacion());
				servicioCrud.insert(usuario);
			} else {
				servicioCrud.update(usuario);
			}
			System.out.println("guardado...");
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

		return "/paginas/usuarios/usuarioLista";
	}

	public String editar() {
		Usuario usuarioData = (Usuario) getExternalContext().getRequestMap()
				.get("item");
		return "/paginas/usuarios/usuario?faces-redirect=true&idUsuario="
				+ usuarioData.getIdentificacion();
	}

	public Map<String, String> getRoles() {
		if (roles == null) {
			List<Rol> rolesBase = rolServicio.devuelveRolesActivos();
			roles = new HashMap<String, String>();
			if (rolesBase != null && !rolesBase.isEmpty()) {
				for (Rol rol : rolesBase) {
					roles.put(rol.getDescripcion().toString(), rol.getId()
							.toString());
				}
			}
		}
		return roles;
	}

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

	public List<String> getRolesSeleccionados() throws NoSuchAlgorithmException {
		if (rolesSeleccionados == null) {
			rolesSeleccionados = new ArrayList<String>();
			getUsuario();
		}
		return rolesSeleccionados;
	}

	public void setRolesSeleccionados(List<String> rolesSeleccionados) {
		this.rolesSeleccionados = rolesSeleccionados;
	}

	public ServicioUsuario getUsuarioServicio() {
		return usuarioServicio;
	}

	public void setUsuarioServicio(ServicioUsuario usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public ServicioRol getRolServicio() {
		return rolServicio;
	}

	public void setRolServicio(ServicioRol rolServicio) {
		this.rolServicio = rolServicio;
	}

	public List<String> getRolStringSeleccionados() {
		if (rolStringSeleccionados == null) {
			rolStringSeleccionados = new ArrayList<String>();
		}
		return rolStringSeleccionados;
	}

	public void setRolStringSeleccionados(List<String> rolStringSeleccionados) {
		this.rolStringSeleccionados = rolStringSeleccionados;
	}

	public void resetearContrasenia() {
		Usuario usuarioData = (Usuario) getExternalContext().getRequestMap()
				.get("item");
		usuarioData.setPassword(usuarioData.getIdentificacion());
		servicioCrud.update(usuarioData);
	}

	public void buscar() {
		this.usuarios = null;
	}

	public List<Usuario> getUsuarios() {
		if (this.usuarios == null) {
			usuarios = this.servicioCrud.findOrder(this.usuarioFiltro);
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioFiltro() {
		return usuarioFiltro;
	}

	public void setUsuarioFiltro(Usuario usuarioFiltro) {
		this.usuarioFiltro = usuarioFiltro;
	}

}
