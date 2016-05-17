/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
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
 *
 * @author ricardo_rodab
 */
@RequestScoped
@ManagedBean
public class ServicioBean {
    
    private Servicio servicio = new Servicio();
    private String mensajeNuevo = "";
    private final OperacionesDAO dao;
    
    public ServicioBean() {
        dao = new OperacionesDAO();
    }
    
    public Servicio getServicio() {
        return this.servicio;
    }
    
    public String getMensajeNuevo(){
        return this.mensajeNuevo;
    }
    
    public void setMensajeNuevo(String mensajeNuevo){
        this.mensajeNuevo = mensajeNuevo;
    }
    
    public String setProgramador(Usuario usuario) {
        return setProgramador(usuario, this.servicio);
    }
    
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
    
    public boolean esElAgente(Usuario usuario) {
        return esElAgente(usuario, this.servicio);
    }
    
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
    
    public String mostrar(int servicio) {
        this.servicio = this.buscar(servicio);
        return this.servicio == null ? "error" : "mostrarServicio";
    }
    
    
    
    public Usuario mostrarProgramador(Servicio ser){
        Servicio ser2 = buscar(ser.getIdServicio());
        Programador ag = getProgramador(ser2);
        return ag.getUsuario();
    }
    
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
    
    public String eliminar(Servicio servicio2) {
        dao.eliminarServicio(servicio2);
        return "servicio";
    }
    
    public List<Servicio> getServicios() {
        return dao.obtenServicios();
    }
    
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
    
    public Agente getAgente(Servicio ser) {
        Servicio ser2 = dao.buscaServicioPorId(ser.getIdServicio());
        if (ser2 == null) {
            return null;
        }
        Agente a = (Agente) ser2.getAgentes().iterator().next();
        return dao.buscaAgente(a);
    }
    
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
    
    public Servicio buscar(int id) {
        Servicio servicio2 = dao.buscaServicioPorId(id);
        if (servicio2 == null) {
            return null;
        } else {
            return servicio2;
        }
    }
    
    /* Envía un mensaje del remitente al destinatario */
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
                m.setUsuarioByIdRemitente(pactual.getUsuario());
                m.setUsuarioByIdDestinatario(agactual.getUsuario());
            } else if (idprog == id_destinatario && idagente == id_remitente) {
                m.setUsuarioByIdRemitente(agactual.getUsuario());
                m.setUsuarioByIdDestinatario(pactual.getUsuario());
            } else {
                return "Servicio inválido";
            }
            dao.guardaMensaje(m);
            return mostrarMensajes(ser);
        }
    }
    
    /* Regresa una lista con los mensajes del servicio, ordenados por su
    Timestamp */
    public List<Mensaje> muestraMensajes() {
        /* Lista con todos los mensajes relacionados al servicio, ordenados por
        su Timestamp */
        List<Mensaje> mensajesDelServicio;
        mensajesDelServicio = dao.obtenMensajesServicio(this.servicio);
        return mensajesDelServicio;
    }
    
    /* Regresa una lista con los últimos n mensajes del servicio, ordenados por
    Timestamp */
    public List muestraUltimosNMensajes(int n) {
        /* La lista a regresar */
        List<Mensaje> regreso = this.muestraMensajes();
        /* El tamaño de la lista a regresar */
        int tamano = regreso.size();
        return regreso.subList(tamano - n, tamano);
    }
    
    public String mostrarMensajes(Servicio ser){
        this.servicio = dao.buscaServicioPorId(ser.getIdServicio());
        if(servicio == null)
            return "error";
        return "mensajes";
    }
    
}
