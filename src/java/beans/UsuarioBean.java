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

import java.sql.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.OperacionesDAO;
import modelo.TipoUsuario;
import modelo.Usuario;

/**
 * @author Jimenez Méndez Ricardo
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para poder manejar a los usuarios.</p>
 *
 * <p>
 * Clase controladora en particular de las sesiones de los
 * usuarios.</p>
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {

    /**
     * Es el atributo para poder manejar al usuario actual de nuestra
     * app.
     */
    private Usuario usuario = new Usuario();

    private final OperacionesDAO dao;

    public UsuarioBean() {
        dao = new OperacionesDAO();
    }

    /**
     * Metodo que nos regresa al usuario de la clase, el atributo
     * privado de la clase.
     *
     * @return - El atributo usuario de la clase.
     */
    public Usuario getUsuario() {
        if (usuario.getFechaDeNaciminiento() == null) {
            usuario.setFechaDeNaciminiento(new Date(1950, 01, 01));
        }
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String registrar() {
        return this.registrar(this.usuario.getTipoUsuario());
    }

    /**
     * Se registra al usuario en el sistema.
     *
     * @param tipo - el tipo de usuario a registrar.
     */
    public String registrar(TipoUsuario tipo) {
        /*
         * Primero verificamos que el usuario no esté registrado
         */
        Usuario u = dao.buscaUsuarioPorCorreo(usuario.getCorreo());
        if (u == null) {
            return "El usuario con ese correo ya existe.";
        } else {
            dao.guarda(u, tipo);
            return verificarDatos();
        }
    }

    public String irModificar() {
        return "modificar";
    }

    public String modificarPerfil() {
        boolean actualizado = dao.actualizaUsuario(usuario);
        return actualizado ? "perfil" : "error";
    }

    public String verificarDatos() {
        Usuario su = dao.verificarDatos(usuario);
        if (su != null) {
            usuario = su;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", su);
            return "servicio";
        }
        return "error";
    }

    public boolean verificarSesion() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") != null;
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
        dao.elimina(usuario);
        return "index";
    }
} //Fin de UsuarioBean.java
