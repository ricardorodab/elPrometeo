/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author ricardo
 */
public class HibernateUtil {
    
    private static final SessionFactory sessionFactory;
    
    static {
        
        try {
            
           sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            
        } catch (Throwable e) {
            
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
            
        }
        
    }
    
    public static SessionFactory getSessionFactory() {
     
        return sessionFactory;
        
    }
    
    
}
