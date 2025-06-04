package Controlador;

//Importaciones de los archivos java, que se encuentran en diferentes paquetes del proyecto
import Modelo.Visitante;
import Modelo.Evento;
import Modelo.Entrada;
import Vista.Estadistica;

import java.time.LocalDateTime; //Extrae la hora y fecha
import java.util.*; //Para el arrayList y el Scanner 
public class EjecutorSistemaFeria {

        public static void main(String[] args) {
            Scanner teclado = new Scanner(System.in);
            //Definicion de las arrayList que vamos a utilizar para el almacenamiento de los datos 
            ArrayList<Entrada> acumulacionEntradas = new ArrayList<>();
            ArrayList<Visitante> acumulacionVisitantes = new ArrayList<>();
            ArrayList<Evento> eventos = new ArrayList<>();
            
            //Se agregan los eventos por defecto
            eventos.add(new Evento("Tierra canela" , "30"));
            eventos.add(new Evento("Don Medardo y sus players" , "8"));
            eventos.add(new Evento("Elefante","6"));
            eventos.add(new Evento("Maximo Escaleras","7"));
            
            //Objeto de estadistica para usar los metodos de la clase estadistica
            Estadistica estadistica = new Estadistica();
            
            //Arreglo para determinar las asistencias por dia, de tamaño 10 ya que la feria dura 10 dias
            estadistica.asistenciaPorDia = new int[10]; // Del 30 al 8 
            
            //Objeto de clase entrada para utilizar los metodos 
            Entrada entrada = new Entrada();

            int opcion = 0;
            do {
                System.out.println("""
                                   ============ MENÚ DEL SISTEMA DE FERIA ============
                                   1. Registrar visitante y generar factura
                                   2. Registrar evento
                                   3. Calcular y mostrar estadísticas
                                   4. Guardar estadísticas en archivo CSV
                                   5. Salir
                                   
                                   Ingresa una opcion: 
                                   """);
                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpieza del Scanner

                switch (opcion) {
                    
                    case 1:
                        //Se solitica en el main la informacion del usuario para gestionar la compra de entradas
                        System.out.print("¿Tiene discapacidad? (true/false): ");
                        boolean discapacidad = teclado.nextBoolean();
                        System.out.print("Cantidad de entradas normales: ");
                        int normales = teclado.nextInt();
                        System.out.print("Cantidad de entradas para eventos: ");
                        int eventosEntradas = teclado.nextInt();
                        teclado.nextLine(); // limpiar Scanner
                        System.out.print("Nombre del evento asistido (vacío si no aplica): ");
                        String eventoNombre = teclado.nextLine();
                        
                        //Se declara el visitante
                        Visitante visitante = new Visitante(discapacidad, normales, eventosEntradas, eventoNombre);
                        
                        //Se agrega el visitante a la lista de visitantes para la estadistica
                        acumulacionVisitantes.add(visitante);

                        entrada.fechaHora = LocalDateTime.now(); // Asignar fecha actual
                        entrada.gestionVentas(acumulacionVisitantes); // Lógica de cobro
                        break;

                    case 2:
                        //Metodo para agregar los eventos
                        System.out.print("Nombre del artista: ");
                        String artista = teclado.nextLine();
                        System.out.print("Día de presentación (entre 30 y 8): ");
                        String dia = teclado.nextLine();
                        
                        //Se declara el evento con los datos 
                        Evento evento = new Evento(artista, dia);
                        
                        eventos.add(evento);
                        break;

                    case 3:
                        //Se invocan los metodos que sirven para calcular las estadisticas
                        Evento.calcularVisitantes(acumulacionVisitantes, eventos);
                        estadistica.calcularEstadisticas(acumulacionVisitantes, eventos);
                        estadistica.imprimirEstadistica(eventos, estadistica.asistenciaPorDia);
                        break;

                    case 4:
                        //Se solicita el nombre del archivo al usuario
                        System.out.print("Nombre del archivo CSV: ");
                        String archivo = teclado.nextLine();
                        
                        //Se invoca el metodo para guardar la estadistica
                        estadistica.guardarEstadistica(archivo, eventos, estadistica.asistenciaPorDia);
                        break;

                    case 5:
                        //Se sale del sistema 
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        //En caso la opcion no sea valida 
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 5);
        }
    }