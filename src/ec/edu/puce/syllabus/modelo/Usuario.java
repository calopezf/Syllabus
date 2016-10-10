package ec.edu.puce.syllabus.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ec.edu.puce.syllabus.constantes.EnumEstado;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    @Id
    @Column(name = "identificacion", length = 20)
    private String identificacion;//o username
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Column(name = "apellido", length = 200)
    private String apellido;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EnumEstado estado;
    @Column(name = "direccion", length = 255)
    private String direccion;
    @Column(name = "password", length = 64)
    private String password;
    @Column(name = "email", length = 64)
    private String email;
    @Transient
    private boolean tachado;
    @Transient
    private boolean registroNuevo;
    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioRegistra")
    // private List<Ficha> ficha;
//    @ManyToMany(mappedBy = "usuarios")
//    private List<Rol> roles;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = {
        @JoinColumn(name = "usuario_identificacion", referencedColumnName = "identificacion")},
    inverseJoinColumns =
    @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private List<Rol> roles;
    
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "institucion_id", referencedColumnName = "id")
//    private Institucion institucion;

//    public Institucion getInstitucion() {
//        return institucion;
//    }
//
//    public void setInstitucion(Institucion institucion) {
//        this.institucion = institucion;
//    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public boolean isRegistroNuevo() {
        return registroNuevo;
    }

    public void setRegistroNuevo(boolean registroNuevo) {
        this.registroNuevo = registroNuevo;
    }

    public EnumEstado getEstado() {
        return estado;
    }

    public void setEstado(EnumEstado estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isTachado() {
        if (this.estado.equals(EnumEstado.ACT)) {
            tachado = true;
        } else {
            tachado = false;
        }
        return tachado;
    }

    public void setTachado(boolean tachado) {
        this.tachado = tachado;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder("Usuario{" + "identificacion=" + identificacion + ", nombre=" + nombre + ", apellido=" + apellido + ", estado=" + estado + ", direccion=" + direccion + ", email=" + email + ", registroNuevo=" + registroNuevo + '}');
        
        toString.append(", roles=[");
        
        if (roles != null && roles.size()>0) {
            for (Rol r : roles) {
                toString.append(r.getId()).append(",");
            }
            toString.deleteCharAt(toString.length()-1);
        }
        toString.append("]");
        toString.append("}");
        
        return toString.toString();
    }
}