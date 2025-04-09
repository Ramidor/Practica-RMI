package servicioalmacen;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author raulp
 */
public class TProducto implements Serializable {

    private String codProd;
    private int cantidad;
    private String nombreProd;
    private float precio;
    private String descripcion;

    private int dia;
    private int mes;
    private int ano;

    public TProducto(String codProd, int cantidad, String nombreProd, float precio, String descripcion, int dia, int mes, int ano) {
        this.codProd = codProd;
        this.cantidad = cantidad;
        this.nombreProd = nombreProd;
        this.precio = precio;
        this.descripcion = descripcion;

        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public TProducto() {
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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        String resultado = null;
        resultado = "" + getCodProd() + "\t\t" + getNombreProd() + "\t\t" + getPrecio() + "\t\t"
                + "" + getCantidad() + "\t\t" + getDia() + "/" + getMes() + "/" + getAno();
        return resultado;
    }

}
