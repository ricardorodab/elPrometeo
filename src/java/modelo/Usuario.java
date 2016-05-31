/* -------------------------------------------------------------------
* Usuario.java
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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * @author Kan-Balam
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para modelar a los usuarios.</p>
 *
 * <p>
 * Clase que modela a los usuarios.</p>
 */
@Entity
@Table(name = "usuario", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = "correo"),
    @UniqueConstraint(columnNames = "telefono")}
)
public class Usuario implements java.io.Serializable {
    /** Es el id del usuario. */
    private int idUsuario;
    /** Es el nombre del usuario. */
    private String nombre;
    /** Es el apellido paterno del usuario. */
    private String apellidoPaterno;
    /** Es el apellido materno del usuario. */
    private String apellidoMaterno;
    /** Es el teléfono del usuario. */
    private Long telefono;
    /** Es el correo del usuario. */
    private String correo;
    /** Es la contraseña del usuario. */
    private String contrasenia;
    /** Es el estado del usuario. */
    private String estado;
    /** Es la fecha de nacimiento del usuario. */
    private Date fechaDeNaciminiento;
    /** Es el género del usuario. */
    private String genero;
    /** Es el programador de un usuario. */
    private Programador programador;
    /** Es el agente de un usuario. */
    private Agente agente;
    /** Nos da el tipo de usuario. */
    private TipoUsuario tipo;
    /** La foto de perfil del Usuario. */
    private String imagen;
    /** Es el conjunto de mensajes del usuario. */
    private Set mensajesForIdRemitente = new HashSet(0);
    /** Es el conjunto de mensajes recibidos del usuario. */
    private Set mensajesForIdDestinatario = new HashSet(0);
    
    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }
    
    /**
     * Constructor.
     * @param idUsuario - es el id del usuario.
     * @param nombre - Es el nombre del usuario.
     * @param apellidoPaterno - Es el apellido paterno del usuario.
     * @param apellidoMaterno - Es el apellido matenro del usuario.
     * @param correo - Es el correo del usuario.
     * @param contrasenia - Es la contraseña del usuario.
     */
    public Usuario(int idUsuario, String nombre, String apellidoPaterno,
            String apellidoMaterno, String correo, String contrasenia) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }
    
    /**
     * Constructor.
     * @param nombre - Es el nombre del usuario.
     * @param apellidoPaterno - Es el apellido paterno del usuario.
     * @param apellidoMaterno - Es el apellido matenro del usuario.
     * @param correo - Es el correo del usuario.
     * @param contrasenia - Es la contraseña del usuario.
     * @param telefono - Es el teléfono del usuario.
     * @param estado - Es el estado del usuario.
     * @param fechaDeNaciminiento - Es la fecha de nacimiento del usuario.
     * @param genero - Es el género del usuario.
     * @param programador - Si es que es programador.
     * @param agente - si es que es agente.
     * @param imagen - Es la imagen del usuario.
     */
    public Usuario(String nombre, String apellidoPaterno,
            String apellidoMaterno, Long telefono, String correo,
            String contrasenia, String estado, Date fechaDeNaciminiento,
            String genero, Programador programador, Agente agente,
            String imagen) {
        //this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.estado = estado;
        this.fechaDeNaciminiento = fechaDeNaciminiento;
        this.genero = genero;
        this.programador = programador;
        this.agente = agente;
        this.imagen = imagen;
    }
    
    /**
     * Nos dice que tipo de usuario es.
     * @return Nos da Agente o Programador.
     */
    public TipoUsuario getTipoUsuario() {
        return this.tipo;
    }
    
    
    /** Pone el tipo del usuario.
     * @param tipo - Es el tipo de usuario.
     */
    public void setTipoUsuario(TipoUsuario tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Nos regresa el id del usuario.
     * @return El id del usuario.
     */
    @Id
    @Column(name = "id_usuario", unique = true, nullable = false)
    public int getIdUsuario() {
        return this.idUsuario;
    }
    
    /**
     * Asigna el id a un usuario.
     * @param idUsuario - El id del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    /**
     * Nos da el nombre del usuario.
     * @return - el nombre del usuario.
     */
    @Column(name = "nombre", nullable = false)
    public String getNombre() {
        return this.nombre;
    }
    
    /**
     * Asigna el nombre a un usuario.
     * @param nombre - El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Nos regresa el primer apellido del usuario.
     * @return El apellido paterno.
     */
    @Column(name = "apellido_paterno", nullable = false)
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }
    
    /**
     * Asigna un apellido al usuario.
     * @param apellidoPaterno - El nuevo apellido paterno.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    
    /**
     * Asigna el dia de nacimiento del usuario.
     * @param dia - Es el dia del 1 al 31.
     */
    public void setDia(int dia) {
        this.fechaDeNaciminiento.setDate(dia);
    }
    
    /**
     * Nos regresa el dia que nacio el usuario.
     * @return El dia que nacio.
     */
    public int getDia() {
        return this.fechaDeNaciminiento.getDate();
    }
    
    /**
     * Asigna el mes que nacio el usuario de 0 a 11.
     * @param mes - El nuevo mes.
     */
    public void setMes(int mes) {
        this.fechaDeNaciminiento.setMonth(mes);
    }
    
    /**
     * Nos regresa el mes que nacio el usuario.
     * @return El mes del usuario del 0 al 11.
     */
    public int getMes() {
        return this.fechaDeNaciminiento.getMonth();
    }
    
    /**
     * Asigna el año que nacio el usuario. 00 es para 1900.
     * @param anio - El año de nacimiento - 1900.
     */
    public void setAnio(int anio) {
        this.fechaDeNaciminiento.setYear(anio);
    }
    
    /**
     * Nos regresa el año de nacimiento del usuario.
     * @return El año de nacimiento del usuario - 1900.
     */
    public int getAnio() {
        return this.fechaDeNaciminiento.getYear();
    }
    
    /**
     * Nos regresa el segundo apellido del usuario.
     * @return El apellido materno.
     */
    @Column(name = "apellido_materno", nullable = false)
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }
    
    /**
     * Asigna el segundo apellido al usuario.
     * @param apellidoMaterno - El nuevo apellido materno.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    
    /**
     * Nos regresa el teléfono del usuario.
     * @return El teléfono del usuario.
     */
    @Column(name = "telefono", unique = true)
    public Long getTelefono() {
        return this.telefono;
    }
    
    /**
     * Asigna un telefono nuevo a un usuario.
     * @param telefono - El nuevo teléfono.
     */
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Nos regresa el correo del usuario.
     * @return - Es el correo del usuario.
     */
    @Column(name = "correo", unique = true, nullable = false)
    public String getCorreo() {
        return this.correo;
    }
    
    /**
     * Asigna un nuevo correo a un usuario.
     * @param correo - Es el nuevo correo.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    /**
     * Nos regresa la contraseña del usuario.
     * @return La contraseña.
     */
    @Column(name = "contrasenia", nullable = false)
    public String getContrasenia() {
        return this.contrasenia;
    }
    
    /**
     * Asigna una nueva contraseña al usuario.
     * @param contrasenia - La nueva contraseña.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    /**
     * Nos regresa el estado del usuario.
     * @return El estado del usuario.
     */
    @Column(name = "estado")
    public String getEstado() {
        return this.estado;
    }
    
    /**
     * Asigna un nuevo estado a el usuario.
     * @param estado - El nuevo estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Nos regresa la fecha de nacimiento del usuario.
     * @return La fecha en un Date de java.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_de_naciminiento", length = 13)
    public Date getFechaDeNaciminiento() {
        return this.fechaDeNaciminiento;
    }
    
    /**
     * Asigna una fecha de nacimiento al usuario.
     * @param fechaDeNaciminiento - La fecha en un date de java.
     */
    public void setFechaDeNaciminiento(Date fechaDeNaciminiento) {
        this.fechaDeNaciminiento = fechaDeNaciminiento;
    }
    
    /**
     * Nos regresa el género del usuario.
     * @return H si es hombre y M si es mujer.
     */
    @Column(name = "genero", length = 1)
    public String getGenero() {
        return this.genero;
    }
    
    /**
     * Asigna un nuevo género al usuario
     * @param genero - Es el nuevo género.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    
    /**
     * Nos regresa al usuario como instancia de tipo Programador
     * @return El programador del usuario.
     */
    public Programador getProgramador() {
        return this.programador;
    }
    
    
    /**
     * Le pone como atributo al Usuario su instancia de tipo Programador
     * @param programador - Es el nuevo programador.
     */
    public void setProgramador(Programador programador) {
        this.programador = programador;
    }
    
    /**
     * Nos regresa al usuario como instancia de tipo Agente
     * @return El agente del usuario.
     */
    public Agente getAgente() {
        return this.agente;
    }
    
    /**
     * Regresa la ruta a la foto de perfil del Usuario
     * @return La imagen en forma de URL.
     */
    public String getImagen() {
        return this.imagen;
    }
    
    /**
     * Establece la ruta a la foto de perfil del Usuario
     * @param imagen - Es la nueva imagen en formato URL.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    /**
     * Metodo para saber si un usuario es agente.
     * @return - true si el agente es diferente de null.
     */
    public boolean esAgente() {
        return this.agente != null;
    }
    
    /**
     * Le pone como atributo al Usuario su instancia de tipo Agente
     * @param agente - Es el nuevo agente del usuario.
     */
    public void setAgente(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * Nos regresa los mensajes que he enviado.
     * @return - Un conjunto con los mensajes que he enviado.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioByIdRemitente")
    public Set getMensajesForIdRemitente() {
        return this.mensajesForIdRemitente;
    }
    
    /**
     * Asigna un nuevo conjunto de mensajes enviados.
     * @param mensajesForIdRemitente - Los mensajes enviados.
     */
    public void setMensajesForIdRemitente(Set mensajesForIdRemitente) {
        this.mensajesForIdRemitente = mensajesForIdRemitente;
    }
    
    /**
     * Nos regresa los mensajes que he recibido.
     * @return - Un conjunto con los mensajes que he recibido.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioByIdDestinatario")
    public Set getMensajesForIdDestinatario() {
        return this.mensajesForIdDestinatario;
    }
    
    /**
     * Asigna un nuevo conjunto de mensajes recibidos.
     * @param mensajesForIdDestinatario - Los mensajes recibidos.
     */
    public void setMensajesForIdDestinatario(Set mensajesForIdDestinatario) {
        this.mensajesForIdDestinatario = mensajesForIdDestinatario;
    }
    
    /**
     * Regresa la ruta absoluta a la imagen del usuario
     * @return La ruta de la imagen en forma de URL.
     */
    public String getRutaImagen(){
        return System.getProperty("user.dir") + "/imagenes/" + this.getImagen();
    }   
} //Fin de Usuario.java