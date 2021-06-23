package ule.edi.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    private String usuarioDestino;
    private List<ule.edi.model.Pedidos> listaPedidos;
    private List<ule.edi.model.Pedidos> listaPedidosFiltrada;
    private List<Stocksangrealmacen> listaAlmacen;
    private List<Stocksangrealmacen> listaAlmacenFiltrada;
    private List<Pedidoshechos> listaPedidosH;
    private List<Pedidoshechos> listaPedidosFiltradaH;

    public List<Pedidoshechos> getListaPedidosFiltradaH() {
        return listaPedidosFiltradaH;
    }

    public void setListaPedidosH(List<Pedidoshechos> users) {
        this.listaPedidosH = users;
    }

    public List<Pedidoshechos> getListaPedidosH() {
        return listaPedidosH;
    }

    public void setListaPedidosFiltradaH(List<Pedidoshechos> users) {
        this.listaPedidosFiltradaH = users;
    }

    public List<Stocksangrealmacen> getListaAlmacenFiltrada() {
        return listaAlmacenFiltrada;
    }

    public void setListaAlmacenFiltrada(List<Stocksangrealmacen> users) {
        this.listaAlmacenFiltrada = users;
    }

    public List<ule.edi.model.Pedidos> getListaPedidosFiltrada() {
        return listaPedidosFiltrada;
    }

    public void setListaPedidosFiltrada(List<ule.edi.model.Pedidos> users) {
        this.listaPedidosFiltrada = users;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(String msg) {
        this.usuarioDestino = msg;
    }

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
        p.setUsuarioDestino(usuarioDestino);
        System.out.println("AAAAAAA " + p.getId());
        //Con esto tenemos el id del pedido que queremos hacer
        AlmacenImpl adao = new AlmacenImpl();
        System.out.println("Prueba caja blanca Pedidos:\nVoy a buscar el pedido con el id: " + id);
        List<ule.edi.model.Pedidos> lista = adao.getPedido(p);
        try {
            lista.isEmpty();
        } catch (NullPointerException e) {
            System.out.println("Prueba caja blanca Pedidos resultado: Lista esta vacia , no se ha encontrado el pedido con el id: " + id);
            FacesContext.getCurrentInstance().addMessage(
                    "MessageId",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Pedido con el id dado no se ha encontrado , error", ""
                    ));
            return;
        }
        if (lista.isEmpty()) {

        } else {
            System.out.println("Prueba caja blanca Pedidos resultado: Se ha encontrado el pedido con el id: " + id);

            String enviarSangreTipo = lista.get(0).getTipo();
            //int a=lista.get(0).ge
            int cantidadAenviar = lista.get(0).getDosis();
            System.out.println("Voy a enviar sangre de tipo " + enviarSangreTipo
                    + " una cantidad total de " + cantidadAenviar + " dosis por completar el pedido");
            List<Stocksangrealmacen> listaStockActual = adao.generarTablaAlmacen();
            //tengo todo el stock disponible en listastockactual
            if (listaStockActual.isEmpty()) {
                System.out.println("Prueba caja blanca Pedidos resultado: El stock actual del almacen no dispone del tipo o la cantidad de sangre necesarias , StockActual para el tipo" + enviarSangreTipo + " isEmpty=true");
                FacesContext.getCurrentInstance().addMessage(
                        "MessageId",
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Error no hay stock", ""
                        ));
            } else {
                System.out.println("Hay stock disponible para el tipo de sangre: " + enviarSangreTipo + " voy a intentar enviar los maximos posibles de dosis");
                int tmp = cantidadAenviar;
                String id2 = "";
                String nom = "";
                //añado a pedidos completados sin la fecha
                //miro a ver si el pedido con el id existe

                ule.edi.model.Pedidos p2 = new ule.edi.model.Pedidos();
                p2 = lista.get(0);
                Pedidoshechos p3 = new Pedidoshechos();

                p3.setId(p2.getId());
                boolean existe = adao.existePedidoCompletado(p3);
                if (existe == false) {
                    //lo creo
                    p3.setUsuarioDestino(p2.getUsuarioDestino());
                    p3.setDosis(p2.getDosis());
                    p3.setFecha(p2.getFecha());
                    p3.setTipo(p2.getTipo());
                    p3.setDosisOriginales(p2.getDosis());
                    adao.crearPedidoHecho(p3);
                }

                // Date date = new Date(System.currentTimeMillis());
                // p3.setFechacompletarpedido(date);
                //
                int enviados = 0;
                for (int i = 0; i < listaStockActual.size(); i++) {
                    if ((listaStockActual.get(i).getTipo().equals(enviarSangreTipo)) && cantidadAenviar > 0) {
                        enviados++;
                        cantidadAenviar--;
                        System.out.println("He enviado una dosis , la cantidad ha enviar actual es de : " + cantidadAenviar);
                        //enviar esta sangre a stockhospital
                        Stocksangrealmacen enviaryborrar = listaStockActual.get(i);
                        Inventariohospital recibir = new Inventariohospital();
                        recibir.setTipoSangre(enviaryborrar.getTipo());
                        System.out.println("xxxxxxzzzz "+enviaryborrar.getFecha().toString());
                        recibir.setFecha(enviaryborrar.getFecha());
                        if (i == 0) {

                            id2 = lista.get(0).getUsuarioDestino();
                            System.out.println("Encuentro el id al que le voy a enviar el pedido que es id: " + id2 + " . Ahora busco el nombre para ese id de usuario destinatario");
                            // nom = adao.getNombreDestinatario(id2);
                            // System.out.println("El nombre del usuario que va a recibir es: " + nom);
                        }
                        //OBTENGO EL USERNAME PARA EL ID

                        recibir.setNombreDuenyo(id2);
                        //recibir.setNombreDuenyo();
                        System.out.println("Envio sangre al usuario " + id2);
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
                    //ule.edi.model.Pedidos p2 = new ule.edi.model.Pedidos();
                    p2 = lista.get(0);
                    p3.setDosis(0);
                    Date date2 = new Date(System.currentTimeMillis());
                    p3.setFechacompletarpedido(date2);
                    //lo envio
                    adao.enviarPedidoCompletado(p3);
                    //lo borro
                    adao.borrarPedidoCompletado(p2);
                    FacesContext.getCurrentInstance().addMessage(
                            "MessageId",
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Se ha completado un pedido", ""
                            ));
                    return;
                }
                if (tmp == cantidadAenviar) {
                    System.out.println("Pedido con el id  " + id2 + " no puede ser completado por falta de stock error");
                    FacesContext.getCurrentInstance().addMessage(
                            "MessageId",
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Error no hay stock para completar el pedido", ""
                            ));
                    return;

                }
                FacesContext.getCurrentInstance().addMessage(
                        "MessageId",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Se ha realizado un pedido a medias por falta de stock , se han enviado " + enviados, ""
                        ));

            }
        }

    }

    public List<Stocksangrealmacen> listaStockAlmacen() {
        AlmacenImpl adao = new AlmacenImpl();
        setListaAlmacen(adao.generarTablaAlmacen());
        listaPedidosActualizar();
        listaPedidosActualizarH();
        return listaAlmacen;

    }

    public List<ule.edi.model.Pedidos> listaPedidosActualizar() {
        ule.edi.dao.AlmacenImpl adao = new AlmacenImpl();
        setListaPedidos(adao.generarTablaPedidos());

        return listaPedidos;

    }

    public void listaPedidosActualizarH() {
        ule.edi.dao.AlmacenImpl adao = new AlmacenImpl();
        List<Pedidoshechos> temp = adao.generarTablaPedidosHechos();
        List<Pedidoshechos> temp2 = new ArrayList<>();
        int cont = 0;
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getDosis() != 0) {
                //no lo anyado

            } else {
                temp2.add(temp.get(i));
            }
        }
        setListaPedidosH(temp2);

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
