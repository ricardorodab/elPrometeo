package modelo;
// Generated 08-abr-2016 1:26:04 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Servicio generated by hbm2java
 */
public class Servicio  implements java.io.Serializable {


     private int idServicio;
     private Double presupuesto;
     private String description;
     private String titulo;
     private Boolean finalizado;
     private Set agentes = new HashSet(0);
     private Set registros = new HashSet(0);
     private Set programadors = new HashSet(0);

    public Servicio() {
    }

	
    public Servicio(int idServicio) {
        this.idServicio = idServicio;
    }
    public Servicio(int idServicio, Double presupuesto, String description, String titulo, Boolean finalizado, Set agentes, Set registros, Set programadors) {
       this.idServicio = idServicio;
       this.presupuesto = presupuesto;
       this.description = description;
       this.titulo = titulo;
       this.finalizado = finalizado;
       this.agentes = agentes;
       this.registros = registros;
       this.programadors = programadors;
    }
   
    public int getIdServicio() {
        return this.idServicio;
    }
    
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }
    public Double getPresupuesto() {
        return this.presupuesto;
    }
    
    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Boolean getFinalizado() {
        return this.finalizado;
    }
    
    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }
    public Set getAgentes() {
        return this.agentes;
    }
    
    public void setAgentes(Set agentes) {
        this.agentes = agentes;
    }
    public Set getRegistros() {
        return this.registros;
    }
    
    public void setRegistros(Set registros) {
        this.registros = registros;
    }
    public Set getProgramadors() {
        return this.programadors;
    }
    
    public void setProgramadors(Set programadors) {
        this.programadors = programadors;
    }




}


