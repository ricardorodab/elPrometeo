/* -------------------------------------------------------------------
* Especialidad.java
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
 * Clase para modelar a las especialidades.</p>
 *
 * <p>
 * Clase que modela a las especialidades.</p>
 */
public class Especialidad  implements java.io.Serializable {
    
    /** El id de la especialidad. */
     private int idEspecialidad;
     /** El texto que representa la especialidad. */
     private String especialidad;
     /** Los programadores con esa especialidad. */
     private Set programadors = new HashSet(0);

     /**
      * Metodo constructor basico.
      */
    public Especialidad() {
    }

    /**
     * Metodo constructor.
     * @param idEspecialidad - Es el id de la especialidad. 
     */
    public Especialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    
    /**
     * Metodo constructor.
     * @param idEspecialidad - Es el id de la especialidad.
     * @param especialidad - Es el nombre de la especialidad.
     * @param programadors - Son los programadores de la especialidad.
     */
    public Especialidad(int idEspecialidad, String especialidad, Set programadors) {
       this.idEspecialidad = idEspecialidad;
       this.especialidad = especialidad;
       this.programadors = programadors;
    }
   
    /**
     * Nos regresa el id de la especialidad.
     * @return El id de una especialidad.
     */
    public int getIdEspecialidad() {
        return this.idEspecialidad;
    }
    
    /**
     * Metodo para asignar un nuevo id a una especialidad.
     * @param idEspecialidad - El nuevo id de la especialidad.
     */    
    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    
    /**
     * Metodo para obtener el nombre de la especialidad.
     * @return El nombre de la especialidad.
     */
    public String getEspecialidad() {
        return this.especialidad;
    }
    
    /**
     * Metodo para asignar un nombre a una especialidad.
     * @param especialidad - Es el nuevo nombre.
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    /**
     * Metodo para obtener los programadores.
     * @return Los programadores.
     */
    public Set getProgramadors() {
        return this.programadors;
    }
    
    /**
     * Metodo para asignar programadores a una especilidad.
     * @param programadors - Son los programadores. 
     */
    public void setProgramadors(Set programadors) {
        this.programadors = programadors;
    }
} //Fin de Especialidad.java