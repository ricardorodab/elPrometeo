/* -------------------------------------------------------------------
* Programador.java
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


import java.util.HashSet;
import java.util.Set;

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
public class Programador  implements java.io.Serializable {
    /** Es el id del programador. */
    private int idProgramador;
    /** Es el usuario que tiene asociado al programador. */
    private Usuario usuario;
    /** Es la formación del programador. */
    private String formacion;
    /** Es la reputación del programador. */
    private Double reputacionProgramador;
    /** Son las especialidades del programador. */
    private Set especialidads = new HashSet(0);
    /** Son los servicios del programador. */
    private Set servicios = new HashSet(0);
    
    /**
     * Metodo constructor del programador por defecto.
     */
    public Programador() {
    }
    
    /**
     * Metodo que contruye a un programador dado un usuario.
     * @param usuario - El usuario como super clase del programador.
     */
    public Programador(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Metodo para contruir a un programador desde cero.
     * @param usuario - Es el usuario super clase del programador.
     * @param formacion - Es la ocupación del programador.
     * @param reputacionProgramador - Es la reputación del programador.
     * @param especialidads
     * @param servicios - Son los servicios del programador.
     */
    public Programador(Usuario usuario, String formacion, Double reputacionProgramador, Set especialidads, Set servicios) {
        this.usuario = usuario;
        this.formacion = formacion;
        this.reputacionProgramador = reputacionProgramador;
        this.especialidads = especialidads;
        this.servicios = servicios;
    }
    
    /**
     * Nos regresa el id del programador.
     * @return El id del programador.
     */
    public int getIdProgramador() {
        return this.idProgramador;
    }
    
    /**
     * Asignamos el id del programador.
     * @param idProgramador - El nuevo id del programador.
     */
    public void setIdProgramador(int idProgramador) {
        this.idProgramador = idProgramador;
    }
    
    /**
     * Nos regresa al usuario del programador.
     * @return El usuario del programador.
     */
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    /**
     * Asigna un usuario a un programador.
     * @param usuario - Es el usuario a asignar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Nos regresa la ocupación del programador.
     * @return La ocupación del programador.
     */
    public String getFormacion() {
        return this.formacion;
    }
    
    /**
     * Metodo para asignar la ocupación de un programador.
     * @param formacion - La ocupación del programador.
     */
    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }
    
    /**
     * Metodo para obtener la reputación del programador.
     * @return La reputación del 1 al 5 del programador.
     */
    public Double getReputacionProgramador() {
        return this.reputacionProgramador;
    }
    
    /**
     * Metodo para asignar una reputación a un programador.
     * @param reputacionProgramador - La nueva reputación.
     */
    public void setReputacionProgramador(Double reputacionProgramador) {
        this.reputacionProgramador = reputacionProgramador;
    }
    
    /**
     * Metodo que nos regresa la especialidad de un programador.
     * @return La especialidad del programador.
     */
    public Set getEspecialidads() {
        return this.especialidads;
    }
    
    /**
     * Asigna la especialidad a un programador.
     * @param especialidads - Es la nueva especialidad.
     */
    public void setEspecialidads(Set especialidads) {
        this.especialidads = especialidads;
    }
    
    /**
     * Metodo para obtener los servicios del programador.
     * @return Los servicios del programador.
     */
    public Set getServicios() {
        return this.servicios;
    }
    
    /**
     * Metodo para asignar servicios a un programador.
     * @param servicios - Los servicios en un conjunto.
     */
    public void setServicios(Set servicios) {
        this.servicios = servicios;
    }
    
    /**
     * Este metodo nos dice si un programador tiene formacion.
     * @return true si tiene al en el campo ocupación.
     */
    public boolean tieneFormacion(){
        return !(this.formacion == null || this.formacion.trim().equals(""));
    }
    
    
}


