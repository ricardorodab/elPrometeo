/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import modelo.Servicio;

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
    
    public void crear(Servicio servicio){
        
    }
    
    public void eliminar(Servicio servicio){
        
    }
    
    public Servicio buscar(int id){
        return null;
    }
    
}
