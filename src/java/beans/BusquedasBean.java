/*
 * Copyright (C) 2016 ricardo_rodab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package beans;

import dao.OperacionesDAO;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Servicio;

/**
 *
 * @author ricardo_rodab
 */
@RequestScoped
@ManagedBean
public class BusquedasBean {
    
   private String busqueda = "";
   private Servicio servicio = new Servicio();
   private final OperacionesDAO dao;
   
    public BusquedasBean() {
        dao = new OperacionesDAO();
    }

    public Servicio getServicio() {
        return this.servicio;
    }
    
    public void setServicio(Servicio servicio){
        this.servicio = servicio;
    }
    
    public String getBusqueda() {
        return this.busqueda;
    }
    
    public void setBusqueda(String busqueda){
        this.busqueda = busqueda;
    }        
    
    public List<Servicio> getServicios() {
        return dao.obtenServicios(this.busqueda);
    }
    
    public List<Servicio> getServicios(String busqueda){
        return dao.obtenServicios(busqueda);
    }
    
    
    /*public String mostrarServicioBuscado(Servicio ser){
        this.servicio = this.buscar(ser.getIdServicio());
        return this.servicio == null ? "error" : "mostrarServicioBuscado"; 
    }
    
     public Servicio buscar(int id) {
        Servicio servicio2 = dao.buscaServicioPorId(id);
        if (servicio2 == null) {
            return null;
        } else {
            return servicio2;
        }
    }*/
    
}
