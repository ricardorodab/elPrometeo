/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import busqueda.Busqueda;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import modelo.Servicio;

@Dependent
@Named(value = "buscarBean")
@ManagedBean
@ApplicationScoped
public class BuscarBean {

    private Busqueda busqueda;
    private String cadena;
    private ArrayList<Servicio> resultados;
   
    private final HttpServletRequest httpServletRequest; 
    private final FacesContext faceContext; 
    private FacesMessage message; 
  
   
    public BuscarBean(){
       
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();

    }
    
    public String buscar_s(){
       busqueda = new Busqueda();
       this.resultados = new ArrayList<>();
       this.resultados = (ArrayList<Servicio>) busqueda.buscar_s(cadena);        
       return "Buscar";
    }
    
    public void setBusqueda(Busqueda busqueda){
        this.busqueda = busqueda;
    }
    
    public Busqueda getBusqueda(){
        return busqueda;
    }

    public void setResultados(ArrayList<Servicio> resultados){
        this.resultados = resultados;
    }
    
    public ArrayList<Servicio> getResultados() {
        return resultados;
    }
    
    public void setCadena(String cadena){
        this.cadena = cadena;
    }
    
    public String getCadena(){
        return cadena;
    }              
      
}  
