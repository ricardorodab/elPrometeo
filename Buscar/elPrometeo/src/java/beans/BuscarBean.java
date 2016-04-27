/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import busqueda.Busqueda;
import modelo.Servicio;

/**
 *
 * @author claudia
 */

@ManagedBean
@RequestScoped
public class BuscarBean {
    private List<Servicio> servicios;
    private Servicio servicio;
    private String cadena;

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void buscarServicio(){
        Busqueda busq = new Busqueda();
        this.setServicios(busq.buscar_s(cadena));
        if(servicios.isEmpty()){
            System.out.println("vacío");
        }
    }

    /**
     * Creates a new instance of BuscarBean
     */
    public BuscarBean() {
        System.out.println("Iniciando Buscar bean");
    }

}
