/* -------------------------------------------------------------------
* ServicioBean.java
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

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dao.OperacionesDAO;
import java.util.Date;
import java.util.Iterator;
import modelo.Agente;
import modelo.Mensaje;
import modelo.Programador;
import modelo.Servicio;
import modelo.Usuario;

/**
 * @author Kan-Balam
 * @version 1.0
 * @since Apr 10 2016.
 * <p>
 * Clase para poder manejar a los servicios.</p>
 *
 * <p>
 * Clase controladora en particular de los servicios.</p>
 */
@RequestScoped
@ManagedBean
public class ServicioBean {
    /** Es el objeto que representa al servicio. */
    private Servicio servicio = new Servicio();
    /** Es el objeto que representa un nuevo mensaje del servicio. */
    private String mensajeNuevo = "";
    /** Es el objeto para acceder a la base de datos. */
    private final OperacionesDAO dao;
    
    /**
     * Constructor que inicializa las operaciones en la base de datos.
     */
    public ServicioBean() {
        dao = new OperacionesDAO();
    }
    
    /**
     * Metodo que nos regresa el servicio actual.
     * @return El servicio que estamos manejando actualmente.
     */
    public Servicio getServicio() {
        return this.servicio;
    }
    
    /**
     * Nos regresa el texto del nuevo mensaje.
     * @return El ultimo mensaje enviado del servicio.
     */
    public String getMensajeNuevo(){
        return this.mensajeNuevo;
    }
    
    /**
     * Asigna un nuevo mensaje al objeto.
     * @param mensajeNuevo - Es el nuevo mensaje que se enviara.
     */
    public void setMensajeNuevo(String mensajeNuevo){
        this.mensajeNuevo = mensajeNuevo;
    }
    
    /**
     * Metodo que asigna un programador al servicio.
     * @param usuario - Es el usuario programador del servicio.
     * @return El perfil del servicio.
     */
    public String setProgramador(Usuario usuario) {
        return setProgramador(usuario, this.servicio);
    }
    
    /**
     * Metodo que asigna un programador al servicio.
     * @param usuario - Es el usuario programador del servicio.
     * @param ser - Es el servicio que se le asigna el programador.
     * @return El perfil del servicio.
     */
    public String setProgramador(Usuario usuario, Servicio ser) {
        /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Usuario u = dao.buscaUsuarioPorCorreo(usuario.getCorreo());
        if (u == null) {
            return "error";
        } else {
            if (u.esAgente()) {
                return "error";
            }
            Programador p = usuario.getProgramador();
            String result = dao.finalizaServicio(p,ser);
            if(result.equalsIgnoreCase("mostrarServicio")){
                return mostrar(ser.getIdServicio());
            }else{
                return "error";
            }
        }
    }
    
    /**
     * Metodo que asigna un agente al servicio.
     * @param usuario - Es el usuario agente del servicio.
     * @return El perfil del servicio.
     */
    public boolean esElAgente(Usuario usuario) {
        return esElAgente(usuario, this.servicio);
    }
    
    /**
     * Metodo para saber si un usuario es el programador.
     * @param usuario - Es el usuario a verificar.
     * @param ser - Es el servicio del cual preguntamos.
     * @return true si el usuario que pasamos es el programador del servicio.
     */
    public boolean esElProgramador(Usuario usuario, Servicio ser) {
        
        /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Usuario u = dao.buscaUsuarioPorCorreo(usuario.getCorreo());
        if (u == null) {
            return false;
        } else if (!ser.getFinalizado()) {
            return false;
        } else {
            if (u.esAgente()) {
                return false;
            }
            Programador dueno = this.getProgramador(ser); // new ArrayList<Agente>(this.servicio.getAgentes()).get(0);
            return dueno.getIdProgramador() == u.getProgramador().getIdProgramador();
        }
    }
    
    /**
     * Metodo para saber si un usuario es el agente.
     * @param usuario - Es el usuario a verificar.
     * @param ser - Es el servicio del cual preguntamos.
     * @return true si el usuario que pasamos es el agente del servicio.
     */
    public boolean esElAgente(Usuario usuario, Servicio ser) {
        
        /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Usuario u = dao.buscaUsuario(usuario.getIdUsuario());
        if (u == null) {
            return false;
        } else {
            if (!u.esAgente()) {
                return false;
            }
            Agente dueno = this.getAgente(ser); // new ArrayList<Agente>(this.servicio.getAgentes()).get(0);
            return dueno.getIdAgente() == u.getAgente().getIdAgente();
        }
    }
    
    /**
     * Con este metodo mostramos un servicio.
     * @param servicio - Es el id del servicio.
     * @return Regresamos la cadena que mostramos el servicio.
     */
    public String mostrar(int servicio) {
        this.servicio = this.buscar(servicio);
        return this.servicio == null ? "error" : "mostrarServicio";
    }
    
