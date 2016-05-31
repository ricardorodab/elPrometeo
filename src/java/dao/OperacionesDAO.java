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
import org.hibernate.TransactionException;
import org.hibernate.type.StandardBasicTypes;
import util.HibernateUtil;

/**
 * @author Kan-Balam
 * @version 1.0
 * @since Mar 09 2016.
 * <p>
 * Clase para poder manejar el acceso a la base de datos.</p>
 *
 * <p>
 * Clase controladora en particular de la base de datos.</p>
 */
public class OperacionesDAO {
    
    /** Es la sesión que accede a la base. */
    private Session session;
    
    /**
     * Metodo que nos regresa una session.
     * @return Una nueva session o una que estemos usando.
     */
    private synchronized Session session() {
        if (HibernateUtil.getSessionFactory().isClosed()) {
            session = HibernateUtil.getSessionFactory().openSession();
        } else {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        return session;
    }
    
    /**
     * Metodo que cierra una sessión.
     */
    private synchronized void closeSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    /**
     * Metodo para buscar un usuario.
     * @param id - Es el id del usuario.
     * @return Un usuario de la base de datos o null si no existe.
     */
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
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Nos da un usuario dado un telefono.
     * @param telefono - Es el telefono del usuario.
     * @return Un usuario que coincida con ese teléfono o null si no existe.
     */
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
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Nos da un usuario dado un correo.
     * @param correo - Es el correo del usuario.
     * @return Un usuario que coincida con el correo o null.
     */
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
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Regresa la lista de bloqueados de un Usuario. Aún no sé para qué, pero
     * seguro resultará útil
     * @param u - Es el usuario que desea obtener la lista de bloqueados.
     * @return Una lista con los id de los bloqueados.
     */
    public List<Integer> obtenListaDeBloqueados(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select id_bloqueado from bloqueados where "
                    + "id_bloqueador = :id").addScalar("id_bloqueado", StandardBasicTypes.INTEGER);
            q.setInteger("id", u.getIdUsuario());
            /* La lista de usuarios bloqueados */
            List<Integer> lista = q.list();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    // Metodo privado que guarda a un programador.
    private void guardaProgramador(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            Programador p = new Programador(u);
            session().save(u);
            session().save(p);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    // Metodo privado que guarda a un agente.
    private void guardaAgente(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            Agente a = new Agente(u);
            session().save(u);
            session().save(a);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * Metodo para guardar a un usuario en la base.
     * @param u - El usuario a guardar.
     * @param tipo - El tipo de usuario.
     */
    public void guarda(Usuario u, TipoUsuario tipo) {
        if (tipo == TipoUsuario.AGENTE) {
            guardaAgente(u);
        } else if (tipo == TipoUsuario.PROGRAMADOR) {
            guardaProgramador(u);
        }
    }
    
    /**
     * Guarda el mensaje en la base de datos.
     * @param m - Es el mensaje a guardar.
     */
    public void guardaMensaje(Mensaje m) {
        Transaction tx = session().beginTransaction();
        try {
            session().save(m);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * Guarda a un usuario en la base de datos.
     * @param u - El usuario a guardar.
     */
    public void guardaUsuario(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            session().save(u);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * Metodo para actualizar a un usuario.
     * @param u - El usuario que se desea actualizar.
     * @return  true si se pudo actualizar.
     */
    public boolean actualizaUsuario(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            session().update(u);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return false;
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return true;
    }
    
    /**
     * Actualiza al agente.
     * @param a - Es el agente a actualizar.
     * @return true si se pudo actualizar al objeto.
     */
    public boolean actualizaAgente(Agente a) {
        Transaction tx = session().beginTransaction();
        try {
            session().update(a);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return false;
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return true;
    }
    
    /**
     * Actualiza a un programador.
     * @param p - Es el programador a actualizar.
     * @return true si se pudo actualizar al objeto.
     */
    public boolean actualizaProgramador(Programador p) {
        Transaction tx = session().beginTransaction();
        try {
            session().update(p);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return false;
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return true;
    }
    
    /**
     * Metodo para verificar los datos de un usuario.
     * @param usuario - Es el usuario a verificar.
     * @return El usuario que se desea encontrar o null si no existe.
     */
    public Usuario verificarDatos(Usuario usuario) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from usuario where correo = :correo and contrasenia = :contraseña")
                    .addEntity(Usuario.class)
                    .setString("correo", usuario.getCorreo())
                    .setString("contraseña", usuario.getContrasenia());
            Usuario u = (Usuario) q.uniqueResult();
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            return u;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Metodo para eliminar un usuario de la base de datos.
     * @param u - Es el usuario a eliminar.
     * @return true si se pudo eliminar.
     */
    public boolean elimina(Usuario u) {
        Transaction tx = session().beginTransaction();
        try {
            if (u.esAgente()) {
                Agente a = (Agente)u.getAgente();
                Iterator ser = a.getServicios().iterator();
                while (ser.hasNext()) {
                    Servicio s = (Servicio) ser.next();
                    Query q2 = session().createSQLQuery("select * from mensaje where id_servicio ="
                            + s.getIdServicio()
                            + " order by fecha_de_envio desc").addEntity(Mensaje.class);
                    List<Mensaje> lista = q2.list();
                    Iterator i2 =  lista.iterator();
                    Query sql3 = session().createSQLQuery("DELETE FROM registro "
                            + "WHERE id_servicio = :id_servicio").setInteger("id_servicio", s.getIdServicio());
                    sql3.executeUpdate();
                    while (i2.hasNext()) {
                        session().delete((Mensaje)i2.next());
                    }
                    s.getProgramadors().clear();
                    s.getAgentes().clear();
                    s.getRegistros().clear();
                    Query sql = session().createSQLQuery("DELETE FROM pide_servicio "
                            + "WHERE id_servicio = :id_servicio").setInteger("id_servicio", s.getIdServicio());
                    sql.executeUpdate();
                    Query sql2 = session().createSQLQuery("DELETE FROM presta_servicio "
                            + "WHERE id_servicio = :id_servicio").setInteger("id_servicio", s.getIdServicio());
                    sql2.executeUpdate();
                    session().update(s);
                    session().delete(s);
                }
                Query sql4 = session().createSQLQuery("DELETE FROM calificacion "
                        + "WHERE id_calificado = :id_usuario").setInteger("id_usuario", u.getIdUsuario());
                sql4.executeUpdate();
                Query sql5 = session().createSQLQuery("DELETE FROM calificacion "
                        + "WHERE id_calificador = :id_usuario").setInteger("id_usuario", u.getIdUsuario());
                sql5.executeUpdate();
                a.getServicios().clear();
                session().update(a);
                session().update(u);
                session().delete(a);
                session().delete(u);
                return true;
            } else {
                Programador p = u.getProgramador();
                Iterator ser = p.getServicios().iterator();
                while (ser.hasNext()) {
                    Servicio s = (Servicio) ser.next();
                    Query q2 = session().createSQLQuery("select * from mensaje where id_servicio ="
                            + s.getIdServicio()
                            + " order by fecha_de_envio desc").addEntity(Mensaje.class);
                    List<Mensaje> lista = q2.list();
                    Iterator i2 =  lista.iterator();
                    Query sql3 = session().createSQLQuery("DELETE FROM registro "
                            + "WHERE id_servicio = :id_servicio").setInteger("id_servicio", s.getIdServicio());
                    sql3.executeUpdate();
                    while (i2.hasNext()) {
                        session().delete((Mensaje)i2.next());
                    }
                    s.getProgramadors().clear();
                    s.getAgentes().clear();
                    s.getRegistros().clear();
                    Query sql = session().createSQLQuery("DELETE FROM pide_servicio "
                            + "WHERE id_servicio = :id_servicio").setInteger("id_servicio", s.getIdServicio());
                    sql.executeUpdate();
                    Query sql2 = session().createSQLQuery("DELETE FROM presta_servicio "
                            + "WHERE id_servicio = :id_servicio").setInteger("id_servicio", s.getIdServicio());
                    sql2.executeUpdate();
                    session().update(s);
                    session().delete(s);
                }
                Query sql4 = session().createSQLQuery("DELETE FROM calificacion "
                        + "WHERE id_calificado = :id_usuario").setInteger("id_usuario", u.getIdUsuario());
                sql4.executeUpdate();
                Query sql5 = session().createSQLQuery("DELETE FROM calificacion "
                        + "WHERE id_calificador = :id_usuario").setInteger("id_usuario", u.getIdUsuario());
                sql5.executeUpdate();
                p.getServicios().clear();
                session().update(p);
                session().update(u);
                session().delete(p);
                session().delete(u);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * Metodo para actualizar un servicio y con un usuario.
     * @param u - Es el usuario del servicio.
     * @param s - Es el servicio que se desea actualizar.
     */
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
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * Regresa los mensajes relacionados con el servicio.
     * @param s - El servicio relacionado a los mensajes.
     * @return Una lista con los mensajes.
     */
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
    
    /**
     * Regresa los servicios de la base de datos.
     * @return - Una lista con todos los servicios.
     */
    public List<Servicio> obtenServicios() {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio").addEntity(Servicio.class);
            List<Servicio> lista = q.list();
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            return lista;
        } catch (TransactionException e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Metodo para eliminar un servicio.
     * @param servicio - Es el servicio a ser eliminado.
     */
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
                session().update(programador);
            }
            for (Object o : servicio.getRegistros()) {
                Registro registro = (Registro) o;
                registro.setServicio(null);
                session().update(registro);
                session().delete(registro);
            }
            servicio.getAgentes().clear();
            servicio.getProgramadors().clear();
            servicio.getRegistros().clear();
            //servicio = (Servicio) session().merge(servicio);
            session().update(servicio);
            session().delete(servicio);
            if (!tx.wasCommitted()) {
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * Metodo para buscar un servicio por id.
     * @param id - El id primario del servicio.
     * @return El servicio que buscamos o null si no existe.
     */
    public Servicio buscaServicioPorId(int id) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio where id_servicio = :id")
                    .addEntity(Servicio.class)
                    .setInteger("id", id);
            Servicio servicio = (Servicio) q.uniqueResult();
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            return servicio;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Metodo para finalizar un servicio.
     * @param p - Es el programador que finaliza el servicio.
     * @param ser - Es el servicio que finalizamos.
     * @return mostrarServicio si se pudo finalizar con éxito.
     */
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
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return "mostrarServicio";
    }
    
    /**
     * Metodo para buscar un agente en la base de datos.
     * @param a - Es el agente a buscar.
     * @return El agente de la base o null si no existe.
     */
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
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Metodo para buscar un programador en la base de datos.
     * @param programador - Es el programador a buscar.
     * @return Es el programador de la base o null si no existe.
     */
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
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return null;
    }
    
    /**
     * Metodo para obtener una lista de servicios con una condición.
     * @param cond - La condición que buscamos.
     * @return Una lista con los servicios que cumplen las condiciones.
     */
    public List<Servicio> obtenServicios(String cond) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select * from servicio as srv where lower(srv.titulo) LIKE :titulo or lower(srv.description) LIKE :description")
                    .addEntity(Servicio.class).setString("titulo", "%" + cond.toLowerCase() + "%").setString("description", "%" + cond.toLowerCase() + "%");
            List<Servicio> lista = q.list();
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            return lista;
        } catch (Exception e) {
            tx.commit();
            e.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * Metodo para bloquear un usuario.
     * @param usuario - El usuario que desea bloquear.
     * @param u - El usuario que se desea bloquear.
     * @return servicio si se bloqueo con exito.
     */
    public String bloquear(Usuario usuario, Usuario u) {
        Usuario pet, dang;
        pet = buscaUsuario(usuario.getIdUsuario());
        dang = buscaUsuario(u.getIdUsuario());
        Transaction tx = session().beginTransaction();
        try {
            Query sql = session().createSQLQuery("INSERT INTO bloqueados"
                    + "(id_bloqueado,id_bloqueador) VALUES(" + dang.getIdUsuario() + "," + pet.getIdUsuario() + ")");
            sql.executeUpdate();
            return "servicio";
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return "error";
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
    }
    
    /**
     * El usuario calificador califica al calificado
     * (Se inserta en la base de datos)
     * @param calificador - El usuario que califica.
     * @param calificado - El usuario que es calificado.
     * @param calificacion - La calificación del usuario.
     * @return el perfil del usuario que se está calificando.
     */
    public String califica(Usuario calificador, Usuario calificado,
            double calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            return "error";
        }
        /* Lo usamos para saber si hay resultados */
        Object hayResultados = null;
        Transaction tx = session().beginTransaction();
        /* Lista de calificaciones */
        List<Double> lista;
        try {
            /*Aquí se ve si hay calificación */
            Query busca = session().createSQLQuery("select * from "
                    + "calificacion where id_calificado = :id and id_calificador"
                    + " = :id2")
                    .setInteger("id", calificado.getIdUsuario())
                    .setInteger("id2", calificador.getIdUsuario());
            hayResultados = busca.uniqueResult();
            if (hayResultados != null) {
                
                /* Si hay calificación, se borra esta */
                Query borra = session().createSQLQuery("DELETE FROM "
                        + "calificacion WHERE id_calificado = :id and "
                        + "id_calificador = :id2")
                        .setInteger("id", calificado.getIdUsuario())
                        .setInteger("id2", calificador.getIdUsuario());
                borra.executeUpdate();
                
            }
            
            /* Se inserta */
            Query inserta = session().createSQLQuery("INSERT INTO calificacion"
                    + "(id_calificador ,id_calificado, calificacion) VALUES("
                    + calificador.getIdUsuario() + ","
                    + calificado.getIdUsuario() + ","
                    + Double.toString(calificacion) + ")");
            inserta.executeUpdate();
            
            /* Se va a promediar */
            Query q = session().createSQLQuery("select calificacion from "
                    + "calificacion where id_calificado = :id")
                    .addScalar("calificacion", StandardBasicTypes.DOUBLE)
                    .setInteger("id", calificado.getIdUsuario());
            /* La reputación nueva del Usuario */
            lista = q.list();
            
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
            return "error";
        } finally {
            closeSession();
        }
        /* Estúpido hibernate hace que mi código se vea feo y me obliga a
        actualizar instancias después de la transacción */
        if (calificado.esAgente()) {
            /* La instancia del usuario como agente */
            Agente c = calificado.getAgente();
            c.setReputacionAgente(promedioLista(lista));
            actualizaAgente(c);
        } else {
            /* La instancia del usuario como programador */
            Programador c = calificado.getProgramador();
            c.setReputacionProgramador(promedioLista(lista));
            actualizaProgramador(c);
        }
        
        return "perfilAjeno";
    }
    
    // Saca el promedio de una lista de dobles.
    private Double promedioLista(List<Double> lista) {
        Double promedio = 0.0;
        /* El promedio que vamos a regresar */
        for (Double d : lista) {
            promedio += d;
        }
        promedio /= lista.size();
        return promedio;
    }
    
    /**
     * Saca el promedio de la calificación del Agente y lo actualiza en la base
     * de datos
     * @param p - Es el agente a promediar su calificación.
     */
    public void promediaAgente(Agente p) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select avg(calificacion) from "
                    + "calificacion where id_calificado = :id")
                    .addScalar("calificacion", StandardBasicTypes.DOUBLE)
                    .setInteger("id", p.getUsuario().getIdUsuario());
            /* La reputación nueva del Agente */
            double promedio = (Double) q.uniqueResult();
            p.setReputacionAgente(promedio);
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        actualizaAgente(p);
    }
    
    /**
     * Saca el promedio de la calificación del Programador y lo actualiza en la base
     * de datos
     * @param p - Es el programador a promediar su calificación.
     */
    public void promediaProgramador(Programador p) {
        Transaction tx = session().beginTransaction();
        try {
            Query q = session().createSQLQuery("select avg(calificacion) from "
                    + "calificacion where id_calificado = :id")
                    .addScalar("calificacion", StandardBasicTypes.DOUBLE)
                    .setInteger("id", p.getUsuario().getIdUsuario());
            /* La reputación nueva del Programador */
            double promedio = (Double) q.uniqueResult();
            p.setReputacionProgramador(promedio);
            
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        actualizaProgramador(p);
    }
    
    /**
     * Nos dice si el calificador ya calificó al calificado.
     * @param calificador - Es que califica.
     * @param calificado - Es el que está siendo calificado.
     * @return true si ya lo calificó antes.
     */
    public boolean hayCalificacion(Usuario calificador, Usuario calificado) {
        Transaction tx = session().beginTransaction();
        /* Lo usamos para saber si hay resultados */
        Object hayResultados = null;
        try {
            Query q = session().createSQLQuery("select * from "
                    + "calificacion where id_calificado = :id and id_calificador"
                    + " = :id2")
                    .setInteger("id", calificado.getIdUsuario())
                    .setInteger("id2", calificador.getIdUsuario());
            hayResultados = q.uniqueResult();
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx.rollback();
        } finally {
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            closeSession();
        }
        return (hayResultados != null);
    }
    
    /**
     * Borra una calificación existente.
     * @param calificador - Es el usuario que califica.
     * @param calificado  - Es el usuario calificado.
     */
    public void borraCalificacion(Usuario calificador, Usuario calificado) {
        Transaction tx2 = session().beginTransaction();
        try {
            Query sql = session().createSQLQuery("DELETE FROM calificacion"
                    + "WHERE id_calificado = :id and id_calificador = :id2")
                    .setInteger("id", calificado.getIdUsuario())
                    .setInteger("id2", calificador.getIdUsuario());
            sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // Lo mantengo para revisar el log.
            tx2.rollback();
        } finally {
            if (!tx2.wasCommitted()) {
                tx2.commit();
            }
            closeSession();
        }
    }
}//Fin de OperacionesDAO.java