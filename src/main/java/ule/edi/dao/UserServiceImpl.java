/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ule.edi.dao;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ule.edi.model.User;
import ule.edi.dao.RegistroDAO;
/**
 *
 * @author kkkk
 */
@Service
@ManagedBean(name="personService")
@SessionScoped
public class UserServiceImpl  implements UserService{
    private RegistroDAO personDAO;
	 
    public void setPersonDAO(RegistroDAO personDAO) {
        this.personDAO = personDAO;
    }
 
    @Override
    @Transactional
    public void addPerson(User p) {
        this.personDAO.addUser(p);
    }
 
  
}
