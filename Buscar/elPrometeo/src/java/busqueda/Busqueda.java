/* To chasnge this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import java.util.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import modelo.Servicio;

public class Busqueda {
    
 private Session sesion;
 private ArrayList <Servicio> resultados_s;
 
    public Busqueda(){
        sesion = HibernateUtil.getSessionFactory().getCurrentSession();
}

    public static String obtenerPalabras(String cadena){
        if(cadena.length()>0){
        cadena = cadena.toLowerCase(); //Transforma la cadena a minúsculas.
        String [] palabras  = cadena.split(" ");
        String resultado = ".*(";
            for(String p: palabras){
                resultado+= p+"|";
            
            }
            if(resultado.length() >= 5){
                resultado =    resultado.substring(0,resultado.length()-1);
            }
        
            resultado += ").*";
            return resultado;
        }
        return cadena;
    }
    
    public List<Servicio> buscar_s(String cadena ){
         
         cadena = obtenerPalabras(cadena);
         sesion = HibernateUtil.getSessionFactory().getCurrentSession();
         List<Servicio> r = new ArrayList<>();
          try{
            Transaction t = sesion.beginTransaction();
             Query q = sesion.createSQLQuery("SELECT presupuesto, description, programadors FROM servicio where "
            + "LOWER(servicio.description) ~ :cadena ; ").addEntity(Servicio.class).setString("cadena",  cadena );

             ArrayList<Servicio> resultados = (ArrayList<Servicio>) q.list();
            sesion.getTransaction().commit();                       
        }catch (Exception e) {
            sesion.getTransaction().rollback();
        }
         return resultados_s;
         
    }
           
}

