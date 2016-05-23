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
import java.util.Iterator;
import java.util.List;
import modelo.Servicio;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import modelo.TipoUsuario;
import modelo.Usuario;
import java.awt.image.BufferedImage;
import modelo.Agente;
import modelo.Programador;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

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
    private Usuario ajeno = new Usuario();
    /* La imagen del usuario (?)*/
    private UploadedFile imagen;
    private final OperacionesDAO dao;
    private File file = new File("");
    
    public UsuarioBean() {
        dao = new OperacionesDAO();
    }
    
    /* Regresa la imagen del usuario */
    public UploadedFile getImagen() {
        return imagen;
    }
    
    public void setFile(File file){
        this.file = file;
    }
    
    public File getFile(){
        return this.file;
    }
    
    /* Pone la imagen del usuario */
    public void setImagen(UploadedFile img) {
        this.imagen = img;
    }

    /* La calificación con la que va a calificar el 
    usuario a otro */
    private double calificacion;

    /**
     * Metodo que nos regresa al usuario de la clase, el atributo privado de la
     * clase.
     *
     * @return - El atributo usuario de la clase.
     */
    public Usuario getUsuario() {
        if (usuario.getFechaDeNaciminiento() == null) {
            usuario.setFechaDeNaciminiento(new Date(1950, 01, 01));
        }
        return usuario;
    }

    public Usuario getAjeno() {
        if (ajeno.getFechaDeNaciminiento() == null) {
            ajeno.setFechaDeNaciminiento(new Date(1950, 01, 01));
        }
        return ajeno;
    }

    public void setAjeno(Usuario ajeno) {
        this.ajeno = ajeno;
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
        try {
            Usuario u = dao.buscaUsuarioPorCorreo(usuario.getCorreo());
            u = dao.buscaUsuarioPorTelefono(usuario.getTelefono());
            if (u != null) {
                return "El usuario con ese correo o número telefónico ya existe.";
            } else {
                dao.guarda(usuario, tipo);
                return verificarDatos();
            }
        } catch (Exception e) {
            return "El usuario con ese correo o número telefónico ya existe.";
        }
    }
    
    /* Guarda la imagen del usuario actual */
    public String guardaImagen() throws IOException, Exception {        
        String type = imagen.getContentType();
        /* El formato de la imagen a guardar */
        String tipo = type.substring(6);
        if (type.startsWith("image")) {
            /* El InputStream de la imagen leída */
            InputStream inputStr;
            try {
                inputStr = imagen.getInputstream();
            } catch (IOException e) {
                return "La imagen subida tiene un error";
            }
            /* Crea el directorio si no existe */
            File directorio = new File(System.getProperty("user.dir") + "/imagenes");
            /* Crea el directorio de imagenes */
            directorio.mkdir();
            /* El id del usuario actual */
            String id = Integer.toString(this.usuario.getIdUsuario());
            /* La ruta de del destino */
            String destPath = System.getProperty("user.dir") + "/imagenes/"
                    + id + "." + tipo;
            this.usuario.setImagen(id + "." + tipo);
            dao.actualizaUsuario(this.usuario);
            /* Imágen a escribir */
            BufferedImage bi = ImageIO.read(inputStr);
            /* Archivo de destino */
            File destino = new File(destPath);
            ImageIO.write(bi, tipo, destino);
            //Revisar si existe.
            try{
            FileUtils.forceDelete(FileUtils.getFile("/Users/ricardo_rodab/NetBeansProjects/ElPrometeo/web/resources/imagenes/"+this.usuario.getImagen()));
            FileUtils.moveFile(destino, new File("/Users/ricardo_rodab/NetBeansProjects/ElPrometeo/web/resources/imagenes/"+this.usuario.getImagen()));
            }catch(Exception e){
                FileUtils.moveFile(destino, new File("/Users/ricardo_rodab/NetBeansProjects/ElPrometeo/web/resources/imagenes/"+this.usuario.getImagen()));    
            }finally{
                return "perfil";
            }
        } else {
            return "El archivo no es una imagen";
        }
    }
    
    public String irAjeno(Usuario user){
        this.ajeno = dao.buscaUsuario(user.getIdUsuario());
        List<Integer> list = dao.obtenListaDeBloqueados(this.usuario);
        Iterator i = list.iterator();
        while(i.hasNext()) {
            int u = (Integer) i.next();
            if(u == this.ajeno.getIdUsuario())
                return "usuarioBloqueado";
        }
        list = dao.obtenListaDeBloqueados(this.ajeno);
        i = list.iterator();
        while(i.hasNext()){
            int u = (Integer) i.next();
            if(u == this.usuario.getIdUsuario())
                return "usuarioBloqueado";
        }
        return "perfilAjeno";
    }
    
    public String getRutaImagen(){
        return System.getProperty("user.dir")+"/"+this.usuario.getImagen();
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

    public String verificaConectado() {
        boolean result = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") != null;
        if (!result) {
            return "inicia-sesion-now";
        }
        return "";
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
    
    public double getCalificacionAjeno(Usuario aj){
        try{
        Usuario u = dao.buscaUsuario(aj.getIdUsuario());
        if(u.esAgente()){
            Agente a = dao.buscaAgente(u.getAgente());            
            return a.getReputacionAgente();
        }
        Programador p = dao.buscaProgramador(u.getProgramador());
        return p.getReputacionProgramador();
        }catch(NullPointerException e){
            return 5;
        }
    }
   

    public String bloquear(Usuario yo, Usuario u) {
        return dao.bloquear(yo, u);
    }

    /* El usuario calificador califica al calificado */
    public String califica(Usuario calificador, Usuario calificado,double c) {
        this.calificacion = c;
        return dao.califica(calificador, calificado, this.calificacion);
    }

} //Fin de UsuarioBean.java
