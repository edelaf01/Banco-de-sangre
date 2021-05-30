package ule.edi.dao;

import java.util.List;

import ule.edi.model.Donantevalidar;
import ule.edi.model.Pedidos;
import ule.edi.model.Stocksangrealmacen;

public interface AlmacenDAO {

    public void addSangre(Donantevalidar u);

    public void borrarSangre(Donantevalidar p);

    public List<Stocksangrealmacen> generarTablaAlmacen();

    public List<Pedidos> generarTablaPedidos();

}
