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
 * Clase para modelar a los.</p>
 *
 * <p>
 * Clase que modela a los.</p>
 */
@Entity
@Table(name = "usuario", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = "correo"),
    @UniqueConstraint(columnNames = "telefono")}
)
public class Usuario implements java.io.Serializable {

    private int idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Long telefono;
    private String correo;
    private String contrasenia;
    private String estado;
    private Date fechaDeNaciminiento;
    private String genero;
    private Programador programador;
    private Agente agente;
    private TipoUsuario tipo;
    /* La foto de perfil del Usuario */
    private String imagen;
    private Set mensajesForIdRemitente = new HashSet(0);
    private Set mensajesForIdDestinatario = new HashSet(0);

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellidoPaterno,
            String apellidoMaterno, String correo, String contrasenia) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

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

    public TipoUsuario getTipoUsuario() {
        return this.tipo;
    }


    /* Pone el tipo del usuario */
    public void setTipoUsuario(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Id
    @Column(name = "id_usuario", unique = true, nullable = false)
    public int getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Column(name = "nombre", nullable = false)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "apellido_paterno", nullable = false)
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setDia(int dia) {
        this.fechaDeNaciminiento.setDate(dia);
    }
    
    public int getDia() {
        return this.fechaDeNaciminiento.getDate();
    }
    
    public void setMes(int mes) {
        this.fechaDeNaciminiento.setMonth(mes);
    }
    
    public int getMes() {
        return this.fechaDeNaciminiento.getMonth();
    }
    
    public void setAnio(int anio) {
        this.fechaDeNaciminiento.setYear(anio);
    }

    public int getAnio() {
        return this.fechaDeNaciminiento.getYear();
    }

    @Column(name = "apellido_materno", nullable = false)
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Column(name = "telefono", unique = true)
    public Long getTelefono() {
        return this.telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    @Column(name = "correo", unique = true, nullable = false)
    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Column(name = "contrasenia", nullable = false)
    public String getContrasenia() {
        return this.contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Column(name = "estado")
    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_de_naciminiento", length = 13)
    public Date getFechaDeNaciminiento() {
        return this.fechaDeNaciminiento;
    }

    public void setFechaDeNaciminiento(Date fechaDeNaciminiento) {
        this.fechaDeNaciminiento = fechaDeNaciminiento;
    }

    @Column(name = "genero", length = 1)
    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    /* Nos regresa al usuario como instancia de tipo Programador */
    public Programador getProgramador() {
        return this.programador;
    }


    /* Le pone como atributo al Usuario su instancia de tipo Programador */
    public void setProgramador(Programador programador) {
        this.programador = programador;
    }

    /* Nos regresa al usuario como instancia de tipo Agente */
    public Agente getAgente() {
        return this.agente;
    }

    /* Regresa la ruta a la foto de perfil del Usuario */
    public String getImagen() {
        return this.imagen;
    }

    /* Establece la ruta a la foto de perfil del Usuario */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean esAgente() {
        return this.agente != null;
    }

    /* Le pone como atributo al Usuario su instancia de tipo Agente */
    public void setAgente(Agente agente) {
        this.agente = agente;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioByIdRemitente")
    public Set getMensajesForIdRemitente() {
        return this.mensajesForIdRemitente;
    }

    public void setMensajesForIdRemitente(Set mensajesForIdRemitente) {
        this.mensajesForIdRemitente = mensajesForIdRemitente;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioByIdDestinatario")
    public Set getMensajesForIdDestinatario() {
        return this.mensajesForIdDestinatario;
    }

    public void setMensajesForIdDestinatario(Set mensajesForIdDestinatario) {
        this.mensajesForIdDestinatario = mensajesForIdDestinatario;
    }
    
    /* Regresa la ruta absoluta a la imagen del usuario */
    public String getRutaImagen(){
        return System.getProperty("user.dir") + "/imagenes/" + this.getImagen();
    }

}
