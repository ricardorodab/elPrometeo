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
import modelo.TipoUsuario;
import modelo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
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
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
            
            try{/* El usuario no existe por lo que lo registramos */
            session.save(this.usuario);
            if(tipo == TipoUsuario.PROGRAMADOR){
                /* Registramos a un programador */
            } else if (tipo == TipoUsuario.AGENTE){
                /* Registramos a un agente */
            }
                return "Registro exitoso.";
            }catch(Exception e){
                return e.getMessage();
            }
        }
    }

    public String verificarDatos() throws Exception {
        UsuarioDAO suDAO = new UsuarioDAO();
        Usuario su;
        String resultado;
        try {
            su = suDAO.verificarDatos(this.usuario);
            if (su != null) {
                for (int i = 0; i < 10; i++) {
                    System.out.println(su.getNombre());
                }
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", su);

                resultado = "exito";
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
} //Fin de UsuarioBean.java
