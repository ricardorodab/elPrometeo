/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import modelo.Agente;
import modelo.Servicio;
import modelo.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author ricardo_rodab
 */
@ManagedBean
public class ServicioBean {
    
    private Servicio servicio = new Servicio();
    
    public Servicio getServicio(){
        return this.servicio;
    }
    
    public boolean esElAgente(Usuario usuario){
        /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Session session;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = null;
        }
        if(session == null)
            session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Usuario WHERE correo = '" + usuario.getCorreo()
                + "'";
        Query query = session.createQuery(sql);
        // Si no existe el usuario.
        if (query.list().isEmpty()) {
            return false;
        } else {
            Usuario usuarioNew = (Usuario)query.uniqueResult();
            if(!usuarioNew.esAgente()){
                return false;
            }
            try {
                //Creamos el servicio nuevo.
                Transaction tx = session.beginTransaction();
                Agente dueno = new ArrayList<Agente>(this.servicio.getAgentes()).get(0);
                session.flush();
                session.clear();
                tx.commit();
                session.close();
                if(dueno.getIdAgente() == usuario.getAgente().getIdAgente()){
                    return true;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }
    
    public String mostrar(int servicio){
        this.servicio = this.buscar(servicio);
        if(this.servicio == null)
            return "error";
        return "mostrarServicio";
    }
    
    /**
     * Método para crear un nuevo servicio.
     * @param usuarioBean - Es el usuario actual que lo crea.
     * @return Error en caso de que no se pudo crear, servicio en caso contrario.
     */
    public String crear(Usuario usuario){
        /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Usuario WHERE correo = '" + usuario.getCorreo()
                + "'";
        Query query = session.createQuery(sql);
        // Si no existe el usuario.
        if (query.list().isEmpty()) {
            return "error";
        } else {
            Usuario usuarioNew = (Usuario)query.uniqueResult();
            if(!usuarioNew.esAgente())
                return "error";
            try {
                //Creamos el servicio nuevo.
                Transaction tx = session.beginTransaction();
                this.servicio.setFinalizado(false);
                this.servicio.getAgentes().add(usuarioNew.getAgente());
                usuarioNew.getAgente().getServicios().add(this.servicio);
                session.update(usuarioNew);
                session.save(this.servicio);
                /* La transacción actual. Se utiliza para que persistan los
                cambios que realicemos en la base */
                session.flush();
                session.clear();
                tx.commit();
                session.close();
                return "servicio";
            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }
    
    public String eliminar(int id){
        /* Primero verificamos que el usuario no esté registrado */
        Session session;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = null;
        }
        if(session == null)
            session = HibernateUtil.getSessionFactory().openSession();
        try {
            //AQUÍ ME REGRESA QUE SON 0 CUANDO DEBERÍAN SER n > 0
            System.out.println(this.servicio.getIdServicio());
            System.out.println(id);
            /* La transacción actual. Se utiliza para que persistan los
            cambios que realicemos en la base */
            Transaction tx = session.beginTransaction();
            /* Subrutina de eliminación sacada de codejava.net */
            /* Se convierte el id de usuario a serializable para utilizar el método load */
            /*
            Código temporal (agregar ON CASCADE)
            */
            //this.servicio = this.buscar(this.servicio.getIdServicio());
            String sql = "FROM Servicio WHERE idServicio = '" + id
                    + "'";
            Query query = session.createQuery(sql);
            tx.commit();
            // Si no existe el servicio.
            if (query.list().isEmpty()) {
                /*if(session.isOpen())
                session.close();*/
                return "error";
            } else {
                Servicio servicioNew = (Servicio)query.list().get(0);
                System.out.println("0");
                servicioNew.getAgentes().clear();
                System.out.println("1");
                servicioNew.getProgramadors().clear();
                System.out.println("2");
                servicioNew.getRegistros().clear();
                System.out.println("3");
                session.delete(servicioNew);
                System.out.println("4");
                System.out.println("Lo eliminó");
                /* Se busca la instancia del usuario para eliminarla de la base *
                Object persistentInstance = session.get(Usuario.class, id);
                if (persistentInstance != null) {
                session.delete(persistentInstance);
                }
                /* No sé qué tanto de lo que sigue se necesite, pero igual lo
                dejaré */
                session.flush();
                session.clear();
                tx.commit();
                //session.close();
                return "servicio";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public List<Servicio> getServicios(){
        Session session;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = null;
        }
        if(session == null)
            session = HibernateUtil.getSessionFactory().openSession();
        List<Servicio> lista = session.createCriteria(Servicio.class).list();
        session.close();
        return lista;
    }
    
    public Servicio buscar(int id){
        /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Session session;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = null;
        }
        if(session == null)
            session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Servicio WHERE idServicio = '" + id
                + "'";
        session.flush();
        session.clear();
        Query query = session.createQuery(sql);
        tx.commit();
        // Si no existe el servicio.
        if (query.list().isEmpty()) {
            if(session.isOpen())
                session.close();
            return null;
        } else {
            Servicio servicioNew = (Servicio)query.list().get(0);
            //Si no hago esto, no se quedan los Sets en el objeto.
            servicioNew.getAgentes().isEmpty();
            servicioNew.getRegistros().isEmpty();
            servicioNew.getProgramadors().isEmpty();
            if(session.isOpen())
                session.close();
            return servicioNew;
        }
    }
    
}
