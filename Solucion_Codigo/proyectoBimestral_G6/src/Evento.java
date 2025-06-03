
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

    //Metodos de la clase Evento
    public void insertarEvento(ArrayList<Evento> eventosPresentables, String nombreArtista, String diaPresentacion) {
        eventosPresentables.add(new Evento(nombreArtista, diaPresentacion));
    }

    public static void calcularVisitantes(ArrayList<Visitante> acumulacionVisitantes, ArrayList<Evento> eventosPresentables) {
        //Se verifica que cada evento tenga como visitantes 0
        for (Evento e : eventosPresentables) {
            e.cantidadVisitantes = 0;
        }

        //Recorremos la lista de visitantes
        for (Visitante visitante : acumulacionVisitantes) {

            //Se comprueba si es necesario trabajar con el visitante o no
            if (visitante.eventoAsistido != null && visitante.cantEntradasEventos > 0) {

                //Se recorre la listra de eventos para buscar si coincide
                for (Evento evento : eventosPresentables) {

                    //Se comprueban los Strings
                    if (evento.nombreArtista.equalsIgnoreCase(visitante.eventoAsistido)) {

                        //Se suma la cantidad de entradas de eventos del visitante a la cantidad de visitantes
                        evento.cantidadVisitantes += visitante.cantEntradasEventos;
                    }
                }
            }
        }
    }
}