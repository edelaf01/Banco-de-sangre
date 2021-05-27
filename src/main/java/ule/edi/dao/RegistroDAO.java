package ule.edi.dao;

import java.util.List;

import ule.edi.model.User;

public interface RegistroDAO {
    public void addUser(User u);

    public List<User> listPersons();

}
