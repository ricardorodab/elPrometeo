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
 * Clase para modelar a los.</p>
 *
 * <p>
 * Clase que modela a los.</p>
 */
@Entity
@Table(name = "servicio", schema = "public"
)
public class Servicio implements java.io.Serializable {

    private int idServicio;
    private Double presupuesto;
    private String description;
    private String titulo;
    private Boolean finalizado;
    private Set mensajes = new HashSet(0);
    /* El agente relacionado a este 
    servicio */
    private Set agentes = new HashSet(0);
    private Set registros = new HashSet(0);
    private Set programadors = new HashSet(0);

    /* El programador relacionado al 
    servicio */
    public Servicio() {
    }

    public Servicio(int idServicio) {
        this.idServicio = idServicio;
    }

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

    @Id

    @Column(name = "id_servicio", unique = true, nullable = false)
    public int getIdServicio() {
        return this.idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    @Column(name = "presupuesto", precision = 17, scale = 17)
    public Double getPresupuesto() {
        return this.presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "titulo")
    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "finalizado")
    public Boolean getFinalizado() {
        return this.finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicio")
    public Set getMensajes() {
        return this.mensajes;
    }

    public void setMensajes(Set mensajes) {
        this.mensajes = mensajes;
    }

    /* Regresa un set que contiene al único Agente que solicitó el servicio */
    public Set getAgentes() {
        return this.agentes;
    }

    /* Establece al Agente que solicitó el servicio */
    public void setAgentes(Set agentes) {
        this.agentes = agentes;
    }

    public Set getRegistros() {
        return this.registros;
    }

    public void setRegistros(Set registros) {
        this.registros = registros;
    }

    /* Regresa un set que contiene al único Programador que realizará el 
     servicio */
    public Set getProgramadors() {
        return this.programadors;
    }

    /* Establece al Programador que realizará el servicio */
    public void setProgramadors(Set programadors) {
        this.programadors = programadors;
    }

    /* Nos dice si dos servicios son iguales */
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

}
