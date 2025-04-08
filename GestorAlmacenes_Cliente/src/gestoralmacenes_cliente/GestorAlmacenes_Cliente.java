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
import servicioalmacen.TDatosAlmacen;
import servicioalmacen.TProducto;

/**
 *
 * @author Adrian
 */
public class GestorAlmacenes_Cliente {

    public static int MenuPrincipal(String nombreFichero) {
        Scanner Teclado = new Scanner(System.in);
        int Salida;
        do {

            System.out.println("\n**************************** Nombre del almacen: "+nombreFichero);
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
        } while (Salida < 0 || Salida > 9);
        return Salida;
    }

    ;
public static void main(String[] args) {
        try {
            int Puerto = 0;
            String Host;
            Scanner sc = new Scanner(System.in);//NUMEROS
            Scanner sc2 = new Scanner(System.in);//STRING
            System.out.print("Introduce el nº de puerto para comunicarse: ");
            Puerto = sc.nextInt();
            System.out.print("Introduce el nombre del host: ");
            Host = sc.next();

            // Obtiene el stub del rmiregistry
            Random rnd = new Random(System.nanoTime());
            GestionAlmacenes gestStub = (GestionAlmacenes) Naming.lookup("rmi://" + Host + ":" + Puerto + "/GestorAlmacen");
            String nombreAlmacen = null, nombreDireccion = null, nombreFichero = null;
            String codProducto, nombreProd, fechaProd, descripcionProd;
            int numProducto;
            TDatosAlmacen datosAlmacen = null;
            TProducto datosProducto = null;

            int Opcion = 0, posAlmacenAbierto = -1;

            do {
                if(posAlmacenAbierto!=-1){
                datosAlmacen = gestStub.DatosAlmacen(posAlmacenAbierto);
                Opcion = MenuPrincipal(""+datosAlmacen.getNombre());
                }else
                    Opcion = MenuPrincipal("");
                    
                if (Opcion > 9 || Opcion < 0) {
                    System.out.println("Opcion Incorrecta CRACK, VE ESPABILANDO Y PON HALGO BIEN");
                    Opcion = MenuPrincipal(nombreFichero);
                }

                switch (Opcion) {
                    case 1:
                        if (posAlmacenAbierto != -1) {
                            gestStub.CerrarAlmacen(posAlmacenAbierto);
                            posAlmacenAbierto = -1;
                        }
                        System.out.println("Introduce el nombre del almacen nuevo: ");
                        nombreAlmacen=sc2.nextLine();
                        System.out.println("Introduce la direccion del almacen nuevo");
                        nombreDireccion=sc2.nextLine();
                        System.out.println("Introduce el nombre del fichero del almacen nuevo");
                        nombreFichero=sc2.nextLine();

                        posAlmacenAbierto = gestStub.CrearAlmacen(nombreAlmacen, nombreDireccion, nombreFichero);
                        System.out.println(""+posAlmacenAbierto);
                        break;
                    case 2:
                        if (posAlmacenAbierto != -1) {
                            gestStub.CerrarAlmacen(posAlmacenAbierto);
                            posAlmacenAbierto = -1;
                        }
                        System.out.println("Introduce el nombre del fichero del almacen");
                        sc.next(nombreFichero);
                        posAlmacenAbierto = gestStub.AbrirAlmacen(nombreFichero);
                        if (posAlmacenAbierto == -1) {
                            System.out.println("El fichero no se ha logrado abrir correctamente");
                        } else {
                            System.out.println("El fichero se ha abierto correctamente en la posicion: "
                                    + posAlmacenAbierto + ".");
                        }

                        break;
                    case 3:
                        if (posAlmacenAbierto == -1) {
                            System.out.println("No hay ningún almacen abierto");
                        } else {
                            gestStub.CerrarAlmacen(posAlmacenAbierto);
                            posAlmacenAbierto = -1;
                            System.out.println("Almacen cerrado con exito");
                        }

                        break;
                    case 4:
                        if (posAlmacenAbierto == -1) {
                            System.out.println("No hay ningún almacen abierto");
                        } else {
                            gestStub.GuardarAlmacen(posAlmacenAbierto);

                            System.out.println("Almacen guardado en fichero");
                        }

                        break;
                    case 5:
                        if (posAlmacenAbierto == -1) {
                            System.out.println("No hay ningún almacen abierto");
                        } else {
                            System.out.println("Listado del almacen ''" + datosAlmacen.getNombre() + "''"
                                    + "localizado en ''" + datosAlmacen.getDireccion());
                            System.out.println("********************************************************************");
                            System.out.println("CODIGO\t\tNOMBRE\t\tPRECIO\t\tCANTIDAD\t\tFECHA CADUCIDAD");
                            numProducto = gestStub.NProductos(posAlmacenAbierto);
                            for (int i = 0; i < numProducto; i++) {
                                datosProducto = gestStub.ObtenerProducto(posAlmacenAbierto, i);
                                System.out.println("" + datosProducto.toString());

                            }

                        }
                        break;

                    case 6:
                        String codProd,
                         fecha,
                         cantidad,
                         precio,
                         nombre;
                        if (posAlmacenAbierto == -1) {
                            System.out.println("No hay ningún almacen abierto");
                        } else {
                            System.out.println("Introduce el codigo del producto");
                            codProd = sc.next();
                            datosProducto.setCodProd(codProd);
                            System.out.println("Introduce el nombre del producto");
                            codProd = sc.next();
                            datosProducto.setCodProd(codProd);
                            System.out.println("Introduce el precio del producto");
                            codProd = sc.next();
                            datosProducto.setCodProd(codProd);
                            System.out.println("Introduce el cantidad del producto");
                            codProd = sc.next();
                            datosProducto.setCodProd(codProd);
                            System.out.println("Introduce el fecha del producto");
                            codProd = sc.next();
                            datosProducto.setCodProd(codProd);
                            if (!gestStub.AnadirProducto(posAlmacenAbierto, datosProducto)) {
                                System.out.println("No se pudo insertar el producto de forma correcta");
                            } else {
                                System.out.println("El producto" + datosProducto.getNombreProd() + " ha sido insertado correctamente");
                            }
                        }
                        break;

                    case 7:

                        int cantidadProd;
                        float precioProd;
                        int posicion;
                        String opt;
                        if (posAlmacenAbierto == -1) {
                            System.out.println("No hay ningún almacen abierto");
                        } else {
                            System.out.println("Introduce el codigo del producto: ");
                            codProducto = sc.next();
                            posicion = gestStub.BuscaProducto(posAlmacenAbierto, codProducto);
                            if (posicion == -1) {
                                System.out.println("El codigo del producto no existe en el almacen abierto.");
                            } else {
                                datosProducto = gestStub.ObtenerProducto(posAlmacenAbierto, posicion);
                                System.out.println("Nombre: " + datosProducto.getNombreProd() + ""
                                        + ". ¿Quieres modificar el nombre del producto?(s/n)");
                                opt = sc.next();
                                if (opt.equals("s") || opt.equals("S")) {
                                    System.out.println("Introduce el nuevo nombre del producto: ");
                                    nombreProd = sc.next();
                                    datosProducto.setNombreProd(nombreProd);
                                }

                                System.out.println("Cantidad: " + datosProducto.getCantidad() + ""
                                        + ". ¿Quieres modificar la cantidad del producto?(s/n)");
                                opt = sc.next();
                                if (opt.equals("s") || opt.equals("S")) {
                                    System.out.println("Introduce la cantidad actual del producto: ");
                                    cantidadProd = sc.nextInt();
                                    datosProducto.setCantidad(cantidadProd);
                                }

                                System.out.println("Precio: " + datosProducto.getPrecio() + ""
                                        + ". ¿Quieres modificar el precio del producto?(s/n)");
                                opt = sc.next();
                                if (opt.equals("s") || opt.equals("S")) {
                                    System.out.println("Introduce el nuevo nombre del producto: ");
                                    precioProd = sc.nextFloat();
                                    datosProducto.setPrecio(precioProd);
                                }

                                System.out.println("Fecha caducidad: " + datosProducto.getCaducidad() + ""
                                        + ". ¿Quieres modificar la fecha caducidad del producto?(s/n)");
                                opt = sc.next();
                                if (opt.equals("s") || opt.equals("S")) {
                                    System.out.println("Introduce la nueva fecha de caducidad del producto: ");
                                    fechaProd = sc.next();
                                    datosProducto.setCaducidad(fechaProd);
                                }

                                System.out.println("Descripción: " + datosProducto.getDescripcion() + ""
                                        + ". ¿Quieres modificar la descripcion del producto?(s/n)");
                                opt = sc.next();
                                if (opt.equals("s") || opt.equals("S")) {
                                    System.out.println("Introduce la nueva descripcion del producto: ");
                                    descripcionProd = sc.next();
                                    datosProducto.setDescripcion(descripcionProd);
                                }
                            }
                            gestStub.ActualizarProducto(posicion, datosProducto);
                        }

                        break;

                    case 8:
                        if (posAlmacenAbierto == -1) {
                            System.out.println("No hay ningún almacen abierto");
                        } else {
                            System.out.println("Introduce el nombre del codigo del producto: ");
                            codProducto = sc.next();
                            posicion = gestStub.BuscaProducto(posAlmacenAbierto, codProducto);
                            if (posicion == -1) {
                                System.out.println("El codigo del producto no existe en el almacen abierto.");
                            } else {
                                datosProducto = gestStub.ObtenerProducto(posAlmacenAbierto, posicion);
                                System.out.println("CODIGO\t\tNOMBRE\t\tPRECIO\t\tCANTIDAD\t\tFECHA CADUCIDAD");
                                 System.out.println("" + datosProducto.toString());
                            }
                        }
                        break;

                    case 9:
                        String opti;
                        if (posAlmacenAbierto == -1) {
                            System.out.println("No hay ningún almacen abierto");
                        } else {
                            System.out.println("Introduce el nombre del codigo del producto: ");
                            codProducto = sc.next();
                            posicion = gestStub.BuscaProducto(posAlmacenAbierto, codProducto);
                            if (posicion == -1) {
                                System.out.println("El codigo del producto no existe en el almacen abierto.");
                            } else {
                                datosProducto = gestStub.ObtenerProducto(posAlmacenAbierto, posicion);
                                System.out.println("¿Quiere eliminar este producto?\n" + datosProducto.toString());
                                opti = sc.next();
                                if (opti.equals("s") || opti.equals("S")) {
                                    if (gestStub.EliminarProducto(posicion, datosProducto.getCodProd())) {
                                        System.out.println("Producto eliminado con exito");
                                    } else {
                                        System.out.println("Producto no pudo eliminarse con exito");
                                    }
                                }
                            }

                        }
                        break;

                }

            } while (Opcion != 0);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
