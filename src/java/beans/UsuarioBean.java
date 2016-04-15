/* -------------------------------------------------------------------
* UsuarioBean.java
* versión 1.0
* Copyright (C) 2016  Kan-Balam.
* Facultad de Ciencias,
* Universidad Nacional Autónoma de México, Mexico.
*
* Este programa es software libre; se puede redistribuir
* y/o modificar en los términos establecidos por la
* Licencia Pública General de GNU tal como fue publicada
* por la Free Software Foundation en la versión 2 o
* superior.
*
* Este programa es distribuido con la esperanza de que
* resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
* sin la garantía implícita de COMERCIALIZACIÓN o
* ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
* Licencia Pública General de GNU para mayores detalles.
*
* Con este programa se debe haber recibido una copia de la
* Licencia Pública General de GNU, de no ser así, visite el
* siguiente URL:
* http://www.gnu.org/licenses/gpl.html
* o escriba a la Free Software Foundation Inc.,
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
* -------------------------------------------------------------------
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;

import dao.UsuarioDAO;
import java.io.Serializable;
import java.sql.Date;
import modelo.Agente;
import modelo.Programador;
import modelo.Servicio;
import modelo.TipoUsuario;
import modelo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 * @author Jimenez Méndez Ricardo
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para poder manejar a los usuarios.</p>
 *
 * <p>
 * Clase controladora en particular de las sesiones de los usuarios.</p>
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {

    /**
     * Es el atributo para poder manejar al usuario actual de nuestra app.
     */
    private Usuario usuario = new Usuario();

    /**
     * Metodo que nos regresa al usuario de la clase, el atributo privado de la
     * clase.
     *
     * @return - El atributo usuario de la clase.
     */
    public Usuario getUsuario() {
        if(usuario.getFechaDeNaciminiento() == null)
            usuario.setFechaDeNaciminiento(new Date(1950, 01, 01));
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String registrar(){       
        return this.registrar(this.usuario.getTipoUsuario());
    }
    /**
     * Se registra al usuario en el sistema.
     *
     * @param tipo - el tipo de usuario a registrar.
     */
    public String registrar(TipoUsuario tipo) {               
        /* Primero verificamos que el usuario no esté registrado */
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Usuario WHERE correo = '" + usuario.getCorreo()
                + "'";
        Query query = session.createQuery(sql);
        if (!query.list().isEmpty()) {
            return "El usuario con ese correo ya existe.";
        } else {
            try {
                /* El usuario no existe por lo que lo registramos */
                Transaction tx = session.beginTransaction();
                /* La transacción actual. Se utiliza para que persistan los 
                    cambios que realicemos en la base */
                session.save(this.usuario);
                if (tipo == TipoUsuario.PROGRAMADOR) {
                    /* Registramos a un programador */
                    Programador p = new Programador(this.usuario);
                    /* El programador a registrar en la base */
                    session.save(p);
                } else if (tipo == TipoUsuario.AGENTE) {
                    /* Registramos a un agente */
                    Agente a = new Agente(this.usuario);
                    /* El agente a registrar en la base */
                    session.save(a);
                }
                session.flush();
                session.clear();
                tx.commit();
                session.close();                
                return verificarDatos();
            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }
    
    public String irModificar(){
        return "modificar";
    }

    public String modificarPerfil() {
       /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Session session = HibernateUtil.getSessionFactory().openSession();

            try {
                //Creamos el servicio nuevo.
                Transaction tx = session.beginTransaction();
                session.update(this.usuario);
                /* La transacción actual. Se utiliza para que persistan los 
                    cambios que realicemos en la base */            
                session.flush();
                session.clear();
                tx.commit();
                session.close();                
                return "perfil";
            } catch (Exception e) {
                return "error";
            }        
    }

    public String verificarDatos() throws Exception {
        UsuarioDAO suDAO = new UsuarioDAO();
        Usuario su;
        String resultado;
        try {
            su = suDAO.verificarDatos(this.usuario);
            if (su != null) {
                this.usuario = su;
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", su);

                resultado = "perfil";
            } else {
                resultado = "error";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public boolean verificarSesion() {
        boolean estado;
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") == null) {
            estado = false;
        } else {
            estado = true;
        }
        return estado;
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }
    
    
     /**
     * Se elimina al usuario del sistema.
     *
     * @return 
     */
    public String eliminar() {
        /* Primero verificamos que el usuario no esté registrado */
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            /* La transacción actual. Se utiliza para que persistan los 
                    cambios que realicemos en la base */
            Transaction tx = session.beginTransaction();
            /* Subrutina de eliminación sacada de codejava.net */
 /* Se convierte el id de usuario a serializable para utilizar el método load */
            /*
            Código temporal (agregar ON CASCADE)
            */
            if(this.usuario.esAgente()){
                Agente ag = this.usuario.getAgente();
                ag.getServicios().clear();
                Agente agDel = (Agente)session.merge(ag);
                session.delete(agDel);
            }else{
                Programador prog = this.usuario.getProgramador();
                prog.getServicios().clear();
                Programador progDel = (Programador)session.merge(prog);
                session.delete(progDel);
            }
         
            Serializable id = new Integer(usuario.getIdUsuario());
            session.delete(this.usuario);
            /* Se busca la instancia del usuario para eliminarla de la base *
            Object persistentInstance = session.get(Usuario.class, id);
            if (persistentInstance != null) {
                session.delete(persistentInstance);
            }
            /* No sé qué tanto de lo que sigue se necesite, pero igual lo 
                dejaré */
            session.flush();
            session.clear();
            tx.commit();
            session.close();
            return "index";
        } catch (Exception e) {
            System.out.println(e);   
            return e.getMessage();
        }
    }
} //Fin de UsuarioBean.java