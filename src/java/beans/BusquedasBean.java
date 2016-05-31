/* -------------------------------------------------------------------
* BusquedasBean.java
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
package beans;

import dao.OperacionesDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Servicio;

/**
 * @author Kan-Balam
 * @version 1.0
 * @since Apr 10 2016.
 * <p>
 * Clase para poder manejar a las busquedas.</p>
 *
 * <p>
 * Clase controladora en particular de las busquedas de los servicios.</p>
 */
@RequestScoped
@ManagedBean
public class BusquedasBean {
    
    /** Es el query que se hace. */
    private String busqueda = "";
    /** Es el servicio que se buscara. */
    private Servicio servicio = new Servicio();
    /** Es el objeto que nos da acceso a la base de datos. */
    private final OperacionesDAO dao;
    
    /**
     * Metodo constructor por default inicializa el acceso a la base de datos.
     */
    public BusquedasBean() {
        dao = new OperacionesDAO();
    }
    
    /**
     * Metodo que nos da el servicio actual.
     * @return Un servicio que nos ayuda a accesar a las busquedas.
     */
    public Servicio getServicio() {
        return this.servicio;
    }
    
    /**
     * Metodo para asignar un nuevo servicio al objeto.
     * @param servicio - Es el servicio que se le asignara.
     */
    public void setServicio(Servicio servicio){
        this.servicio = servicio;
    }
    
    /**
     * Ayuda a obtener el query que estamos buscando.
     * @return Un objeto tipo String que es el query.
     */
    public String getBusqueda() {
        return this.busqueda;
    }
    
    /**
     * Metodo para asignar un nuevo query al objeto.
     * @param busqueda - Es la cadena que representa al query que buscamos.
     */
    public void setBusqueda(String busqueda){
        this.busqueda = busqueda;
    }
    
    /**
     * Metodo que nos da los resultados de una búsqueda.
     * @return Una lista con los servicios que coinciden con la búsqueda.
     */
    public List<Servicio> getServicios() {
        return dao.obtenServicios(this.busqueda);
    }
    
    /**
     * Metodo que nos da los resultados de una búsqueda.
     * @param busqueda - Es el nuevo query a buscar.
     * @return una lista con los servicios que coinciden con la búsqueda.
     */
    public List<Servicio> getServicios(String busqueda){
        return dao.obtenServicios(busqueda);
    }
} //Fin de BusquedasBean.java