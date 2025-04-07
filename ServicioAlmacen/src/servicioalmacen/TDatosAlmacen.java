package servicioalmacen;

import java.util.ArrayList;

/**
 *
 * @author raulp
 */
public class TDatosAlmacen {

    private String nombre;
    private String direccion;
    private String fichero;
    private int NClientes;
    private ArrayList<TProducto> productos;

    public TDatosAlmacen(String nombre, String direccion, String fichero) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fichero = fichero;
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public int getNClientes() {
        return NClientes;
    }

    public void setNClientes(int NClientes) {
        this.NClientes = NClientes;
    }

    public ArrayList<TProducto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<TProducto> productos) {
        this.productos = productos;
    }

}
