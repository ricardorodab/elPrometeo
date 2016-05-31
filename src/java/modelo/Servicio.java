/* -------------------------------------------------------------------
* Servicios.java
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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Kan-Balam
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para modelar a los servicios.</p>
 *
 * <p>
 * Clase que modela a los servicios.</p>
 */
@Entity
@Table(name = "servicio", schema = "public"
)
public class Servicio implements java.io.Serializable {
    /** Es el id del servicio. */
    private int idServicio;
    /** Es el presupuesto del servicio. */
    private Double presupuesto;
    /** Es la descripción del servicio. */
    private String description;
    /** Es el titulo del servicio. */
    private String titulo;
    /** Nos dice si ya terminó un servicio. */
    private Boolean finalizado;
    /** Es el conjunto de mensajes de un servicio. */
    private Set mensajes = new HashSet(0);
    /** El agente relacionado a este servicio. */
    private Set agentes = new HashSet(0);
    /** El registro de mensajes del servicio. */
    private Set registros = new HashSet(0);
    /** El programador asociado al servicio. */
    private Set programadors = new HashSet(0);
    
    /**
     * Constructor por defecto.
     */
    public Servicio() {
    }
    
    /**
     * Constructor
     * @param idServicio - Es el id del servicio.
     */
    public Servicio(int idServicio) {
        this.idServicio = idServicio;
    }
    
    /**
     * Es el constructor del servicio.
     * @param idServicio - Es el id del servicio.
     * @param presupuesto - Es el presupuesto.
     * @param description - Es la descripción.
     * @param titulo - Es el título.
     * @param finalizado - True si ya finalizó.
     * @param mensajes - Son los mensajes del servicio.
     * @param agentes - Son los agentes del servicio.
     * @param registros - Es el registro de mensajes del servicio.
     * @param programadors  - Es el programador del servicio.
     */
    public Servicio(int idServicio, Double presupuesto, String description,
            String titulo, Boolean finalizado, Set mensajes, Set agentes,
            Set registros, Set programadors) {
        this.idServicio = idServicio;
        this.presupuesto = presupuesto;
        this.description = description;
        this.titulo = titulo;
        this.finalizado = finalizado;
        this.mensajes = mensajes;
        this.agentes = agentes;
        this.registros = registros;
        this.programadors = programadors;
    }
    
    /**
     * Nos regresa el id del servicio.
     * @return El id del servicio.
     */
    @Id
    @Column(name = "id_servicio", unique = true, nullable = false)
    public int getIdServicio() {
        return this.idServicio;
    }
    
    /**
     * Asigna un id a un servicio.
     * @param idServicio - El nuevo id del servicio.
     */
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }
    
    /**
     * Nos regresa el presupuesto del servicio.
     * @return El presupuesto del servicio.
     */
    @Column(name = "presupuesto", precision = 17, scale = 17)
    public Double getPresupuesto() {
        return this.presupuesto;
    }
    
    /**
     * Asigna un presupuesto a un servicio.
     * @param presupuesto - El nuevo presupuesto del servicio.
     */
    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }
    
    /**
     * Nos regresa la descripción del servicio.
     * @return La descripción del servicio.
     */
    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Asigna una descripción a un servicio.
     * @param description - La nueva descripción del servicio.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Nos regresa el título del servicio.
     * @return El título del servicio.
     */
    @Column(name = "titulo")
    public String getTitulo() {
        return this.titulo;
    }
    
    /**
     * Asigna un título a un servicio.
     * @param titulo - El nuevo título del servicio.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Nos regresa si ya terminó el servicio.
     * @return True si ya acabó el servicio.
     */
    @Column(name = "finalizado")
    public Boolean getFinalizado() {
        return this.finalizado;
    }
    
    /**
     * Asigna si ya acabó o no un servicio.
     * @param finalizado - El nuevo estado del servicio.
     */
    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }
    
    /**
     * Nos regresa los mensajes del servicio.
     * @return Los mensjaes del servicio.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicio")
    public Set getMensajes() {
        return this.mensajes;
    }
    
    /**
     * Asigna los mensajes a un servicio.
     * @param mensajes - Los mensajes de un servicio.
     */
    public void setMensajes(Set mensajes) {
        this.mensajes = mensajes;
    }
    
    /** Regresa un set que contiene al único Agente que solicitó el servicio
     * @return El agente del servicio.
     */
    public Set getAgentes() {
        return this.agentes;
    }
    
    /** Establece al Agente que solicitó el servicio
     * @param agentes - El agente en un conjunto.
     */
    public void setAgentes(Set agentes) {
        this.agentes = agentes;
    }
    
    /**
     * Nos regresa los registros de mensajes del servicio.
     * @return Los registros del servicio.
     */
    public Set getRegistros() {
        return this.registros;
    }
    
    /**
     * Asigna registros a un servicio.
     * @param registros - Los nuevos registros.
     */
    public void setRegistros(Set registros) {
        this.registros = registros;
    }
    
    /** Regresa un set que contiene al único Programador que realizará el servicio.
     * @return A el conjunto que tiene al programador.
     */
    public Set getProgramadors() {
        return this.programadors;
    }
    
    /** Establece al Programador que realizará el servicio
     * @param programadors - Es el conjunto con el programador.
     */
    public void setProgramadors(Set programadors) {
        this.programadors = programadors;
    }
    
    /** Nos dice si dos servicios son iguales
     * @param o - El objeto a comparar.
     * @return true si es igual.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Servicio temp = (Servicio) o;
        if (temp.getIdServicio() != this.getIdServicio()) {
            return false;
        }
        if (!temp.getTitulo().equals(this.getTitulo())) {
            return false;
        }
        if (!this.getAgentes().equals(temp.getAgentes())) {
            return false;
        }
        return true;
    }   
} //Fin de Servicio.java