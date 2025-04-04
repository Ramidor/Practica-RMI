/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestoralmacenes_cliente;

import servicioalmacen.GestionAlmacenes;
import java.rmi.Naming;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Adrian
 */
public class GestorAlmacenes_Cliente {

    public static int MenuPrincipal() {
        Scanner Teclado = new Scanner(System.in);
        int Salida;
        do {
            System.out.println("\n****************************");
            System.out.println("**");
            System.out.println("** 1.- Crear un almacen vacío");
            System.out.println("** 2.- Abrir un  fichero de almacén");
            System.out.println("** 3.- Cerrar un almacen");
            System.out.println("** 4.- Guardar Datos");
            System.out.println("** 5.- Listar productos del almacén");
            System.out.println("** 6.- Añadir un producto");
            System.out.println("** 7.- Actualizar un producto");
            System.out.println("** 8.- Consultar un producto");
            System.out.println("** 9.- Eliminar un producto");
            System.out.println("** 0.- Salir");
            System.out.println("**");
            System.out.print("** Elige Opcion:");
            Salida = Teclado.nextInt();
        } while (Salida < 1 || Salida > 9);
        return Salida;
    }

    ;
public static void main(String[] args) {
        try {
            int Puerto = 0;
            String Host;
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el nº de puerto para comunicarse: ");
            Puerto = sc.nextInt();
            System.out.print("Introduce el nombre del host: ");
            Host = sc.next();

            // Obtiene el stub del rmiregistry
            Random rnd = new Random(System.nanoTime());
            GestionAlmacenes gestStub = (GestionAlmacenes) Naming.lookup("rmi://" + Host + ":" + Puerto + "/GestorAlmacen");
            String nombreAlmacen = null, nombreDireccion = null, nombreFichero = null;

            int Opcion = 0, posAlmacenAbierto = -1;

            do {
                Opcion = MenuPrincipal();
                if (Opcion > 9 || Opcion < 1) {
                    System.out.println("Opcion Incorrecta CRACK, VE ESPABILANDO Y PON HALGO BIEN");
                    Opcion = MenuPrincipal();
                }

                switch (Opcion) {
                    case 1:
                        if (posAlmacenAbierto != -1) {
                            gestStub.CerrarAlmacen(posAlmacenAbierto);
                            posAlmacenAbierto = -1;
                        }
                        System.out.println("Introduce el nombre del almacen nuevo");
                        sc.next(nombreAlmacen);
                        System.out.println("Introduce la direccion del almacen nuevo");
                        sc.next(nombreDireccion);
                        System.out.println("Introduce el nombre del fichero del almacen nuevo");
                        sc.next(nombreFichero);

                        posAlmacenAbierto = gestStub.CrearAlmacen(nombreAlmacen, nombreDireccion, nombreFichero);

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        break;
                    case 5:
                        break;

                    case 6:
                        break;

                    case 7:
                        break;

                    case 8:
                        break;

                    case 9:
                        break;

                }

            } while (Opcion != 0);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
