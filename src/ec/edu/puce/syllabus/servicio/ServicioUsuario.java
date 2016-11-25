/**
 * UsuarioservicioImpl.java
 * 
* Thu Sep 26 17:17:42 ECT 2013
 */
package ec.edu.puce.syllabus.servicio;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.RandomStringUtils;

import ec.edu.puce.syllabus.crud.ServicioCrud;
import ec.edu.puce.syllabus.modelo.Usuario;
import ec.edu.puce.syllabus.utils.MailMessage;
import ec.edu.puce.syllabus.utils.Md5;

@Stateless(name = "UsuarioServicio")
@LocalBean
public class ServicioUsuario {

@EJB
private ServicioCrud servicioCrud;
//@EJB
//private ClienteQueueMailServicio clienteQueueMailServicio;
@EJB
private EmailServicio emailServicio;
    
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
    

    public void enviaMailCambioClave(Usuario usuario) {
        StringBuffer txt = new StringBuffer(200);
        txt.append("Estimado usuario/a, se ha generado una nueva clave, sus credenciales de acceso son:<br/><br/>Usuario: ").append(usuario.getIdentificacion());
        txt.append("<br/>").append("Clave: ").append(usuario.getPassword());
        txt.append("<br/>");

        List<String> to = new ArrayList<String>();
        to.add(usuario.getEmail());

        MailMessage mailMessage = new MailMessage();
        mailMessage.setSubject("SYLLABUS ");
        mailMessage.setText(txt.toString());
        mailMessage.setTo(to);

        //Se encola el mail
//        clienteQueueMailServicio.encolarMail(mailMessage);
    }


    public void generaCadenaAleatoriaYEnviaMail(Usuario usuario) throws NoSuchAlgorithmException {
        System.out.println("entra generaCadenaAleatoria");
        String code = RandomStringUtils.randomAlphanumeric(6);
        System.out.println("code: " + code);
//        String claveMd5 = Md5.hash(code);

        //Se actualiza clave de usuario
        usuario.setPassword(usuario.getIdentificacion());
        servicioCrud.update(usuario);

        enviaMailCambioClave(usuario);
        System.out.println("sale generaCadenaAleatoria");
    }
    

}
