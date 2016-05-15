/* -------------------------------------------------------------------
* OperacionesDAO.java
* versión 1.0
* Copyright (C) 2016  Kan-Balam.
* Facultad de Ciencias,
* Universidad Nacional Autónoma de México, Mexico.
*
* Este programa es software libre; se puede redistribuir
* y/o modificar en los términos establecidos por la
* Licencia Pública General de GNU tal como fue publicada
* por la Free Software Foundation en la versión 2 o
* superior.
*
* Este programa es distribuido con la esperanza de que
* resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
* sin la garantía implícita de COMERCIALIZACIÓN o
* ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
* Licencia Pública General de GNU para mayores detalles.
*
* Con este programa se debe haber recibido una copia de la
* Licencia Pública General de GNU, de no ser así, visite el
* siguiente URL:
* http://www.gnu.org/licenses/gpl.html
* o escriba a la Free Software Foundation Inc.,
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
* -------------------------------------------------------------------
*/
package dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import modelo.Agente;
import modelo.Mensaje;
import modelo.Programador;
import modelo.Registro;
import modelo.Servicio;
import modelo.TipoUsuario;
import modelo.Usuario;
import org.hibernate.SQLQuery;
import org.hibernate.TransactionException;
import org.hibernate.type.StandardBasicTypes;
import util.HibernateUtil;

/**
 *
 * @author ricardo
 */
public class OperacionesDAO {
    
    private Session session;
    
