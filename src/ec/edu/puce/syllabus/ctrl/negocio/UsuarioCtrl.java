package ec.edu.puce.syllabus.ctrl.negocio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.constantes.EnumTipoParametro;
import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.ctrl.BaseCtrl;
import ec.edu.puce.syllabus.modelo.Parametro;
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
	private List<Parametro> referenciaLista;
	private DualListModel<String> componenteRoles;

	@PostConstruct
	public void postConstructor() {
		this.usuarioFiltro = new Usuario();
	}

	private String destination = "C:\\Java\\wildfly-8.2.1.Final\\standalone\\deployments\\Syllabus.war\\img\\";

	public void upload(FileUploadEvent event) {
		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());
			FacesMessage message = new FacesMessage(
					"El archivo se ha subido con éxito!");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void copyFile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(destination
					+ fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
			System.out.println("El archivo se ha creado con éxito!");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
			Date date = new Date();
			String ruta1 = destination + fileName;
			String nombre = dateFormat.format(date) + "-" + fileName;
			String ruta2 = destination + nombre;
			System.out.println("Archivo: " + ruta1 + " Renombrado a: " + ruta2);
			File archivo = new File(ruta1);
			archivo.renameTo(new File(ruta2));
			usuario.setFoto(nombre);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getImagePath() {
		return usuario.getFoto() != null ? "/img/" + usuario.getFoto() : null;
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
				usuario.setReferencia(new Parametro());
				List<String> rolTarget = new ArrayList<String>();
				List<String> rolSource = new ArrayList<String>();
				List<Rol> rolesBase = rolServicio.devuelveRolesActivos();
				for (Rol rol : rolesBase) {
					rolSource.add(rol.getId().toString());
				}
				componenteRoles = new DualListModel<String>(rolSource,
						rolTarget);
			} else {
				usuario = usuarioServicio.obtieneUsuarioXCedula(usuarioId);
				usuario.setRegistroNuevo(false);
				List<String> rolTarget = new ArrayList<String>();
				List<String> rolSource = new ArrayList<String>();
				for (Rol rol : usuario.getRoles()) {
					rolTarget.add(rol.getId().toString());
				}
				List<Rol> rolesBase = rolServicio.devuelveRolesActivos();
				for (Rol rol : rolesBase) {
					if(!rolTarget.contains(rol.getId().toString())){
						rolSource.add(rol.getId().toString());
					}
				}
				componenteRoles = new DualListModel<String>(rolSource,
						rolTarget);
				setRolesSeleccionados(rolTarget);
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
			if (validadorDeCedula(this.usuario.getIdentificacion())) {
				// pone los roles seleccionados
				List<Rol> rolesXUsuario = new ArrayList<Rol>();
				Rol rolNuevo;
				for (String id : getComponenteRoles().getTarget()) {
					rolNuevo = servicioCrud.findById(id, Rol.class);
					rolesXUsuario.add(rolNuevo);
				}
				usuario.setRoles(rolesXUsuario);
				Usuario usuarioEnBase = usuarioServicio
						.obtieneUsuarioXCedula(usuario.getIdentificacion());
				if (usuarioEnBase.getIdentificacion() == null) {
					this.usuario.setPassword(this.usuario.getIdentificacion());
					servicioCrud.insert(this.usuario);
				} else {
					servicioCrud.update(this.usuario);
				}
				System.out.println("guardado...");
				String m = getBundleMensajes("registro.guardado.correctamente",
						null);
				addInfoMessage(m, m);
			} else {
				return null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			String m = getBundleMensajes("registro.noguardado.exception",
					new Object[] { e.getMessage() });
			addErrorMessage(m, m, null);
			return null;
		}

		return "/paginas/usuarios/usuarioLista";
	}

	public void validaCedula(AjaxBehaviorEvent event) {
		validadorDeCedula(this.usuario.getIdentificacion());
	}

	public boolean validadorDeCedula(String cedula) {
		boolean cedulaCorrecta = false;

		try {

			if (cedula.length() == 10) // ConstantesApp.LongitudCedula
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					// Coeficientes de validación cédula
					// El decimo digito se lo considera dígito verificador
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9, 10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1))
								* coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}

					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					} else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			System.out
					.println("Una excepcion ocurrio en el proceso de validadcion");
			cedulaCorrecta = false;
		}

		if (!cedulaCorrecta) {
			addErrorMessage("cedula",
					getBundleMensajes("numeroCedulaIncorrecto", null), "");
		}
		return cedulaCorrecta;
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

	public void enviaContraseniaNueva() {
		try {
			Usuario usuarioRecuperado = servicioCrud.findByPK(
					usuario.getIdentificacion(), Usuario.class);
			if (usuarioRecuperado == null) {
				throw new NoSuchAlgorithmException();
			}
			// String emailUsuario =
			// usuarioServicio.obtieneEmailXCedula(usuario.getIdentificacion());
			System.out.println("usuarioRecuperado: " + usuarioRecuperado);
			this.usuarioServicio
					.generaCadenaAleatoriaYEnviaMail(usuarioRecuperado);

			String m = getBundleMensajes("clave.reseteada.correctamente", null);
			addInfoMessage(m, m);

		} catch (NoSuchAlgorithmException nae) {
			nae.printStackTrace();
			String m = getBundleMensajes("no.existe.usuario", null);
			addErrorMessage(m, m, m);
		}
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

	public List<Parametro> getReferenciaLista() {
		if (this.referenciaLista == null) {
			this.referenciaLista = new ArrayList<Parametro>();
			if (componenteRoles.getTarget() != null) {
				int contaProfe = 0;
				for (String s : componenteRoles.getTarget()) {
					if (!s.equals("ALUMNO") && contaProfe < 1) {
						contaProfe++;
						Parametro referenciaFiltro = new Parametro();
						referenciaFiltro
								.setTipo(EnumTipoParametro.OCUPACION_PROFESOR);
						referenciaFiltro.setEstado(EnumEstado.ACT);
						for (Parametro a : servicioCrud
								.findOrder(referenciaFiltro)) {
							this.referenciaLista.add(a);
						}
					}
					if (s.equals("ALUMNO")) {
						Parametro referenciaFiltro = new Parametro();
						referenciaFiltro
								.setTipo(EnumTipoParametro.NIVEL_ALUMNO);
						referenciaFiltro.setEstado(EnumEstado.ACT);
						for (Parametro a : servicioCrud
								.findOrder(referenciaFiltro)) {
							this.referenciaLista.add(a);
						}
					}
				}
			}
		}
		return referenciaLista;
	}

	public void setReferenciaLista(List<Parametro> referenciaLista) {
		this.referenciaLista = referenciaLista;
	}

	public void cambiaRoles(AjaxBehaviorEvent event) {
		this.referenciaLista = null;
	}

	public DualListModel<String> getComponenteRoles() {
		if (componenteRoles == null) {
			componenteRoles = new DualListModel<String>();
		}
		return componenteRoles;
	}

	public void setComponenteRoles(DualListModel<String> componenteRoles) {
		this.componenteRoles = componenteRoles;
	}

}
