package ule.edi.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ule.edi.dao.AlmacenImpl;
import ule.edi.dao.LaboratorioImpl;
import ule.edi.model.Donantevalidar;
import ule.edi.model.Inventariohospital;

import ule.edi.model.Stocksangrealmacen;

import ule.edi.util.SessionUtils;

@ManagedBean(name = "pedidos")
@SessionScoped
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 1094801825228386358L;

    private Integer id;
    private String msg;

    private List<ule.edi.model.Pedidos> listaPedidos;
    private List<Stocksangrealmacen> listaAlmacen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer pwd) {
        this.id = pwd;
    }

    public List<ule.edi.model.Pedidos> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<ule.edi.model.Pedidos> users) {
        this.listaPedidos = users;
    }

    public List<Stocksangrealmacen> getListaAlmacen() {
        return listaAlmacen;
    }

    public void setListaAlmacen(List<Stocksangrealmacen> users) {
        this.listaAlmacen = users;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //Para enviar necesitamos recibir un id
    public void enviarPedido() {
        ule.edi.model.Pedidos p = new ule.edi.model.Pedidos();
        p.setId(id);
        //Con esto tenemos el id del pedido que queremos hacer
        AlmacenImpl adao = new AlmacenImpl();
        List<ule.edi.model.Pedidos> lista = adao.getPedido(p);
        if (lista.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Pedido con el id dado no se ha encontrado , error", ""
                    ));
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Pedido encontrado", ""
                    ));
            String enviarSangreTipo = lista.get(0).getTipo();
            int cantidadAenviar = lista.get(0).getDosis();
            List<Stocksangrealmacen> listaStockActual = adao.generarTablaAlmacen();
            //tengo todo el stock disponible en listastockactual
            if (listaStockActual.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Error no hay stock", ""
                        ));
            } else {
                for (int i = 0; i < listaStockActual.size(); i++) {
                    if ((listaStockActual.get(i).getTipo().equals(enviarSangreTipo)) && cantidadAenviar > 0) {
                        cantidadAenviar--;
                       //enviar esta sangre a stockhospital
                       Stocksangrealmacen enviaryborrar=listaStockActual.get(i);
                       Inventariohospital recibir = new Inventariohospital();
                       recibir.setTipoSangre(enviaryborrar.getTipo());
                       recibir.setFecha(enviaryborrar.getFecha());
                       
                       adao.enviarSangre(recibir);
                       adao.borrarSangre(enviaryborrar);
                    }
                }
            }
        }

    }

    public List<Stocksangrealmacen> listaStockAlmacen() {
        AlmacenImpl adao = new AlmacenImpl();
        setListaAlmacen(adao.generarTablaAlmacen());
        this.listaPedidosActualizar();
        return listaAlmacen;

    }

    public List<ule.edi.model.Pedidos> listaPedidosActualizar() {
        ule.edi.dao.AlmacenImpl adao = new AlmacenImpl();
        setListaPedidos(adao.generarTablaPedidos());

        return listaPedidos;

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
