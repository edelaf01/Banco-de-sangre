/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ule.edi.dao;

/**
 *
 * @author kkkk
 */
import java.sql.Connection;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ule.edi.model.Donantevalidar;
import ule.edi.model.Inventariohospital;
import ule.edi.model.Pedidos;
import ule.edi.model.Pedidoshechos;

import ule.edi.model.Stocksangrealmacen;

import ule.edi.model.User;
import ule.edi.util.DataConnect;

@Repository
public class AlmacenImpl {

    private static final Logger logger = LoggerFactory.getLogger(AlmacenImpl.class);
    private Session session;

    @Transactional
    public void borrarSangre(Stocksangrealmacen p) {
        // Connection con = null;
        // con = DataConnect.getConnection();
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            //  session = HibernateUtil.getSessionFactory().getCurrentSession();
            String hql = "delete from Stocksangrealmacen where id=:id";
            Query query = session.createQuery(hql);

            transaction = session.beginTransaction();

            query.setString("id", "" + p.getId());

            query.executeUpdate();
            if (!transaction.wasCommitted()) {
                transaction.commit();
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            //DataConnect.close(con)
            session.flush();
            session.clear();
            session.close();

        }

    }

    public String getNombreDestinatario(int id2) {
        String dev = "";
        Connection con = null;
        con = DataConnect.getConnection();
        List<User> listaSangre = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM User where id=:id ";

            session.beginTransaction();

            Query query = session.createQuery(hql);
            query.setString("id", "" + id2);
            if (!query.list().isEmpty()) {

                listaSangre = query.list();
                dev = listaSangre.get(0).getUsername();
                return dev;
            }

            session.flush();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
        return dev;
    }

    @Transactional
    public void enviarSangre(Inventariohospital s) {
        // Connection con = null;
        // con = DataConnect.getConnection();
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();

            transaction = session.beginTransaction();

            session.save(s);

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            //DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
    }

    public List<Pedidos> generarTablaPedidos() {
        //Devuelve una lista 
        Connection con = null;
        con = DataConnect.getConnection();
        List<Pedidos> listaSangre = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM Pedidos ";

            session.beginTransaction();

            Query query = session.createQuery(hql);

            if (!query.list().isEmpty()) {

                listaSangre = query.list();

                return listaSangre;
            }

            session.flush();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
        return listaSangre;
    }

    public List<Stocksangrealmacen> generarTablaAlmacen() {
        //Devuelve una lista 
        Connection con = null;
        con = DataConnect.getConnection();
        List<Stocksangrealmacen> listaSangre = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM Stocksangrealmacen ";

            session.beginTransaction();

            Query query = session.createQuery(hql);

            if (!query.list().isEmpty()) {

                listaSangre = query.list();

                return listaSangre;
            }

            session.flush();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
        return listaSangre;
    }

    public List<ule.edi.model.Pedidos> getPedido(ule.edi.model.Pedidos p) {
        //Devuelve una lista 
        Connection con = null;
        con = DataConnect.getConnection();
        List<ule.edi.model.Pedidos> listaSangre = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM Pedidos where id=:id ";
            System.out.println("Buscando pedido con el id: " + p.getId());
            Query query = session.createQuery(hql);

            query.setString("id", "" + p.getId());

            if (!query.list().isEmpty()) {

                listaSangre = query.list();
                System.out.println("Buscando pedido con el id: " + p.getId() + " Se ha encontrado resultados para el pedido dirigido al id " + listaSangre.get(0).getUsuarioDestino()
                        + " que pide " + listaSangre.get(0).getDosis() + " dosis de sangre");
                return listaSangre;
            }

            session.flush();

        } catch (Exception e) {
            System.out.println("Excepcion encontrada con el pedido con el id " + p.getId());
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
        System.out.println("Error no se ha encontrado el pedido con el id:" + p.getId());
        return listaSangre;
    }

    public void actualizarPedido(ule.edi.model.Pedidos p) {
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();
            int a = p.getDosis();
            a = a - 1;
            p.setDosis(a);
            transaction = session.beginTransaction();

            session.saveOrUpdate(p);

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            //DataConnect.close(con);
            session.close();
        }
    }

    public void enviarPedidoCompletado(Pedidoshechos p3) {
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();

            transaction = session.beginTransaction();
          
            session.update(p3);

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            //DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
    }

    public void borrarPedidoCompletado(ule.edi.model.Pedidos p2) {
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            //  session = HibernateUtil.getSessionFactory().getCurrentSession();
            String hql = "delete from Pedidos where id=:id";
            Query query = session.createQuery(hql);

            transaction = session.beginTransaction();

            query.setString("id", "" + p2.getId());

            query.executeUpdate();
            if (!transaction.wasCommitted()) {
                transaction.commit();
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            //DataConnect.close(con)
            session.flush();
            session.clear();
            session.close();

        }
    }

    public boolean existePedidoCompletado(Pedidoshechos p3) {
        Connection con = null;
        con = DataConnect.getConnection();

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM Pedidoshechos where id=:id ";
            System.out.println("Buscando pedido con el id: " + p3.getId());
            Query query = session.createQuery(hql);

            query.setString("id", "" + p3.getId());

            if (!query.list().isEmpty()) {

                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            // System.out.println("Excepcion encontrada con el pedido con el id "+p.getId());
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
        //System.out.println("Error no se ha encontrado el pedido con el id:" + p.getId());
        return false;
    }

    public void crearPedidoHecho(Pedidoshechos p3) {
         Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();

            transaction = session.beginTransaction();

            session.save(p3);

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            //DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
    }

    public List<Pedidoshechos> generarTablaPedidosHechos() {
        Connection con = null;
        con = DataConnect.getConnection();
        List<Pedidoshechos> listaSangre = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM Pedidoshechos ";

            session.beginTransaction();

            Query query = session.createQuery(hql);

            if (!query.list().isEmpty()) {

                listaSangre = query.list();

                return listaSangre;
            }

            session.flush();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
        return listaSangre;
    }

}
