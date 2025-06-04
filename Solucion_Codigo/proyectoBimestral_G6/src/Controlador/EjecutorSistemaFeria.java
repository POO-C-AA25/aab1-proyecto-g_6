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
            ArrayList<Entrada> acumulacionEntradas = new ArrayList<>();
            ArrayList<Visitante> acumulacionVisitantes = new ArrayList<>();
            ArrayList<Evento> eventos = new ArrayList<>();
            //Se agregan los eventos por defecto
            
            eventos.add(new Evento("Tierra canela" , "30"));
            eventos.add(new Evento("Don Medardo y sus players" , "29"));
            Estadistica estadistica = new Estadistica();
            estadistica.asistenciaPorDia = new int[10]; // Del 30 al 8 

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
                teclado.nextLine(); // Limpieza de buffer

                switch (opcion) {
                    
                    case 1:
                        System.out.print("¿Tiene discapacidad? (true/false): ");
                        boolean discapacidad = teclado.nextBoolean();
                        System.out.print("Cantidad de entradas normales: ");
                        int normales = teclado.nextInt();
                        System.out.print("Cantidad de entradas para eventos: ");
                        int eventosEntradas = teclado.nextInt();
                        teclado.nextLine(); // limpiar buffer
                        System.out.print("Nombre del evento asistido (o vacío si no aplica): ");
                        String eventoNombre = teclado.nextLine();

                        Visitante visitante = new Visitante(discapacidad, normales, eventosEntradas, eventoNombre);
                        acumulacionVisitantes.add(visitante);

                        entrada.fechaHora = LocalDateTime.now(); // Asignar fecha actual
                        entrada.gestionVentas(acumulacionVisitantes); // Lógica de cobro
                        break;

                    case 2:
                        System.out.print("Nombre del artista: ");
                        String artista = teclado.nextLine();
                        System.out.print("Día de presentación (entre 30 y 8): ");
                        String dia = teclado.nextLine();
                        Evento evento = new Evento(artista, dia);
                        eventos.add(evento);
                        break;

                    case 3:
                        Evento.calcularVisitantes(acumulacionVisitantes, eventos);
                        estadistica.calcularEstadisticas(acumulacionVisitantes, eventos);
                        estadistica.imprimirEstadistica(eventos, estadistica.asistenciaPorDia);
                        break;

                    case 4:
                        System.out.print("Nombre del archivo CSV: ");
                        String archivo = teclado.nextLine();
                        estadistica.guardarEstadistica(archivo, eventos, estadistica.asistenciaPorDia);
                        break;

                    case 5:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 5);
        }
    }