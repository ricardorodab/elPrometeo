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
 * @author Kan-Balam
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para poder manejar a los usuarios.</p>
 *
 * <p>
 * Clase controladora en particular de los usuarios.</p>
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {
    
    
    /** Es el atributo para poder manejar al usuario actual de nuestra app. */
    private Usuario usuario = new Usuario();
    /** Es el usuario ajeno a el usuario que inició sesión. */
    private Usuario ajeno = new Usuario();
    /** La imagen del usuario. */
    private UploadedFile imagen;
    /** Es el objeto para acceder a la base de datos. */
    private final OperacionesDAO dao;
    /** Es el objeto para controlas las imágenes. */
    private File file = new File("");
    /** La calificación con la que va a calificar el usuario a otro. */
    private double calificacion;
    /**
     * Metodo contructor de la clase.
     */
    public UsuarioBean() {
        dao = new OperacionesDAO();
    }
    
    /**
     * Regresa la imagen del usuario.
     * @return Una imagen que se haya subido.
     */
    public UploadedFile getImagen() {
        return imagen;
    }
    
    /**
     * Metodo para asignar un archivo al objeto del usuario.
     * @param file - Es el archivo que incluirá la imagen.
     */
    public void setFile(File file){
        this.file = file;
    }
    
    /**
     * Nos regresa el archivo del objeto usuarioBean.
     * @return Un file o null si no se encuentra.
     */
    public File getFile(){
        return this.file;
    }
    
    /**
     * Pone la imagen del usuario.
     * @param img - Es la imagen a asignar al objeto.
     */
    public void setImagen(UploadedFile img) {
        this.imagen = img;
    }
    
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
    
    /**
     * Metodo que nos regresa el usuario actual.
     * @return Un usuario el cual ha iniciado sesión o uno nuevo si no existe.
     */
    public Usuario getAjeno() {
        if (ajeno.getFechaDeNaciminiento() == null) {
            ajeno.setFechaDeNaciminiento(new Date(1950, 01, 01));
        }
        return ajeno;
    }
    
    /**
     * Metodo que asigna un usuario ajeno a el objeto.
     * @param ajeno - Es el usuario ajeno a el que inició sesión.
     */
    public void setAjeno(Usuario ajeno) {
        this.ajeno = ajeno;
    }
    
    /**
     * Metodo que asigna un usuario a nuestra clase UsuarioBean.
     * @param usuario - Es el nuevo usuario que se le asigna.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Metodo para registrar un nuevo usuario en la base de datos.
     * @return error o servicio para redirigir a usuarios.
     */
    public String registrar() {
        return this.registrar(this.usuario.getTipoUsuario());
    }
    
    /**
     * Se registra al usuario en el sistema.
     *
     * @param tipo - el tipo de usuario a registrar.
     * @return Una cadena para saber si se llevó acabo con exito el registro.
     */
    public String registrar(TipoUsuario tipo) {
        
        /*
        * Primero verificamos que el usuario no esté registrado
        */
        try {
            Usuario u;
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
    
    /**
     * Guarda la imagen del usuario actual.
     * @return Una cadena para saber si la imagen se guardó correctamente.
     * @throws IOException - En caso de que no se procese bien la imagen.
     * @throws Exception  - En caso de un error no contemplado.
     */
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
                FacesContext.getCurrentInstance().getExternalContext().redirect("subirImagen.xhtml");
                return "subirImagen";
            }
        } else {
            return "El archivo no es una imagen";
        }
    }
    
    /**
     * Metodo para ir al usuario ajeno a el usuario actual.
     * @param user - Es el usuario que buscamos acceder.
     * @return El perfil del usuario o una página para ver si está bloqueado.
     */
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
    
    /**
     * Metodo que nos da la ruta de las imagenes.
     * @return Una ruta de la imagen.
     */
    public String getRutaImagen(){
        return System.getProperty("user.dir")+"/"+this.usuario.getImagen();
    }
    
    /**
     * Metodo que nos redirige a modificar perfil.
     * @return El perfil a modificar.
     */
    public String irModificar() {
        return "modificar";
    }
    
    /**
     * Metodo para modificar un perfil de un usuario.
     * @return El perfil modificado o un error.
     */
    public String modificarPerfil() {
        boolean actualizado = dao.actualizaUsuario(usuario);
        return actualizado ? "perfil" : "error";
    }
    
    /**
     * Metodo para verificar si un usuario inicio sesión correctamente.
     * @return Los servicios si inició sesión correctamente.
     */
    public String verificarDatos() {
        Usuario su = dao.verificarDatos(usuario);
        if (su != null) {
            usuario = su;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", su);
            return "servicio";
        }
        return "error";
    }
    
    /**
     * Metodo para verificar una sesión de un usuario.
     * @return true si existe una sesión con un usuario.
     */
    public boolean verificarSesion() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") != null;
    }
    
    /**
     * Metodo que verifica si un usuario se encuentra conectado.
     * @return una cadena para saber que se debe realizar.
     */
    public String verificaConectado() {
        boolean result = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") != null;
        if (!result) {
            return "inicia-sesion-now";
        }
        return "";
    }
    
    /**
     * Metodo para cerrar una sesión.
     * @return la página principal de la página.
     */
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
    
    /**
     * Metodo para obtener las calificaciones de un usuario.
     * @param aj - Es el usuario a recibir las calificaciones.
     * @return un doble con las calificaciones.
     */
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
    
    /**
     * Metodo para bloquear a dos usuarios.
     * @param yo - El usuario que bloquea.
     * @param u - El usuario que se desea bloquear.
     * @return Una cadena para redirigir a los servicios.
     */
    public String bloquear(Usuario yo, Usuario u) {
        return dao.bloquear(yo, u);
    }
    
    /**
     * El usuario calificador califica al calificado.
     * @param calificador - Es el usuario que califica.
     * @param calificado - Es el usuario calificado.
     * @param c - Es la calificación.
     * @return Nos regresa a los servicios.
     */
    public String califica(Usuario calificador, Usuario calificado,double c) {
        this.calificacion = c;
        return dao.califica(calificador, calificado, this.calificacion);
    }
} //Fin de UsuarioBean.java