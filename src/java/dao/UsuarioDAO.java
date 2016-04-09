/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.hibernate.Query;
import org.hibernate.Session;

import modelo.Usuario;
import util.HibernateUtil;

/**
 *
 * @author ricardo
 */
public class UsuarioDAO {
    
    private Session session;
    
    public Usuario verificarDatos(Usuario usuario) throws Exception {
        
        Usuario su = null;
        
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM Usuario WHERE nombre = '" + usuario.getNombre() + "' AND contrasenia = '" + usuario.getContrasenia() + "'";
            Query query = session.createQuery(sql);
            
            if(!query.list().isEmpty()) {
                
                su = (Usuario)query.list().get(0);
                
            }
            
        } catch (Exception e) {
            
            throw e;
            
        }
        
        return su;
        
    }
    
}