    /**
     * Muestra el programador del un servicio.
     * @param ser - Es el servicio del cual buscamos.
     * @return El usuario programador del servicio.
     */
    public Usuario mostrarProgramador(Servicio ser){
        Servicio ser2 = buscar(ser.getIdServicio());
        Programador ag = getProgramador(ser2);
        return ag.getUsuario();
    }
    
    /**
     * Muestra el agente de un servicio.
     * @param ser - Es el servicio del cual buscamos.
     * @return El usuario agente del servicio.
     */
    public Usuario mostrarAgente(Servicio ser) {
        Servicio ser2 = buscar(ser.getIdServicio());
        Agente ag = getAgente(ser2);
        return ag.getUsuario();
    }
    
    /**
     * Método para crear un nuevo servicio.
     *
     * @param usuario - Es el usuario actual que lo crea.
     * @return Error en caso de que no se pudo crear, servicio en caso
     * contrario.
     */
    public String crear(Usuario usuario) {
        Usuario user = dao.buscaUsuarioPorCorreo(usuario.getCorreo());
        if (user == null) {
            return "error";
        } else {
            if (!user.esAgente()) {
                return "error";
            }
            servicio.setFinalizado(false);
            servicio.getAgentes().add(user.getAgente());
            Agente agente = user.getAgente();
            user.getAgente().getServicios().add(this.servicio);
            dao.actualizaServicio(user, servicio);
            return "servicio";
        }
    }
    
    /**
     * Metodo para eliminar un servicio se la base datos.
     * @param servicio2 - El servicio a eliminar.
     * @return La cadena que redirige a los servicios.
     */
    public String eliminar(Servicio servicio2) {
        dao.eliminarServicio(servicio2);
        return "servicio";
    }
    
    /**
     * Metodo para obtener una lista de los servicios.
     * @return Una lista con todos los servicios.
     */
    public List<Servicio> getServicios() {
        return dao.obtenServicios();
    }
    
    /**
     * Metodo que nos dice si un usuario esta bloqueado por un servicio.
     * @param usuarioActual - Es usuario a verificar.
     * @param ser - El servicio que comparte.
     * @return true si el usuario está bloqueado.
     */
    public boolean usuarioBloqueado(Usuario usuarioActual, Servicio ser) {
        List<Integer> list = dao.obtenListaDeBloqueados(usuarioActual);
        if(usuarioActual.esAgente()){
            Programador ag = getProgramador(buscar(ser.getIdServicio()));
            Usuario us = dao.buscaUsuario(ag.getIdProgramador());
            Iterator i = list.iterator();
            while(i.hasNext()){
                if((Integer)i.next() == us.getIdUsuario())
                    return true;
            }
            return false;
        }else{
            Agente ag = getAgente(buscar(ser.getIdServicio()));
            Usuario us = dao.buscaUsuario(ag.getIdAgente());
            Iterator i = list.iterator();
            while(i.hasNext()){
                if((Integer)i.next() == us.getIdUsuario())
                    return true;
            }
            list = dao.obtenListaDeBloqueados(us);
            i = list.iterator();
            while(i.hasNext()){
                if((Integer)i.next() == usuarioActual.getIdUsuario())
                    return true;
            }
            return false;
        }
    }
    
    /**
     * Metodo que nos regresa los datos de un programador de un servicio.
     * @param usuarioActual - El usuario a obtener los datos.
     * @param ser - El servicio a verificar.
     * @return Una cadena con los datos.
     */
    public String getDatosProgramador(Usuario usuarioActual, Servicio ser) {
        List<Integer> list = dao.obtenListaDeBloqueados(usuarioActual);
        Programador ag = getProgramador(buscar(ser.getIdServicio()));
        Usuario us = dao.buscaUsuario(ag.getIdProgramador());
        Iterator i = list.iterator();
        while(i.hasNext()){
            if((Integer)i.next() == us.getIdUsuario())
                return "El usuario ha sido bloqueado! Por seguridad sus datos ya no podrán ser vistos";
        }
        return "Nombre del programador: " + us.getNombre() + " " + us.getApellidoPaterno() + "\n"
                + "Correo del programador: " + us.getCorreo() + "\n"
                + "Teléfono: " + us.getTelefono() + "\n";
    }
    
    /**
     * Metodo que nos regresa los datos de un agente de un servicio.
     * @param usuarioActual - El usuario a obtener los datos.
     * @param ser - El servicio a verificar.
     * @return Una cadena con los datos.
     */
    public String getDatosAgente(Usuario usuarioActual, Servicio ser) {
        List<Integer> list = dao.obtenListaDeBloqueados(usuarioActual);
        Agente ag = getAgente(buscar(ser.getIdServicio()));
        Usuario us = dao.buscaUsuario(ag.getIdAgente());
        Iterator i = list.iterator();
        while(i.hasNext()){
            if((Integer)i.next() == us.getIdUsuario())
                return "El usuario ha sido bloqueado! Por seguridad sus datos ya no podrán ser vistos";
        }
        list = dao.obtenListaDeBloqueados(us);
        i = list.iterator();
        while(i.hasNext()){
            if((Integer)i.next() == usuarioActual.getIdUsuario())
                return "El usuario te ha bloqueado! Por seguridad sus datos ya no podrán ser vistos";
        }
        return "Nombre del agente: " + us.getNombre() + " " + us.getApellidoPaterno() + "\n"
                + "Correo del agente: " + us.getCorreo() + "\n"
                + "Teléfono: " + us.getTelefono() + "\n";
    }
    
