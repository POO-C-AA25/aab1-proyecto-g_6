package Vista;


//Librerias para el manejo de archivos
//No vi la necesidad de guardar los objetos entonces decidi no ocupar serializacion de objetos
import Modelo.Visitante;
import Modelo.Evento;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Estadistica {

    //Atributos de la clase Estadistica
    public int totalVisitantes;
    public int totalPersonasDiscapacitadas;
    public double ingresosTotales;
    public double descuentosTotales;
    public int[] asistenciaPorDia;
    public Evento eventoMayorAsistencia;
    public Evento eventoMenorAsistencia;

    //Constructores de la clase Estadistica
    public Estadistica() {
    }

    public Estadistica(int totalVisitantes, int totalPersonasDiscapacitadas, double ingresosTotales, double descuentosTotales, int[] asistenciaPorDia, Evento eventoMayorAsistencia, Evento eventoMenorAsistencia) {
        this.totalVisitantes = totalVisitantes;
        this.totalPersonasDiscapacitadas = totalPersonasDiscapacitadas;
        this.ingresosTotales = ingresosTotales;
        this.descuentosTotales = descuentosTotales;
        this.asistenciaPorDia = asistenciaPorDia;
        this.eventoMayorAsistencia = eventoMayorAsistencia;
        this.eventoMenorAsistencia = eventoMenorAsistencia;
    }

    public void calcularEstadisticas(ArrayList<Visitante> acumulacionVisitantes, ArrayList<Evento> eventosPresentables) {
        this.totalVisitantes = 0;
        this.totalPersonasDiscapacitadas = 0;
        this.ingresosTotales = 0;
        this.descuentosTotales = 0;
        for (int i = 0; i < this.asistenciaPorDia.length; i++) {
            this.asistenciaPorDia[i] = 0;
        }

        for (Visitante v : acumulacionVisitantes) {
            this.totalVisitantes += v.cantidadTotalEntradas;
            if (v.discapacidad) {
                this.totalPersonasDiscapacitadas++;
                this.descuentosTotales += v.descuentoAplicado;
            }
            this.ingresosTotales += v.totalPagar;
        }

        for (Evento e : eventosPresentables) {
            int dia = Integer.parseInt(e.diaPresentacion);
            int idx = dia - 30;
            if (idx >= 0 && idx < asistenciaPorDia.length) {
                asistenciaPorDia[idx] += e.cantidadVisitantes;
            }
        }

        //Se comprueba si esta vacia con ayuda de .isEmpty que sirve pues para ver si esta vacia
        if (!eventosPresentables.isEmpty()) {
            this.eventoMayorAsistencia = eventosPresentables.get(0);
            this.eventoMenorAsistencia = eventosPresentables.get(0);

            for (Evento e : eventosPresentables) {
                if (e.cantidadVisitantes > this.eventoMayorAsistencia.cantidadVisitantes) {
                    this.eventoMayorAsistencia = e;
                }
                if (e.cantidadVisitantes < this.eventoMenorAsistencia.cantidadVisitantes) {
                    this.eventoMenorAsistencia = e;
                }
            }
        }
    }

    //Metodo para imprimir la estadistica directamente en la terminal
    public void imprimirEstadistica(ArrayList<Evento> eventosPresentables, int[] asistenciaPorDia) {
        System.out.println("===== Estadísticas de la Feria =====");
        System.out.println("Total visitantes: " + totalVisitantes);
        System.out.println("Personas con discapacidad: " + totalPersonasDiscapacitadas);
        System.out.println("Ingresos totales: $" + ingresosTotales);
        System.out.println("Descuentos totales: $" + descuentosTotales);

        // Actualiza asistencia diaria sumando visitantes por evento
        for (Evento evento : eventosPresentables) {
            int diaEvento = Integer.parseInt(evento.diaPresentacion); // Día del evento
            int indiceDia = diaEvento - 30; // Índice en asistenciaPorDia (día 30 es índice 0)
            if (indiceDia >= 0 && indiceDia < asistenciaPorDia.length) {
                asistenciaPorDia[indiceDia] += evento.cantidadVisitantes;
            }
        }

        //Mostrar asistencia por día
        System.out.println("Asistencia por día:");
        for (int i = 0; i < asistenciaPorDia.length; i++) {
            int dia = 30 + i;
            System.out.println("Día " + dia + ": " + asistenciaPorDia[i] + " visitantes");
        }

        //Mostrar evento con más asistencia
        if (eventoMayorAsistencia != null) {
            System.out.println("Evento con mayor asistencia: " + eventoMayorAsistencia.nombreArtista + " (" + eventoMayorAsistencia.cantidadVisitantes + " visitantes)");
        }

        //Mostrar evento con menor asistencia
        if (eventoMenorAsistencia != null) {
            System.out.println("Evento con menor asistencia: " + eventoMenorAsistencia.nombreArtista + " (" + eventoMenorAsistencia.cantidadVisitantes + " visitantes)");
        }
    }

    //Metodo para guardar la estadistica en un archivo CSV
    public void guardarEstadistica(String nombreArchivo, ArrayList<Evento> eventosPresentables, int[] asistenciaPorDia) {
        try (FileWriter escritor = new FileWriter(nombreArchivo)) {

            escritor.write("=== Estadísticas Generales ===\n");
            escritor.write("Total visitantes," + totalVisitantes + "\n");
            escritor.write("Personas con discapacidad," + totalPersonasDiscapacitadas + "\n");
            escritor.write("Ingresos totales," + ingresosTotales + "\n");
            escritor.write("Descuentos totales," + descuentosTotales + "\n\n");

            escritor.write("=== Asistencia por Día ===\n");
            for (int i = 0; i < asistenciaPorDia.length; i++) {
                int dia = 30 + i;
                escritor.write("Día " + dia + "," + asistenciaPorDia[i] + "\n");
            }

            escritor.write("\n=== Eventos ===\n");
            for (Evento evento : eventosPresentables) {
                escritor.write(evento.nombreArtista + "," + evento.diaPresentacion + "," + evento.cantidadVisitantes + "\n");
            }

            escritor.write("\n=== Eventos con Mayor y Menor Asistencia ===\n");
            if (eventoMayorAsistencia != null) {
                escritor.write("Mayor asistencia," + eventoMayorAsistencia.nombreArtista + "," + eventoMayorAsistencia.cantidadVisitantes + "\n");
            }
            if (eventoMenorAsistencia != null) {
                escritor.write("Menor asistencia," + eventoMenorAsistencia.nombreArtista + "," + eventoMenorAsistencia.cantidadVisitantes + "\n");
            }

            System.out.println("Estadísticas guardadas correctamente en " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al guardar el archivo CSV");
        }
    }

}