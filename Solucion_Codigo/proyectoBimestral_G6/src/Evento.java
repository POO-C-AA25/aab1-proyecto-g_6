
import java.util.ArrayList;

public class Evento {

    //Atributos de la clase Evento
    public String nombreArtista;
    public String diaPresentacion;
    public int cantidadVisitantes;

    //Constructores de la clase Evento
    public Evento() {
    }

    public Evento(String nombreArtista, String diaPresentacion) {
        this.nombreArtista = nombreArtista;
        this.diaPresentacion = diaPresentacion;
    }

    public void insertarEvento(ArrayList<Evento> eventosPresentables, String nombreArtista, String diaPresentacion) {
        eventosPresentables.add(new Evento(nombreArtista, diaPresentacion));
    }

    public static void calcularVisitantes(ArrayList<Visitante> visitantes, ArrayList<Evento> eventos) {
        //Se verifica que cada evento tenga como visitantes 0
        for (Evento e : eventos) {
            e.cantidadVisitantes = 0;
        }

        //Recorremos la lista de visitantes
        for (Visitante v : visitantes) {

            //Se comprueba si es necesario trabajar con el visitante o no
            if (v.eventoAsistido != null && v.cantEntradasEventos > 0) {

                //Se recorre la listra de eventos para buscar si coincide
                for (Evento e : eventos) {

                    //Se comprueban los Strings
                    if (e.nombreArtista.equalsIgnoreCase(v.eventoAsistido)) {

                        //Se suma la cantidad de entradas de eventos del visitante a la cantidad de visitantes
                        e.cantidadVisitantes += v.cantEntradasEventos;
                    }
                }
            }
        }
    }
}