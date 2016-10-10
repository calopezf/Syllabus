/**
 * RolservicioImpl.java
 * 
 * Thu Sep 26 17:13:37 ECT 2013
 */
package ec.edu.puce.syllabus.servicio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.modelo.Rol;

@Stateless(name = "RolServicio")
@LocalBean
public class ServicioRol {
	@EJB
	ServicioCrud servicioCrud;

	public List<Rol> devuelveRolesActivos() {
		Rol rol = new Rol();
		rol.setEstado(EnumEstado.ACT);
		return servicioCrud.findOrder(rol);
	}

	public List<Rol> obtieneRolesActivos() {
		Rol rol = new Rol();
		rol.setEstado(EnumEstado.ACT);
		return servicioCrud.findOrder(rol);
	}
	
}
