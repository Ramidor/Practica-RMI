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
    int nAlmacenesAbiertos = 0;

    public GestionAlmacenesImpl() throws RemoteException {
        super();
        almacenes = new ArrayList<>();
    }

    @Override
    public TDatosAlmacen DatosAlmacen(int pAlmacen) throws RemoteException {
        TDatosAlmacen almacen = null;
        if (AlmacenAbierto(pAlmacen)) {

            almacen = almacenes.get(pAlmacen);
        }
        return almacen;
    }

    @Override
    public int NProductos(int pAlmacen) throws RemoteException {
        return almacenes.get(pAlmacen).getProductos().size();
    }

    @Override
    public int CrearAlmacen(String pNombre, String pDireccion, String pNomFichero) throws RemoteException {
        int posicion = buscarAlmacenAbierto(pNomFichero);
        if (posicion == -1) {
            TDatosAlmacen almacen = new TDatosAlmacen(pNombre, pDireccion, pNomFichero);
            almacen.setNClientes(1);
            almacenes.add(almacen);
            posicion = buscarAlmacenAbierto(pNomFichero);

            nAlmacenesAbiertos++;
        } else {
            int nClientes = almacenes.get(posicion).getNClientes();
            almacenes.get(posicion).setNClientes(nClientes);
            nAlmacenesAbiertos++;
        }

        return posicion;
    }

    @Override
    public int AbrirAlmacen(String pNomFichero) throws RemoteException {
        int posicion = buscarAlmacenAbierto(pNomFichero);
        TProducto producto = null;
        if (posicion == -1) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(pNomFichero))) {
                // Leer la cabecera
                int numProductos = dis.readInt();  // Número de productos
                String nombreAlmacen = dis.readUTF();  // Nombre del almacén
                String direccionAlmacen = dis.readUTF();  // Dirección del almacén

                TDatosAlmacen almacen = new TDatosAlmacen(nombreAlmacen, direccionAlmacen, pNomFichero);

                almacen.setNClientes(1);
                // Leer los productos del fichero
                for (int i = 0; i < numProductos; i++) {

                    producto.setCodProd(dis.readUTF());
                    producto.setCantidad(dis.readInt());
                    producto.setNombreProd(dis.readUTF());
                    producto.setPrecio(dis.readFloat());

                    producto.setDescripcion(dis.readUTF());
                    producto.setDia(dis.readInt());
                    producto.setMes(dis.readInt());
                    producto.setAno(dis.readInt());

                    almacen.getProductos().add(producto);
                }
                almacenes.add(almacen);
                nAlmacenesAbiertos++;
                posicion = buscarAlmacenAbierto(pNomFichero);
                System.out.println("" + posicion);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            int nClientes = almacenes.get(posicion).getNClientes();
            almacenes.get(posicion).setNClientes(nClientes);
            nAlmacenesAbiertos++;
        }

        return posicion;
    }

    @Override
    public boolean GuardarAlmacen(int pAlmacen) throws RemoteException {
        boolean ok = false;

        TDatosAlmacen almacen = DatosAlmacen(pAlmacen);
        try (DataOutputStream dis = new DataOutputStream(new FileOutputStream(almacen.getFichero()))) {

            dis.writeInt(almacen.getProductos().size());
            dis.writeUTF(almacen.getNombre());
            dis.writeUTF(almacen.getDireccion());

            for (int i = 0; i < almacen.getProductos().size(); i++) {
                dis.writeUTF(almacen.getProductos().get(i).getCodProd());
                dis.writeInt(almacen.getProductos().get(i).getCantidad());

                dis.writeUTF(almacen.getProductos().get(i).getNombreProd());
                dis.writeFloat(almacen.getProductos().get(i).getPrecio());
                dis.writeUTF(almacen.getProductos().get(i).getDescripcion());
                dis.writeInt(almacen.getProductos().get(i).getDia());
                dis.writeInt(almacen.getProductos().get(i).getMes());
                dis.writeInt(almacen.getProductos().get(i).getAno());

            }
            ok = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ok;
    }

    @Override
    public boolean CerrarAlmacen(int pAlmacen) throws RemoteException {
        boolean ok = false;
        int nClientes = almacenes.get(pAlmacen).getNClientes();
        if (nClientes > 1) {
            nClientes--;
            GuardarAlmacen(pAlmacen);
            almacenes.get(pAlmacen).setNClientes(nClientes);
            ok = true;
        } else {
            GuardarAlmacen(pAlmacen);
            nClientes = -1;
            almacenes.get(pAlmacen).setNClientes(nClientes);
            ok = true;
            nAlmacenesAbiertos--;
            if (nAlmacenesAbiertos == 0) {
                almacenes.clear();
            }
        }
        return ok;
    }

    @Override
    public boolean AlmacenAbierto(int pAlmacen) throws RemoteException {
        boolean encontrado;

        if (almacenes.get(pAlmacen).getNClientes() == -1 || pAlmacen > almacenes.size() || pAlmacen < 0) {
            encontrado = false;

        } else {

            encontrado = true;
        }

        return encontrado;
    }

    @Override
    public int BuscaProducto(int pAlmacen, String pCodProducto) throws RemoteException {
        int posicion = -1;
        TDatosAlmacen almacen = DatosAlmacen(pAlmacen);
        for (int i = 0; i < almacen.getProductos().size(); i++) {
            if (almacen.getProductos().get(i).getCodProd().equals(pCodProducto)) {
                posicion = i;
            }
        }
        return posicion;
    }

    @Override
    public TProducto ObtenerProducto(int pAlmacen, int pPosProducto) throws RemoteException {
        TProducto producto = null;
        TDatosAlmacen almacen = DatosAlmacen(pAlmacen);
        if (pPosProducto >= 0 && pPosProducto < almacen.getProductos().size()) {
            producto = almacen.getProductos().get(pPosProducto);
        }
        return producto;
    }

    @Override
    public boolean AnadirProducto(int pAlmacen, TProducto pProdNuevo) throws RemoteException {
        boolean ok = false;
        TDatosAlmacen almacen = DatosAlmacen(pAlmacen);
        System.out.println("Pito chico");
        if (almacen.getProductos().add(pProdNuevo)) {
            System.out.println("Pito intermedio");
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean ActualizarProducto(int pAlmacen, TProducto pProducto) throws RemoteException {
        boolean ok = false;
        int posicion = BuscaProducto(pAlmacen, pProducto.getCodProd());
        TDatosAlmacen almacen = DatosAlmacen(pAlmacen);
        if (posicion != -1) {
            almacen.getProductos().add(posicion, pProducto);
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean EliminarProducto(int pAlmacen, String pCodProducto) throws RemoteException {
        boolean ok = false;
        TDatosAlmacen almacen = DatosAlmacen(pAlmacen);
        for (int i = 0; i < almacen.getProductos().size(); i++) {
            if (almacen.getProductos().get(i).getCodProd().equals(pCodProducto)) {
                almacen.getProductos().remove(i);
                ok = true;
            }
        }
        return ok;
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
