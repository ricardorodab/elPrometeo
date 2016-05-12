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

    private final OperacionesDAO dao;

    public ServicioBean() {
        dao = new OperacionesDAO();
    }

    public Servicio getServicio() {
        return this.servicio;
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
//<<<<<<< HEAD
            String result = dao.finalizaServicio(p,ser);
            if(result.equalsIgnoreCase("mostrarServicio")){
                return mostrar(ser.getIdServicio());
            }else{
                return "error";
            }
/*=======
            return dao.finalizaServicio(p, ser);
>>>>>>> 9040699feb9942f29691c61c7b9ae8661ede1b6a */
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
            Programador dueno = (Programador) ser.getProgramadors().iterator().next(); // new ArrayList<Agente>(this.servicio.getAgentes()).get(0);
            return dueno.getIdProgramador() == usuario.getProgramador().getIdProgramador();
        }
    }

    public boolean esElAgente(Usuario usuario, Servicio ser) {

        /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Usuario u = dao.buscaUsuarioPorCorreo(usuario.getCorreo());
        if (u == null) {
            return false;
        } else {
            if (!u.esAgente()) {
                return false;
            }
            Agente dueno = (Agente) ser.getAgentes().iterator().next(); // new ArrayList<Agente>(this.servicio.getAgentes()).get(0);
            return dueno.getIdAgente() == usuario.getAgente().getIdAgente();
        }
    }

    public String mostrar(int servicio) {
        this.servicio = this.buscar(servicio);
        return this.servicio == null ? "error" : "mostrarServicio";
    }
    
    
        
    public Usuario mostrarProgramador(Servicio ser){
        System.out.println(ser.getIdServicio());    
        System.out.println("111");
        System.out.println("111");
        System.out.println(this.servicio.getIdServicio());
        Servicio ser2 = buscar(ser.getIdServicio());                
        Programador ag = getProgramador(ser2);
        return ag.getUsuario();
    }

    public String mostrarAgente(Servicio ser) {
        return "perfil";
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

    public String getDatosProgramador(Servicio ser) {
        Programador ag = getProgramador(buscar(ser.getIdServicio()));
        Usuario us = dao.buscaUsuario(ag.getIdProgramador());
        return "Nombre del programador: " + us.getNombre() + " " + us.getApellidoPaterno() + "\n"
                + "Correo del programador: " + us.getCorreo() + "\n"
                + "Teléfono: " + us.getTelefono() + "\n";
    }

    public String getDatosAgente(Servicio ser) {
        Agente ag = getAgente(buscar(ser.getIdServicio()));
        Usuario us = dao.buscaUsuario(ag.getIdAgente());
        return "Nombre del agente: " + us.getNombre() + " " + us.getApellidoPaterno() + "\n"
                + "Correo del agente: " + us.getCorreo() + "\n"
                + "Teléfono: " + us.getTelefono() + "\n";
    }

    public Agente getAgente(Servicio ser) {
        if (ser == null) {
            return null;
        }
        return (Agente) ser.getAgentes().iterator().next();
    }

    public Programador getProgramador(Servicio ser) {
        if (ser == null) {
            return null;
        }
        if (!ser.getFinalizado()) {
            return null;
        }
        Programador p = (Programador)ser.getProgramadors().iterator().next();
        return dao.buscaProgramador(p);        
    }

    public Servicio buscar(int id) {
        Servicio servicio = dao.buscaServicioPorId(id);
        if (servicio == null) {
            return null;
        } else {
            return servicio;
        }
    }

    /* Envía un mensaje del remitente al destinatario */
    public String enviarMensaje(int id_remitente, int id_destinatario,
            String mensaje) {
        Servicio actual = this.servicio;
        /* El servicio actual */
        Programador pactual = this.getProgramador(actual);
        /* El programador 
            del servicio actual */
        Agente agactual = this.getAgente(actual);
        /* El agente que solicitó el servicio actual */
        if (actual == null || pactual == null || agactual == null) {
            return "Servicio inválido";
        } else {
            int idprog = pactual.getIdProgramador();
            /* El id del programador 
            actual */
            int idagente = agactual.getIdAgente();
            /* El id del agente actual */
            Mensaje m = new Mensaje();
            /* La instancia del mensaje a enviar */
            m.setServicio(this.servicio);
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
            return "Mensaje enviado";
        }
    }

    /* Regresa una lista con los mensajes del servicio, ordenados por su 
    Timestamp */
    public List muestraMensajes() {
        /* Lista con todos los mensajes relacionados al servicio, ordenados por 
        su Timestamp */
        List<Mensaje> mensajes_del_servicio;
        mensajes_del_servicio = dao.obtenMensajesServicio(this.servicio);
        return mensajes_del_servicio;
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

}
