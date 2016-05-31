/* -------------------------------------------------------------------
* TipoUsuarioBean.java
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

import javax.faces.bean.ManagedBean;
import modelo.TipoUsuario;

/**
 * @author Kan-Balam
 * @version 1.0
 * @since Apr 10 2016.
 * <p>
 * Clase para poder manejar a los dos tipos de usuarios. </p>
 *
 * <p>
 * Clase que detipe dos tipos de usuarios.</p>
 */
@ManagedBean
public class TipoUsuarioBean {
    
    /**
     * Metodo que nos regresa el primer tipo de usuario.
     * @return un objeto de tipo Agente.
     */
    public TipoUsuario getAgente(){
        return TipoUsuario.AGENTE;
    }
    
    /**
     * Metodo que nos regresa el segundo tipo de usuario.
     * @return un objeto de tipo Programador.
     */
    public TipoUsuario getProgramador(){
        return TipoUsuario.PROGRAMADOR;
    }
    
}
