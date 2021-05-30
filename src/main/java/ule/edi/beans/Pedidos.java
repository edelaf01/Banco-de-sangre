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
import ule.edi.model.Pedidoshechos;

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
                int tmp = cantidadAenviar;
                for (int i = 0; i < listaStockActual.size(); i++) {
                    if ((listaStockActual.get(i).getTipo().equals(enviarSangreTipo)) && cantidadAenviar > 0) {
                        cantidadAenviar--;
                        //enviar esta sangre a stockhospital
                        Stocksangrealmacen enviaryborrar = listaStockActual.get(i);
                        Inventariohospital recibir = new Inventariohospital();
                        recibir.setTipoSangre(enviaryborrar.getTipo());
                        recibir.setFecha(enviaryborrar.getFecha());
                        int id2 = lista.get(0).getDestinatarioid();
                        //OBTENGO EL USERNAME PARA EL ID
                        String nom = adao.getNombreDestinatario(id2);
                        recibir.setNombreDuenyo(nom);
                        //recibir.setNombreDuenyo();
                        adao.enviarSangre(recibir);
                        adao.borrarSangre(enviaryborrar);
                        //uno menos en el pedido
                        adao.actualizarPedido(lista.get(0));
                        //TO-DO MOVER A PEDIDOS COMPLETADOS si llega a 0 pendientes
                    }
                }
                if (cantidadAenviar == 0) {
                    ule.edi.model.Pedidos p2 = new ule.edi.model.Pedidos();
                    p2 = lista.get(0);
                    Pedidoshechos p3 = new Pedidoshechos();
                    p3.setDestinatarioid(p2.getDestinatarioid());
                    p3.setDosis(p2.getDosis());
                    p3.setFecha(p2.getFecha());
                    p3.setTipo(p2.getTipo());
                    Date date = new Date(System.currentTimeMillis());
                    p3.setFechacompletarpedido(date);
                    //lo envio
                    adao.enviarPedidoCompletado(p3);
                    //lo borro
                    adao.borrarPedidoCompletado(p2);
                     FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Se ha completado un pedido,basado", ""
                            ));
                }
                if (tmp == cantidadAenviar) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Error no hay stock para completar el pedido", ""
                            ));
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
