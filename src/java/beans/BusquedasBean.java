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
    
    private List<Servicio> condicion;
    private String cond = new String("");
    private final OperacionesDAO dao;
    
    public BusquedasBean () {
        dao = new OperacionesDAO();
    }
    
    public void setCond(String cond){
        this.cond = cond;
    }
    
    public String getCond(){
        return this.cond;
    }
    
    public String getServicios(){
        //this.condicion = dao.obtenServicios(this.cond);
        return "busquedas";
    }
    
    public String getServicios(String cond){
        //this.condicion = dao.obtenServicios(cond);
        return "busquedas";
    }
    
}