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
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ule.edi.model.User;

@Repository
public class RegistroImpl implements RegistroDAO {

    private static final Logger logger = LoggerFactory.getLogger(RegistroImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addUser(User p) {
        Session session;
        session = this.sessionFactory.getCurrentSession();
        session.persist(p);
        logger.info("Person saved successfully, Person Details=" + p);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> listPersons() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> personsList = session.createQuery("from Person").list();
        for (User p : personsList) {
            logger.info("Person List::" + p);
        }
        return personsList;
    }

}
