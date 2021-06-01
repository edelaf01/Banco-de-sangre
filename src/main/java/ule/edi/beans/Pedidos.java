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
        System.out.println("Prueba caja blanca Pedidos:\nVoy a buscar el pedido con el id: " + id);
        List<ule.edi.model.Pedidos> lista = adao.getPedido(p);

        if (lista.isEmpty()) {
            System.out.println("Prueba caja blanca Pedidos resultado: Lista esta vacia , no se ha encontrado el pedido con el id: " + id);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Pedido con el id dado no se ha encontrado , error", ""
                    ));
        } else {
            System.out.println("Prueba caja blanca Pedidos resultado: Se ha encontrado el pedido con el id: " + id);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Pedido encontrado", ""
                    ));

            String enviarSangreTipo = lista.get(0).getTipo();

            int cantidadAenviar = lista.get(0).getDosis();
            System.out.println("Voy a enviar sangre de tipo " + enviarSangreTipo
                    + " una cantidad total de " + cantidadAenviar + " dosis por completar el pedido");
            List<Stocksangrealmacen> listaStockActual = adao.generarTablaAlmacen();
            //tengo todo el stock disponible en listastockactual
            if (listaStockActual.isEmpty()) {
                System.out.println("Prueba caja blanca Pedidos resultado: El stock actual del almacen no dispone del tipo o la cantidad de sangre necesarias , StockActual para el tipo" + enviarSangreTipo + " isEmpty=true");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Error no hay stock", ""
                        ));
            } else {
                System.out.println("Hay stock disponible para el tipo de sangre: " + enviarSangreTipo + " voy a intentar enviar los maximos posibles de dosis");
                int tmp = cantidadAenviar, id2 = 0;
                String nom = "";
                for (int i = 0; i < listaStockActual.size(); i++) {
                    if ((listaStockActual.get(i).getTipo().equals(enviarSangreTipo)) && cantidadAenviar > 0) {

                        cantidadAenviar--;
                        System.out.println("He enviado una dosis , la cantidad ha enviar actual es de : " + cantidadAenviar);
                        //enviar esta sangre a stockhospital
                        Stocksangrealmacen enviaryborrar = listaStockActual.get(i);
                        Inventariohospital recibir = new Inventariohospital();
                        recibir.setTipoSangre(enviaryborrar.getTipo());
                        recibir.setFecha(enviaryborrar.getFecha());
                        if (i == 0) {

                            id2 = lista.get(0).getDestinatarioid();
                            System.out.println("Encuento el id al que le voy a enviar el pedido que es id: " + id2 + " . Ahora busco el nombre para ese id de usuario destinatario");
                            nom = adao.getNombreDestinatario(id2);
                            System.out.println("El nombre del usuario que va a recibir es: " + nom);
                        }
                        //OBTENGO EL USERNAME PARA EL ID

                        recibir.setNombreDuenyo(nom);
                        //recibir.setNombreDuenyo();
                        System.out.println("Envio sangre al usuario " + nom);
                        adao.enviarSangre(recibir);

                        adao.borrarSangre(enviaryborrar);
                        System.out.println("Borro una dosis del stock almacen ");
                        //uno menos en el pedido
                        adao.actualizarPedido(lista.get(0));
                        System.out.println("Actualizo el pedido, ahora quedan  " + lista.get(0).getDosis());

                    }
                }
                if (cantidadAenviar == 0) {
                    System.out.println("Pedido con el id  " + id2 + " completado, procedo a borrar el pedido y moverlo a pedidos completados");
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
                    System.out.println("Pedido con el id  " + id2 + " no puede ser completado por falta de stock error");
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Error no hay stock para completar el pedido", ""
                            ));
                }
            }
        }

    }

    public void listaStockAlmacen() {
        AlmacenImpl adao = new AlmacenImpl();
        setListaAlmacen(adao.generarTablaAlmacen());
        this.listaPedidosActualizar();

    }

    public void listaPedidosActualizar() {
        ule.edi.dao.AlmacenImpl adao = new AlmacenImpl();
        setListaPedidos(adao.generarTablaPedidos());

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