    /**
     * Metodo que nos regresa un agente de un servicio.
     * @param ser - Es el servicio del cual buscamos al agente.
     * @return Un objeto de tipo agente.
     */
    public Agente getAgente(Servicio ser) {
        Servicio ser2 = dao.buscaServicioPorId(ser.getIdServicio());
        if (ser2 == null) {
            return null;
        }
        Agente a = (Agente) ser2.getAgentes().iterator().next();
        return dao.buscaAgente(a);
    }
    
    /**
     * Metodo que nos regresa un programador de un servicio.
     * @param ser - Es el servicio del cual buscamos al programador.
     * @return Un objeto de tipo programador.
     */
    public Programador getProgramador(Servicio ser) {
        Servicio ser2 = dao.buscaServicioPorId(ser.getIdServicio());
        if (ser2 == null) {
            return null;
        }
        if (!ser.getFinalizado()) {
            return null;
        }
        Programador p = (Programador)ser2.getProgramadors().iterator().next();
        return dao.buscaProgramador(p);
    }
    
    /**
     * Metodo para buscar un servicio.
     * @param id - Es el id del servicio.
     * @return Un objeto de tipo servicio.
     */
    public Servicio buscar(int id) {
        Servicio servicio2 = dao.buscaServicioPorId(id);
        if (servicio2 == null) {
            return null;
        } else {
            return servicio2;
        }
    }
    
    /**
     * Envía un mensaje del remitente al destinatario
     * @param id_remitente - Es el id del usuario que envía.
     * @param id_destinatario - Es el id del usuario que recibe.
     * @param mensaje - Es el texto del mensaje.
     * @param ser - Es el servicio del cual se envía.
     * @return El estado del envío del mensaje.
     */
    public String enviarMensaje(int id_remitente, int id_destinatario,
            String mensaje,Servicio ser) {
        Servicio actual = dao.buscaServicioPorId(ser.getIdServicio());
        if(actual == null)
            return "Servicio inválido";
        /* El servicio actual */
        Programador pactual = this.getProgramador(actual);
        /* El programador
        del servicio actual */
        Agente agactual = this.getAgente(actual);
        /* El agente que solicitó el servicio actual */
        if (pactual == null || agactual == null) {
            return "Servicio inválido";
        } else {
            int idprog = pactual.getIdProgramador();
            /* El id del programador
            actual */
            int idagente = agactual.getIdAgente();
            /* El id del agente actual */
            Mensaje m = new Mensaje();
            /* La instancia del mensaje a enviar */
            m.setServicio(actual);
            m.setTexto(mensaje);
            m.setFechaDeEnvio(new Date());
            if (idprog == id_remitente && idagente == id_destinatario) {
                m.setTexto("Programador: "+m.getTexto());
                m.setUsuarioByIdRemitente(pactual.getUsuario());
                m.setUsuarioByIdDestinatario(agactual.getUsuario());
            } else if (idprog == id_destinatario && idagente == id_remitente) {
                m.setTexto("Agente: "+m.getTexto());
                m.setUsuarioByIdRemitente(agactual.getUsuario());
                m.setUsuarioByIdDestinatario(pactual.getUsuario());
            } else {
                return "Servicio inválido";
            }
            dao.guardaMensaje(m);
            return mostrarMensajes(ser);
        }
    }
    
    /**
     * Regresa una lista con los mensajes del servicio, ordenados por su Timestamp
     * @return Una lista con los mensajes del servicio.
     */
    public List<Mensaje> muestraMensajes() {
        /* Lista con todos los mensajes relacionados al servicio, ordenados por
        su Timestamp */
        List<Mensaje> mensajesDelServicio;
        mensajesDelServicio = dao.obtenMensajesServicio(this.servicio);
        return mensajesDelServicio;
    }
    
    /**
     * Regresa una lista con los últimos n mensajes del servicio, ordenados por Timestamp
     * @param n - Es el último mensaje.
     * @return Una lista con los mensajes.
     */
    public List muestraUltimosNMensajes(int n) {
        /* La lista a regresar */
        List<Mensaje> regreso = this.muestraMensajes();
        /* El tamaño de la lista a regresar */
        int tamano = regreso.size();
        return regreso.subList(tamano - n, tamano);
    }
    
    /**
     * Metodo que nos regresa mensajes.
     * @param ser - Es el servicio del cual bucamos.
     * @return mensajes para redirigir a la vista mensajes.
     */
    public String mostrarMensajes(Servicio ser){
        this.servicio = dao.buscaServicioPorId(ser.getIdServicio());
        if(servicio == null)
            return "error";
        return "mensajes";
    }
} //Fin de ServicioBean.java