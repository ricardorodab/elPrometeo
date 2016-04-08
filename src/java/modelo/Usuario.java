package modelo;
// Generated 08-abr-2016 1:26:04 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private int idUsuario;
     private String nombre;
     private String apellidoPaterno;
     private String apellidoMaterno;
     private Long telefono;
     private String correo;
     private String contrasenia;
     private String estado;
     private Date fechaDeNaciminiento;
     private String genero;
     private Programador programador;
     private Agente agente;

    public Usuario() {
    }

	
    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasenia) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }
    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, Long telefono, String correo, String contrasenia, String estado, Date fechaDeNaciminiento, String genero, Programador programador, Agente agente) {
       this.idUsuario = idUsuario;
       this.nombre = nombre;
       this.apellidoPaterno = apellidoPaterno;
       this.apellidoMaterno = apellidoMaterno;
       this.telefono = telefono;
       this.correo = correo;
       this.contrasenia = contrasenia;
       this.estado = estado;
       this.fechaDeNaciminiento = fechaDeNaciminiento;
       this.genero = genero;
       this.programador = programador;
       this.agente = agente;
    }
   
    public int getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }
    
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    public Long getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getContrasenia() {
        return this.contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaDeNaciminiento() {
        return this.fechaDeNaciminiento;
    }
    
    public void setFechaDeNaciminiento(Date fechaDeNaciminiento) {
        this.fechaDeNaciminiento = fechaDeNaciminiento;
    }
    public String getGenero() {
        return this.genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public Programador getProgramador() {
        return this.programador;
    }
    
    public void setProgramador(Programador programador) {
        this.programador = programador;
    }
    public Agente getAgente() {
        return this.agente;
    }
    
    public void setAgente(Agente agente) {
        this.agente = agente;
    }




}


