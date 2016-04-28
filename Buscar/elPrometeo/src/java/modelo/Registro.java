package modelo;
// Generated 08-abr-2016 1:26:04 by Hibernate Tools 4.3.1



/**
 * Registro generated by hbm2java
 */
public class Registro  implements java.io.Serializable {


     private int idMensaje;
     private Mensaje mensaje;
     private Servicio servicio;
     private Integer envio;
     private Integer recibio;

    public Registro() {
    }

	
    public Registro(Mensaje mensaje, Servicio servicio) {
        this.mensaje = mensaje;
        this.servicio = servicio;
    }
    public Registro(Mensaje mensaje, Servicio servicio, Integer envio, Integer recibio) {
       this.mensaje = mensaje;
       this.servicio = servicio;
       this.envio = envio;
       this.recibio = recibio;
    }
   
    public int getIdMensaje() {
        return this.idMensaje;
    }
    
    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }
    public Mensaje getMensaje() {
        return this.mensaje;
    }
    
    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    public Servicio getServicio() {
        return this.servicio;
    }
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    public Integer getEnvio() {
        return this.envio;
    }
    
    public void setEnvio(Integer envio) {
        this.envio = envio;
    }
    public Integer getRecibio() {
        return this.recibio;
    }
    
    public void setRecibio(Integer recibio) {
        this.recibio = recibio;
    }




}

