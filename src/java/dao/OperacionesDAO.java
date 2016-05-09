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
import modelo.Programador;
import modelo.Registro;
import modelo.Servicio;
import modelo.TipoUsuario;
import modelo.Usuario;
import org.hibernate.TransactionException;
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
            tx.commit();
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
            tx.commit();
        }
        return null;
    }

    /* Nos dice si el bloqueador bloqueó al bloqueado */
    public boolean buscaBloqueado(Usuario bloqueador, Usuario bloqueado) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from bloqueados"
                    + " where id_bloqueado = " + bloqueado.getIdUsuario()
                    + " and id_bloqueador = " + bloqueador.getIdUsuario());
            if (q.uniqueResult() == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            tx.commit();
        }
        return false;
    }

    /* Regresa la lista de bloqueados de un Usuario. Aún no sé para qué, pero 
    seguro resultará útil */
    public List<Usuario> obtenListaDeBloqueados(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from bloqueados where "
                    + "id_bloqueador = "
                    + u.getIdUsuario()).addEntity(Usuario.class);
            /* La lista de usuarios bloqueados */
            List<Usuario> lista = q.list();
            tx.commit();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
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
        }
        tx.commit();
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
        }
        if (!tx.wasCommitted()) {
            tx.commit();
        }
    }

    public void guarda(Usuario u, TipoUsuario tipo) {
        if (tipo == TipoUsuario.AGENTE) {
            guardaAgente(u);
        } else if (tipo == TipoUsuario.PROGRAMADOR) {
            guardaProgramador(u);
        }
    }

    public void guardaUsuario(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            session().save(u);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        }
        tx.commit();
    }

    public boolean actualizaUsuario(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            session().update(u);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return false;
        }
        tx.commit();
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
            tx.commit();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
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
                    Servicio s = (Servicio) ser.next();
                    session().delete(s);
                }
                a.getServicios().clear();
                a = (Agente) session().merge(a);
                session().delete(a);
                session().delete(u);
            } else {
                Programador p = u.getProgramador();
                p.getServicios().clear();
                p = (Programador) session().merge(p);
                session().delete(p);
                session().delete(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        }
        tx.commit();
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
        }
        tx.commit();
    }

    public List<Servicio> obtenServicios() {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio").addEntity(Servicio.class);
            List<Servicio> lista = q.list();
            tx.commit();
            return lista;
        } catch (TransactionException e) {
            return obtenServicios();
        } catch (Exception e) {
            e.printStackTrace();
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
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public Servicio buscaServicioPorId(int id) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio where id_servicio = :id")
                    .addEntity(Servicio.class)
                    .setInteger("id", id);
            Servicio servicio = (Servicio) q.uniqueResult();
            tx.commit();
            return servicio;
        } catch (Exception e) {
            e.printStackTrace();
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
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return "error";
        }
        return "servicio";
    }

    public List<Servicio> obtenServicios(String cond) {
        //Transaction tx = session().beginTransaction();
        /*Transaction tx;
        if (session().getTransaction() != null
            && session().getTransaction().isActive()) {
            tx = session().getTransaction();
    } else {
            tx = session().beginTransaction();
        }*/
 /*try {
            Query q = session().createSQLQuery("select * from servicio where titulo like %:titulo% or description like %:description%")
                    .addEntity(Servicio.class).setString("titulo", cond).setString("description", cond);
            List<Servicio> lista = q.list();
            tx.commit();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

}
