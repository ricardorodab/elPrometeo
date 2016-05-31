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
 * Clase para modelar a los.</p>
 *
 * <p>
 * Clase que modela a los.</p>
 */
public class Registro  implements java.io.Serializable {


     private int idMensaje;
     private Mensaje mensaje;
     private Servicio servicio;
     private Integer envio;
     private Integer recibio;

    public Registro() {
    }

	
    public Registro(Mensaje mensaje, Servicio servicio) {
        this.mensaje = mensaje;
        this.servicio = servicio;
    }
    public Registro(Mensaje mensaje, Servicio servicio, Integer envio, Integer recibio) {
       this.mensaje = mensaje;
       this.servicio = servicio;
       this.envio = envio;
       this.recibio = recibio;
    }
   
    public int getIdMensaje() {
        return this.idMensaje;
    }
    
    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }
    public Mensaje getMensaje() {
        return this.mensaje;
    }
    
    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    public Servicio getServicio() {
        return this.servicio;
    }
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    public Integer getEnvio() {
        return this.envio;
    }
    
    public void setEnvio(Integer envio) {
        this.envio = envio;
    }
    public Integer getRecibio() {
        return this.recibio;
    }
    
    public void setRecibio(Integer recibio) {
        this.recibio = recibio;
    }




}


