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

import ule.edi.model.User;
 
public interface UserService {
 
    public void addPerson(User p);
    public List<User> listPersons();
     
}
