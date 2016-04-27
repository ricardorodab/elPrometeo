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
    private final Session sesion;
 
    public Busqueda(){
        sesion = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public String obtenerPalabra(String cadena){
        if(cadena.length() > 0){
            cadena = cadena.toLowerCase();
            String [] palabras = cadena.split(" ");
            return palabras[0];
        }
        return null;
    }
    
    public List<Servicio> buscar_s(String cadena){
        List<Servicio> resultado;
        Transaction tx = sesion.beginTransaction();
        Query q = sesion.getNamedQuery("Buscar").setString("cadena", cadena);
        resultado = (List<Servicio>) q.list();
        return resultado;
    }
} 
    