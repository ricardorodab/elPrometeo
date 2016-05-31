/* -------------------------------------------------------------------
* Agente.java
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
 * Clase para modelar a los Agentes.</p>
 *
 * <p>
 * Clase que modela a los Agentes.</p>
 */
public class Agente  implements java.io.Serializable {
    /** Es el id del agente. */
    private int idAgente;
    /** Es el usuario que tiene asociado al agente. */
    private Usuario usuario;
    /** Es la ocupación del agente. */
    private String ocupacion;
    /** Es la reputación del agente. */
    private Double reputacionAgente;
    /** Son los servicios del agente. */
    private Set servicios = new HashSet(0);
    
    /**
     * Metodo constructor del agente por defecto.
     */
    public Agente() {
    }
    
    /**
     * Metodo que contruye a un agente dado un usuario.
     * @param usuario - El usuario como super clase del agente.
     */
    public Agente(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Metodo para contruir a un agente desde cero.
     * @param usuario - Es el usuario super clase del agente.
     * @param ocupacion - Es la ocupación del agente.
     * @param reputacionAgente - Es la reputación del agente.
     * @param servicios - Son los servicios del agente.
     */
    public Agente(Usuario usuario, String ocupacion, Double reputacionAgente, Set servicios) {
        this.usuario = usuario;
        this.ocupacion = ocupacion;
        this.reputacionAgente = reputacionAgente;
        this.servicios = servicios;
    }
    
    /**
     * Nos regresa el id del agente.
     * @return El id del agente.
     */
    public int getIdAgente() {
        return this.idAgente;
    }
    
    /**
     * Asignamos el id del agente.
     * @param idAgente - El nuevo id del agente.
     */
    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }
    
    /**
     * Nos regresa al usuario del agente.
     * @return El usuario del agente.
     */
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    /**
     * Asigna un usuario a un agente.
     * @param usuario - Es el usuario a asignar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Nos regresa la ocupación del agente.
     * @return La ocupación del agente.
     */
    public String getOcupacion() {
        return this.ocupacion;
    }
    
    /**
     * Metodo para asignar la ocupación de un agente.
     * @param ocupacion - La ocupación del agente.
     */
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
    
    /**
     * Metodo para obtener la reputación del agente.
     * @return La reputación del 1 al 5 del agente.
     */
    public Double getReputacionAgente() {
        return this.reputacionAgente;
    }
    
    /**
     * Metodo para asignar una reputación a un agente.
     * @param reputacionAgente - La nueva reputación.
     */
    public void setReputacionAgente(Double reputacionAgente) {
        this.reputacionAgente = reputacionAgente;
    }
    
    /**
     * Metodo para obtener los servicios del agente.
     * @return Los servicios del agente.
     */
    public Set getServicios() {
        return this.servicios;
    }
    
    /**
     * Metodo para asignar servicios a un agente.
     * @param servicios - Los servicios en un conjunto.
     */
    public void setServicios(Set servicios) {
        this.servicios = servicios;
    }
    
    /**
     * Este metodo nos dice si un agente tiene ocupación.
     * @return true si tiene al en el campo ocupación.
     */
    public boolean tieneOcupacion(){
        return !(this.ocupacion == null || this.ocupacion.trim().equals(""));
    }
    
    /**
     * Metodo equals.
     * @param o - El objeto a verificar si es igual.
     * @return true si es igual o a el agente.
     */
    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        Agente temp = (Agente)o;
        if(temp.getIdAgente() != this.getIdAgente())
            return false;
        if(!temp.getOcupacion().equals(this.getOcupacion()))
            return false;
        if(this.getReputacionAgente() != temp.getReputacionAgente())
            return false;
        return true;
    }
} //Fin de Agente.java