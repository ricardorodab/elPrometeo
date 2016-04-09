/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;

import dao.UsuarioDAO;
import modelo.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {
    
    private Usuario usuario = new Usuario();
    
    public Usuario getUsuario() {
        
        return usuario;
        
    }
    
    public void setUsuario(Usuario usuario) {
        
        this.usuario = usuario;
        
    }
    
    public String verificarDatos() throws Exception {
        
        UsuarioDAO suDAO = new UsuarioDAO();
        Usuario su;
        String resultado;
        
        try {
            
            su = suDAO.verificarDatos(this.usuario);
            if(su != null) {
                
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", su);
                resultado = "exito";
               
            } else {
                
                resultado = "error";
                
            }
        } catch (Exception e) {
            
            throw e;
            
        }
        
        return resultado;
        
    }
    
    public boolean verificarSesion() {
        
        boolean estado;
        
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") ==  null) {
            
            estado = false;
            
        } else {
            
            estado = true;
            
        }
        
        return estado;
        
    }
    
    public String cerrarSesion() {
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
        
    }
    
}
