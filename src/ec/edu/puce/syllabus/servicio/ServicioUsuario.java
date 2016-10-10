/**
 * UsuarioservicioImpl.java
 * 
* Thu Sep 26 17:17:42 ECT 2013
 */
package ec.edu.puce.syllabus.servicio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.modelo.Usuario;

@Stateless(name = "UsuarioServicio")
@LocalBean
public class ServicioUsuario {

@EJB
private ServicioCrud servicioCrud;
    
    public Usuario obtenerPorCedulaClave(String cedula, String clave) {
    	Usuario usu=new Usuario();
    	usu.setIdentificacion(cedula);
    	usu.setPassword(clave);
    	List<Usuario> usuarios=this.servicioCrud.findOrder(usu);
    	
    	if(usuarios!=null && !usuarios.isEmpty()){
    		return usuarios.get(0);
    	}else {
    		return new Usuario();
    	}
    }

    public Usuario obtieneUsuarioXCedula(String cedula) {
    	Usuario usu=new Usuario();
    	usu.setIdentificacion(cedula);
    	List<Usuario> usuarios=this.servicioCrud.findOrder(usu);
    	
    	if(usuarios!=null && !usuarios.isEmpty()){
    		return usuarios.get(0);
    	}else {
    		return new Usuario();
    	}
    }
    

}
