/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import manejador_archivos.ArchivoSerializable;
import manejador_archivos.ArchivoTexto;
import modelo.CarritoItem;
import modelo.Producto;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class TiendaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ArchivoSerializable as = new ArchivoSerializable("src/main/productos.dat");
    ArchivoTexto atxt = new ArchivoTexto();
    
    @FXML
    TextField txfCantidad;
    
    @FXML
    Button btnAgregar;
    
    @FXML
    Button btnConfirmar;
    
    @FXML
    Label lblError;
    
    @FXML
    Label lblCompra;
    
    @FXML
    TableView<Producto> tviewProductos;
    
    @FXML
    TableColumn<Producto, String> colNombre;
    
    @FXML
    TableColumn<Producto, Double> colPrecio;
    
    @FXML
    TableColumn<Producto, Integer> colStock;
    
    @FXML
    ListView<String> lviewCarrito;
    
    private ObservableList<CarritoItem> productosCarrito = FXCollections.observableArrayList();;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            cargarProductos();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        lblError.setText("");
        lblCompra.setText("");
        
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
    
    public void agregarProductos(ActionEvent e){
        lblError.setText("");
        lblCompra.setText("");
        
        ObservableList<String> productosTicket = lviewCarrito.getItems();
        
        Producto p = tviewProductos.getSelectionModel().getSelectedItem();
        int cantidad;
        int stockDisminuir;
        
        try{
            cantidad = Integer.parseInt(txfCantidad.getText());
        }catch(NumberFormatException ex){
            lblError.setText("Ingrese un numero");
            return;
        }
        if (cantidad < 0){
            lblError.setText("Ingrese un numero mayor a 0");
            return;
        }
       
        if(p != null){
            if (p.getStock() < cantidad){
                //error, no hay mas stocks
                lblError.setText("La cantidad supera el stock");
                return;
            }
            
            for (CarritoItem item : productosCarrito){
                /// verifica si el producto ya se encuentra en la ObservableList de productos!!!
                if (item.getProducto().equals(p)){
                    
                    item.setCantidad(item.getCantidad() + cantidad);
                    stockDisminuir = p.getStock() - cantidad;
                    p.setStock(stockDisminuir);
                    
                    int cantidadAuxiliar = item.getCantidad();
                    
                    /// busca para actualizar la cantidad de los productos que se encuentren dentro de la copia de ObservableList<String> de la listview!!!!!!!!!!!!!!!!!! 
                    for (int i = 0; i < productosTicket.size(); i++){
                        
                        String s = productosTicket.get(i);
                        if (s.toLowerCase().contains(p.getNombre().toLowerCase())){
                            System.out.println("contiene");
                            productosTicket.set(i, item.stringFormateado(cantidadAuxiliar));
                        }
                    }
                    
                    tviewProductos.refresh();
                    lviewCarrito.refresh();
                    return;
                }
            }
            
            productosCarrito.add(new CarritoItem(p, cantidad));

            stockDisminuir = p.getStock() - cantidad;
            p.setStock(stockDisminuir);
            
            lviewCarrito.getItems().add((new CarritoItem(p, cantidad).stringFormateado(cantidad)));
            tviewProductos.refresh();
            lviewCarrito.refresh();

            return;
        }
        lblError.setText("Seleccione un producto");
    }
    
    private void cargarProductos() throws IOException, FileNotFoundException, ClassNotFoundException{
        List<Producto> lista = as.cargarLista();
       
        tviewProductos.setItems(FXCollections.observableArrayList(lista));
    }
    
    public void confirmarCompra(ActionEvent e) throws IOException{
        if(productosCarrito.isEmpty()){
            lblError.setText("El carrito se encuentra vacio");
            return;
        }
        
        double total = 0;
        
        for (CarritoItem ci : productosCarrito){
            total += ci.subtotal();
        }
        
        String pagar= "TOTAL A PAGAR: $"+total;
        
        lblCompra.setText("Compra exitosa!");
        
        atxt.guardarLista(productosCarrito, pagar);
        
        List<Producto> copia = new ArrayList<>(tviewProductos.getItems());
        
        as.guardarLista(copia, "");
    }
}