    private synchronized Session session() {
        if (HibernateUtil.getSessionFactory().isClosed()) {
            session = HibernateUtil.getSessionFactory().openSession();
        } else {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        return session;
    }
    
    private synchronized void closeSession(){
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    public Usuario buscaUsuario(int id) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from usuario where id_Usuario = :id")
                    .addEntity(Usuario.class)
                    .setInteger("id", id);
            return (Usuario) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    public Usuario buscaUsuarioPorTelefono(Long telefono) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from usuario where telefono = :telefono")
                    .addEntity(Usuario.class)
                    .setLong("telefono", telefono);
            return (Usuario) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    public Usuario buscaUsuarioPorCorreo(String correo) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from usuario where correo = :correo")
                    .addEntity(Usuario.class)
                    .setString("correo", correo);
            return (Usuario) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    /* Regresa la lista de bloqueados de un Usuario. Aún no sé para qué, pero
    seguro resultará útil */
    public List<Integer> obtenListaDeBloqueados(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select id_bloqueado from bloqueados where "
                    + "id_bloqueador = :id").addScalar("id_bloqueado",StandardBasicTypes.INTEGER);
            q.setInteger("id", u.getIdUsuario());
            /* La lista de usuarios bloqueados */
            List<Integer> lista = q.list();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    private void guardaProgramador(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            Programador p = new Programador(u);
            session().save(u);
            session().save(p);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
    private void guardaAgente(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            Agente a = new Agente(u);
            session().save(u);
            session().save(a);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
    public void guarda(Usuario u, TipoUsuario tipo) {
        if (tipo == TipoUsuario.AGENTE) {
            guardaAgente(u);
        } else if (tipo == TipoUsuario.PROGRAMADOR) {
            guardaProgramador(u);
        }
    }
    
    /* Guarda el mensaje en la base de datos */
    public void guardaMensaje(Mensaje m) {
        Transaction tx = session().beginTransaction();
        try {
            session().save(m);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
    public void guardaUsuario(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            session().save(u);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
    public boolean actualizaUsuario(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            session().update(u);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return false;
        } finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return true;
    }
    
    public Usuario verificarDatos(Usuario usuario) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from usuario where correo = :correo and contrasenia = :contraseña")
                    .addEntity(Usuario.class)
                    .setString("correo", usuario.getCorreo())
                    .setString("contraseña", usuario.getContrasenia());
            Usuario u = (Usuario) q.uniqueResult();
            if(!tx.wasCommitted())
                tx.commit();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    public boolean elimina(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            if (u.esAgente()) {
                Agente a = u.getAgente();
                Iterator ser = a.getServicios().iterator();
                while (ser.hasNext()) {
                    Servicio s = (Servicio)ser.next();
                    session().delete(s);
                }
                a.getServicios().clear();
                session().update(a);
                session().delete(u);
            } else {
                Programador p = u.getProgramador();
                p.getServicios().clear();
                session().update(p);
                session().delete(p);
                session().delete(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return true;
    }
    
    public void actualizaServicio(Usuario u, Servicio s) {
        Transaction tx = session().beginTransaction();
        try {
            session().update(u);
            session.update(u.getAgente());
            session().save(s);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return;
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
    /* Regresa los mensajes relacionados con el servicio s */
    public List<Mensaje> obtenMensajesServicio(Servicio s) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from mensaje where id_servicio ="
                    + s.getIdServicio()
                    + " order by fecha_de_envio desc").addEntity(Mensaje.class);
            List<Mensaje> lista = q.list();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    public List<Servicio> obtenServicios() {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio").addEntity(Servicio.class);
            List<Servicio> lista = q.list();
            if(!tx.wasCommitted())
                tx.commit();
            return lista;
        }
        catch (TransactionException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    public void eliminarServicio(Servicio servicio) {
        Transaction tx = session().beginTransaction();
        try {
            for (Object o : servicio.getAgentes()) {
                Agente agente = (Agente) o;
                agente.getServicios().remove(servicio);
                //session().merge(agente);
                session().update(agente);
            }
            for (Object o : servicio.getProgramadors()) {
                Programador programador = (Programador) o;
                programador.getServicios().remove(servicio);
                session().merge(programador);
            }
            for (Object o : servicio.getRegistros()) {
                Registro registro = (Registro) o;
                registro.setServicio(null);
                registro = (Registro) session().merge(registro);
                session().delete(registro);
            }
            servicio.getAgentes().clear();
            servicio.getProgramadors().clear();
            servicio.getRegistros().clear();
            //servicio = (Servicio) session().merge(servicio);
            session().update(servicio);
            session().delete(servicio);
            if(!tx.wasCommitted())
                tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
    public Servicio buscaServicioPorId(int id) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio where id_servicio = :id")
                    .addEntity(Servicio.class)
                    .setInteger("id", id);
            Servicio servicio = (Servicio) q.uniqueResult();
            if(!tx.wasCommitted())
                tx.commit();
            return servicio;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    public String finalizaServicio(Programador p, Servicio ser) {
        Transaction tx = session().beginTransaction();
        try {
            ser.getProgramadors().add(p);
            ser.setFinalizado(true);
            p.getServicios().add(ser);
            session().update(ser);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return "error";
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return "mostrarServicio";
    }
    
    public Agente buscaAgente(Agente a) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from agente where id_Agente = :id")
                    .addEntity(Agente.class)
                    .setInteger("id", a.getIdAgente());
            return (Agente) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    public Programador buscaProgramador(Programador programador) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from programador where id_Programador = :id")
                    .addEntity(Programador.class)
                    .setInteger("id", programador.getIdProgramador());
            return (Programador) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
        return null;
    }
    
    public List<Servicio> obtenServicios(String cond) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio as srv where lower(srv.titulo) LIKE :titulo or lower(srv.description) LIKE :description")
                    .addEntity(Servicio.class).setString("titulo", "%"+cond.toLowerCase()+"%").setString("description", "%"+cond.toLowerCase()+"%");
            List<Servicio> lista = q.list();
            if(!tx.wasCommitted())
                tx.commit();
            return lista;
        }catch (Exception e) {
            tx.commit();
            e.printStackTrace();
            tx.rollback();
            return null;
        }finally{
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
    public String bloquear(Usuario usuario, Usuario u) {
        Usuario pet,dang;
        pet = buscaUsuario(usuario.getIdUsuario());
        dang = buscaUsuario(u.getIdUsuario());
        Transaction tx = session().beginTransaction();
        try {
            Query sql = session().createSQLQuery("INSERT INTO bloqueados"
                    + "(id_bloqueado,id_bloqueador) VALUES("+dang.getIdUsuario()+","+pet.getIdUsuario()+")");
            sql.executeUpdate();
            return "servicio";
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return "error";
        } finally {
            if(!tx.wasCommitted())
                tx.commit();
            closeSession();
        }
    }
    
}
