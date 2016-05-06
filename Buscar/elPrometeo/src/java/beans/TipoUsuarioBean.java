/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import modelo.TipoUsuario;

/**
 *
 * @author ricardo_rodab
 */
@ManagedBean
public class TipoUsuarioBean {    
    
    public TipoUsuario getAgente(){
        return TipoUsuario.AGENTE;
    }
    
    public TipoUsuario getProgramador(){
        return TipoUsuario.PROGRAMADOR;
    }
    
}
