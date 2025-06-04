package Modelo;


import java.util.ArrayList;

public class Visitante {

    //Atributos de la clase visitante
    public boolean discapacidad;
    public int cantEntradasNormales;
    public int cantEntradasEventos;
    public int cantidadTotalEntradas;
    public double totalPagar;
    public double descuentoAplicado;
    public String eventoAsistido;

    //Constructores de la clase Visitante
    public Visitante() {
    }

    public Visitante(boolean discapacidad, int cantEntradasNormales, int cantEntradasEventos, String eventoAsistido) {
        this.discapacidad = discapacidad;
        this.cantEntradasNormales = cantEntradasNormales;
        this.cantEntradasEventos = cantEntradasEventos;
        this.cantidadTotalEntradas = cantEntradasNormales + cantEntradasEventos;
        this.eventoAsistido = eventoAsistido;
        // totalPagar, tipoEntrada, descuentoAplicado se pueden calcular luego
    }

    //Metodo para agregar los datos del visitante, estos automaticamente se van a ingresar en una ArrayList para la facturacion
    public void insertarVisitante(ArrayList<Visitante> acumulacionVisitantes, boolean discapacidad, int cantEntradasNormales, int cantEntradasEventos, String eventoAsistido) {
        acumulacionVisitantes.add(new Visitante(discapacidad, cantEntradasNormales, cantEntradasEventos, eventoAsistido));
    }
}