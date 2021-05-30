package ule.edi.dao;

import java.util.List;

import ule.edi.model.Donantevalidar;

public interface LaboratorioDAO {
    public void addSangre(Donantevalidar u);
    public void borrarSangre(Donantevalidar p);
    public List<Donantevalidar> generarTabla();
 
}
