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
import modelo.Servicio;

public class BuscarBean {

    private Busqueda search;
    private String cadena;
    private ArrayList<Servicio> resultados_s;
   
    private final HttpServletRequest httpServletRequest; 
    private final FacesContext faceContext; 
    private FacesMessage message; 
  
   
    public BuscarBean(){
       
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();

        
    }
    
    public String buscar_s(){
       search = new Busqueda();
       this.resultados_s = new ArrayList<>();
       this.resultados_s = (ArrayList<Servicio>) search.buscar_s(cadena);        
       return "Buscar";
    }
    
    public Busqueda getSearch(){
        return search;
    }
       
    public void setSearch(Busqueda s){
        this.search = s;
    }

    public ArrayList<Servicio> getResultados() {
        return this.resultados_s;
    }
     
    public String getCadena_s(){
        return cadena;
    }  
        
    public void setCadena_s(ArrayList<Servicio> serv){
        this.resultados_s = serv;
    }
      
}  
