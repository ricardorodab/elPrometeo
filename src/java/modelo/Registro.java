/* -------------------------------------------------------------------
* Registro.java
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
package modelo;
// Generated 08-abr-2016 1:26:04 by Hibernate Tools 4.3.1



/**
 * @author Kan-Balam
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para modelar a los registros de mensajes.</p>
 *
 * <p>
 * Clase que modela a los registros de mensaje.</p>
 */
public class Registro  implements java.io.Serializable {
    /** Id del mensaje. */
    private int idMensaje;
    /** Es el mensaje. */
    private Mensaje mensaje;
    /** Es el servicio asociado. */
    private Servicio servicio;
    /** Es el id del usuario que envia. */
    private Integer envio;
    /** Es el id del usuario que recibe. */
    private Integer recibio;
    
    /**
     * Contructor por defecto.
     */
    public Registro() {
    }
    
    /**
     * Metodo constructor.
     * @param mensaje - Es el mensaje que se registra.
     * @param servicio - Es el servicio asociado a un mensaje.
     */
    public Registro(Mensaje mensaje, Servicio servicio) {
        this.mensaje = mensaje;
        this.servicio = servicio;
    }
    
    /**
     * Metodo constructor.
     * @param mensaje - Es el mensaje que se registra.
     * @param servicio - Es el servicio asociado a un mensaje.
     * @param envio - Es el id de quien envia.
     * @param recibio  - Es el id de quien recibe.
     */
    public Registro(Mensaje mensaje, Servicio servicio, Integer envio, Integer recibio) {
        this.mensaje = mensaje;
        this.servicio = servicio;
        this.envio = envio;
        this.recibio = recibio;
    }
    
    /**
     * Es el id del mensaje.
     * @return El id del mensaje.
     */
    public int getIdMensaje() {
        return this.idMensaje;
    }
    
    /**
     * Metodo que asigna un id a un mensaje.
     * @param idMensaje - Es el id del mensaje nuevo.
     */
    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }
    
    /**
     * Metodo que nos regresa un mensaje.
     * @return El mensaje del registro.
     */
    public Mensaje getMensaje() {
        return this.mensaje;
    }
    
    /**
     * Metodo que asigna un mensaje a un registro.
     * @param mensaje - Es el mensaje que se asigna al registro.
     */
    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    
    /**
     * Metodo que nos da un servicio de un registro.
     * @return Nos regresa el servicio del registro.
     */
    public Servicio getServicio() {
        return this.servicio;
    }
    
    /**
     * Asigna un servicio a un registro.
     * @param servicio - El servicio a ser registrado.
     */
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    
    /**
     * Nos da el id del usuario que envía.
     * @return el id del usuario que envia.
     */
    public Integer getEnvio() {
        return this.envio;
    }
    
    /**
     * Asigna un id a un usuario que envia.
     * @param envio - Es el id del usuario que envia.
     */
    public void setEnvio(Integer envio) {
        this.envio = envio;
    }
    
    /**
     * Nos da el id del usuario que recibe.
     * @return el id del usuario que recibe.
     */
    public Integer getRecibio() {
        return this.recibio;
    }
    
    /**
     * Es el metodo para asignar un id a quien recibe.
     * @param recibio - Es el id de quien recibe.
     */
    public void setRecibio(Integer recibio) {
        this.recibio = recibio;
    }
} //Fin de Registro.java