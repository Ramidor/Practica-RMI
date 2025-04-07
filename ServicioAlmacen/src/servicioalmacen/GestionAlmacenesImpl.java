package servicioalmacen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

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
        TProducto producto = null;
        if (posicion != -1) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(pNomFichero))) {
                // Leer la cabecera
                int numProductos = dis.readInt();  // Número de productos
                String nombreAlmacen = dis.readUTF();  // Nombre del almacén
                String direccionAlmacen = dis.readUTF();  // Dirección del almacén

                TDatosAlmacen almacen = new TDatosAlmacen(nombreAlmacen, direccionAlmacen, pNomFichero);
                
                // Leer los productos del fichero
                for (int i = 0; i < numProductos; i++) {
                    
                    producto.setCodProd(dis.readUTF());
                    producto.setNombreProd(dis.readUTF());
                    producto.setPrecio(dis.readFloat());
                    producto.setCantidad(dis.readInt());
                    producto.setDescripcion(dis.readUTF());
                    
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
        boolean ok=false;

        TDatosAlmacen almacen = DatosAlmacen(pAlmacen);
        try (DataOutputStream dis = new DataOutputStream(new FileOutputStream(almacen.getFichero()))) {
            
                dis.writeInt(almacen.getProductos().size());
                dis.writeUTF(almacen.getNombre());
                dis.writeUTF(almacen.getDireccion());
                
                for (int i = 0; i < almacen.getProductos().size(); i++) {
                    dis.writeUTF(almacen.getProductos().get(i).getCodProd());
                    dis.writeUTF(almacen.getProductos().get(i).getNombreProd());
                    dis.writeFloat(almacen.getProductos().get(i).getPrecio());
                    dis.writeInt(almacen.getProductos().get(i).getCantidad());
                    dis.writeUTF(almacen.getProductos().get(i).getCaducidad());
                    dis.writeUTF(almacen.getProductos().get(i).getDescripcion());
                }
                ok=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        return ok;
    }

    @Override
    public boolean CerrarAlmacen(int pAlmacen) throws RemoteException {
        boolean ok=false;
        
        
        return ok;
    }

    @Override
    public boolean AlmacenAbierto(int pAlmacen) throws RemoteException {
        boolean encontrado;
        
        if(almacenes.get(pAlmacen).getNClientes()==-1)
            encontrado = false;
        else
            encontrado = false;
        
        return encontrado;
    }

    @Override
    public int BuscaProducto(int pAlmacen, String pCodProducto) throws RemoteException {
        return 0;
    }

    @Override
    public TProducto ObtenerProducto(int pAlmacen, int pPosProducto) throws RemoteException {
        return null;
    }

    @Override
    public boolean AnadirProducto(int pAlmacen, TProducto pProdNuevo) throws RemoteException {
        return true;
    }

    @Override
    public boolean ActualizarProducto(int pAlmacen, TProducto pProducto) throws RemoteException {
        return true;
    }

    @Override
    public boolean EliminarProducto(int pAlmacen, String pCodProducto) throws RemoteException {
        return true;
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
