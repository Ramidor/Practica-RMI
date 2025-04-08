/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestoralmacenes_servidor;

import servicioalmacen.GestionAlmacenes;
import servicioalmacen.GestionAlmacenesImpl;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 *
 * @author Adrian
 */
public class GestorAlmacenes_Servidor {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException{
        // TODO code application logic here
                try {
            int Puerto = 0;
            Scanner Teclado = new Scanner(System.in);
            System.out.print("Introduce el nº de puerto para comunicarse: ");
            Puerto = Teclado.nextInt();

            Registry registry = LocateRegistry.createRegistry(Puerto);
            GestionAlmacenesImpl obj = new GestionAlmacenesImpl();

            //GestionAlmacenes stub = (GestionAlmacenes) UnicastRemoteObject.exportObject(obj, Puerto);



            registry = LocateRegistry.getRegistry(Puerto);
            registry.bind("GestorAlmacen", obj);

            System.out.println("Servidor Gestor Almacen esperando peticiones ... ");
        } catch (Exception e) {
            System.out.println("Error en servidor Gestor Almacen:" + e);
        }
    }
    
}
