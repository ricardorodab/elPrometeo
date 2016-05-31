/* -------------------------------------------------------------------
 * Mensaje.java
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
// Generated May 6, 2016 1:12:11 AM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Kan-Balam
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para modelar a los mensajes.</p>
 *
 * <p>
 * Clase que modela a los mensajes.</p>
 */
@Entity
@Table(name = "mensaje", schema = "public"
)
public class Mensaje implements java.io.Serializable {
    /** Es el id de los mensajes. */
    private int idMensaje;
    /** Es el servicio del mensaje. */
    private Servicio servicio;
    /** Es el usuario que envia el mensaje. */
    private Usuario usuarioByIdRemitente;
    /** Es el usuario que recibe el mensaje. */
    private Usuario usuarioByIdDestinatario;
    /** Es el contenido del mensaje. */
    private String texto;
    /** Es la fecha de envío del mensaje. */
    private Date fechaDeEnvio;

    /** 
     * Contructor del mensaje.
     */
    public Mensaje() {
    }

    /**
     * Contructor del mensaje.
     * @param idMensaje - Es el id del mensaje.
     * @param fechaDeEnvio - Es la fecha de envío.
     */
    public Mensaje(int idMensaje, Date fechaDeEnvio) {
        this.idMensaje = idMensaje;
        this.fechaDeEnvio = fechaDeEnvio;
    }

    /**
     * Constructor del mensaje.
     * @param idMensaje - Es el id del mensaje.
     * @param servicio - Es el servicio del mensaje.
     * @param usuarioByIdRemitente - Es el usuario que envía.
     * @param usuarioByIdDestinatario - Es el usuario que recibe.
     * @param texto - Es el contenido del mensaje.
     * @param fechaDeEnvio - Es la fecha de envío.
     */
    public Mensaje(int idMensaje, Servicio servicio,
            Usuario usuarioByIdRemitente, Usuario usuarioByIdDestinatario,
            String texto, Date fechaDeEnvio) {
        this.idMensaje = idMensaje;
        this.servicio = servicio;
        this.usuarioByIdRemitente = usuarioByIdRemitente;
        this.usuarioByIdDestinatario = usuarioByIdDestinatario;
        this.texto = texto;
        this.fechaDeEnvio = fechaDeEnvio;
    }

    /**
     * Metodo para obtener el id del mensaje.
     * @return el id del mensaje.
     */
    @Id
    @Column(name = "id_mensaje", unique = true, nullable = false)
    public int getIdMensaje() {
        return this.idMensaje;
    }

    /**
     * Metodo para asignar un id a un mensaje.
     * @param idMensaje - Es el nuevo id.
     */
    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    /**
     * Metodo para obtener servicios del mensaje.
     * @return El servicio del mensaje.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio")
    public Servicio getServicio() {
        return this.servicio;
    }

    /**
     * Metodo para asignar un servicio a un mensaje.
     * @param servicio - Es el servicio a asignar.
     */
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Metodo para obtener el usuario que envía.
     * @return - El usuario que envia.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_remitente")
    public Usuario getUsuarioByIdRemitente() {
        return this.usuarioByIdRemitente;
    }

    /**
     * Metodo para asignar al usuario que envía.
     * @param usuarioByIdRemitente - El usuario que envía.
     */
    public void setUsuarioByIdRemitente(Usuario usuarioByIdRemitente) {
        this.usuarioByIdRemitente = usuarioByIdRemitente;
    }

    /**
     * Metodo para obtener el usuario que recibe.
     * @return - El usuario que recibe.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_destinatario")
    public Usuario getUsuarioByIdDestinatario() {
        return this.usuarioByIdDestinatario;
    }

    /**
     * Metodo para asignar al usuario que recibe.
     * @param usuarioByIdDestinatario - El usuario que recibe.
     */
    public void setUsuarioByIdDestinatario(Usuario usuarioByIdDestinatario) {
        this.usuarioByIdDestinatario = usuarioByIdDestinatario;
    }

    /**
     * Nos regresa el contenido del mensaje.
     * @return El contenido del mensaje.
     */
    @Column(name = "texto")
    public String getTexto() {
        return this.texto;
    }

    /**
     * Metodo para asignar contenido a un mensaje.
     * @param texto - Es el contenido del mensaje.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Nos regresa la fecha de envío del mensaje.
     * @return La fecha de envío.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_de_envio", nullable = false, length = 29)
    public Date getFechaDeEnvio() {
        return this.fechaDeEnvio;
    }

    /**
     * Asignamos la fecha de envío de un mensaje.
     * @param fechaDeEnvio - La fecha de envío.
     */
    public void setFechaDeEnvio(Date fechaDeEnvio) {
        this.fechaDeEnvio = fechaDeEnvio;
    }
} //Fin de Mensaje.java