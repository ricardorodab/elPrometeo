/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import modelo.Servicio;
import modelo.Usuario;
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
    
    public void mostrar(Servicio servicio){
        
    }
    
    /**
     * Método para crear un nuevo servicio.
     * @param usuarioBean - Es el usuario actual que lo crea.
     * @return Error en caso de que no se pudo crear, servicio en caso contrario.
     */
    public String crear(UsuarioBean usuarioBean){
         /* Primero verificamos que el usuario esté registrado y sea un agente. */
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Usuario WHERE correo = '" + usuarioBean.getUsuario().getCorreo()
                + "'";
        Query query = session.createQuery(sql);
        // Si no existe el usuario.
        if (query.list().isEmpty()) {
            return "error";
        } else {
            Usuario usuario = (Usuario)query.uniqueResult();
            if(!usuario.esAgente())
                return "error";    
            try {
                //Creamos el servicio nuevo.
                Transaction tx = session.beginTransaction();
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
    
    public void eliminar(Servicio servicio){
        
    }
    
    public Servicio buscar(int id){
        return null;
    }
    
}
