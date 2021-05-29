package ule.edi.dao;

import java.util.List;

import ule.edi.model.Donantevalidar;

public interface CampoDAO {
    public void addSangre(Donantevalidar u);

    public List<Donantevalidar> listPersons();

}
