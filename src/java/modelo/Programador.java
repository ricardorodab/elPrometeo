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


     private int idProgramador;
     private Usuario usuario;
     private String formacion;
     private Double reputacionProgramador;
     private Set especialidads = new HashSet(0);
     private Set servicios = new HashSet(0);

    public Programador() {
    }

	
    public Programador(Usuario usuario) {
        this.usuario = usuario;
    }
    public Programador(Usuario usuario, String formacion, Double reputacionProgramador, Set especialidads, Set servicios) {
       this.usuario = usuario;
       this.formacion = formacion;
       this.reputacionProgramador = reputacionProgramador;
       this.especialidads = especialidads;
       this.servicios = servicios;
    }
   
    public int getIdProgramador() {
        return this.idProgramador;
    }
    
    public void setIdProgramador(int idProgramador) {
        this.idProgramador = idProgramador;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getFormacion() {
        return this.formacion;
    }
    
    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }
    public Double getReputacionProgramador() {
        return this.reputacionProgramador;
    }
    
    public void setReputacionProgramador(Double reputacionProgramador) {
        this.reputacionProgramador = reputacionProgramador;
    }
    public Set getEspecialidads() {
        return this.especialidads;
    }
    
    public void setEspecialidads(Set especialidads) {
        this.especialidads = especialidads;
    }
    public Set getServicios() {
        return this.servicios;
    }
    
    public void setServicios(Set servicios) {
        this.servicios = servicios;
    }

    public boolean tieneFormacion(){
        return !(this.formacion == null || this.formacion.trim().equals(""));
    }


}


