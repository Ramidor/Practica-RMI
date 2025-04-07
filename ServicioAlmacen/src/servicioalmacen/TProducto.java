package servicioalmacen;

import java.util.Date;

/**
 *
 * @author raulp
 */
public class TProducto {

    private String codProd;
    private int cantidad;
    private String nombreProd;
    private float precio;
    private String descripcion;
    private String caducidad;

    public TProducto(String codProd, int cantidad, String nombreProd, float precio, String descripcion, String caducidad) {
        this.codProd = codProd;
        this.cantidad = cantidad;
        this.nombreProd = nombreProd;
        this.precio = precio;
        this.descripcion = descripcion;
        this.caducidad = caducidad;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }
    
    
}
