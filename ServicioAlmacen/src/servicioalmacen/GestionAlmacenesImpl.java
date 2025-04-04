package servicioalmacen;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author raulp
 */
public class GestionAlmacenesImpl extends UnicastRemoteObject implements GestionAlmacenes {

    ArrayList<TDatosAlmacen> almacenes;

    public GestionAlmacenesImpl() throws RemoteException {
        super();
        almacenes = new ArrayList<>();
    }

    @Override
    public TDatosAlmacen DatosAlmacen(int pAlmacen) throws RemoteException {
        return almacenes.get(pAlmacen);
    }

    @Override
    public int NProductos(int pAlmacen) throws RemoteException {
        return almacenes.get(pAlmacen).getProductos().size();
    }

    @Override
    public int CrearAlmacen(String pNombre, String pDireccion, String pNomFichero) throws RemoteException {
        int posicion = buscarAlmacenAbierto(pNomFichero);
        if (posicion != -1) {
            TDatosAlmacen almacen = new TDatosAlmacen(pNombre, pDireccion, pNomFichero);
            almacenes.add(almacen);
            posicion = buscarAlmacenAbierto(pNomFichero);
        }

        return posicion;
    }

    @Override
    public int AbrirAlmacen(String pNomFichero) throws RemoteException {
        int posicion = buscarAlmacenAbierto(pNomFichero);
        if (posicion != -1) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(pNomFichero))) {
                // Leer la cabecera
                int numProductos = dis.readInt();  // Número de productos
                String nombreAlmacen = dis.readUTF();  // Nombre del almacén
                String direccionAlmacen = dis.readUTF();  // Dirección del almacén

                TDatosAlmacen almacen = new TDatosAlmacen(nombreAlmacen, direccionAlmacen, pNomFichero);
                
                // Leer y mostrar los productos
                for (int i = 0; i < numProductos; i++) {
                    String codigo = dis.readUTF();
                    String nombre = dis.readUTF();
                    Float precio = dis.readFloat();
                    int cantidad = dis.readInt();
                    
                    String nombre = dis.readUTF();
                    almacen.getProductos().add(producto);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return posicion;
    }

    @Override
    public boolean GuardarAlmacen(int pAlmacen) throws RemoteException {
    }

    @Override
    public boolean CerrarAlmacen(int pAlmacen) throws RemoteException {
    }

    @Override
    public boolean AlmacenAbierto(int pAlmacen) throws RemoteException {
    }

    @Override
    public int BuscaProducto(int pAlmacen, String pCodProducto) throws RemoteException {
    }

    @Override
    public TProducto ObtenerProducto(int pAlmacen, int pPosProducto) throws RemoteException {
    }

    @Override
    public boolean AnadirProducto(int pAlmacen, TProducto pProdNuevo) throws RemoteException {
    }

    @Override
    public boolean ActualizarProducto(int pAlmacen, TProducto pProducto) throws RemoteException {
    }

    @Override
    public boolean EliminarProducto(int pAlmacen, String pCodProducto) throws RemoteException {
    }

    private int buscarAlmacenAbierto(String pNomFichero) {
        int posicion = -1;
        for (int i = 0; i < almacenes.size(); i++) {
            if (almacenes.get(i).getFichero().equals(pNomFichero)) {
                posicion = i;
            }
        }

        return posicion;
    }
}
