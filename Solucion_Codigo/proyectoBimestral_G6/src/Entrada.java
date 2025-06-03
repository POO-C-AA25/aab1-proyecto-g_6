
import java.time.DayOfWeek; //Esta libreria sirve para verificar los dias de la semana
import java.time.LocalDate; //Extrae la fecha (Solo dias no horas, esto se cambio con respecto al UML)
import java.time.LocalDateTime;//Extrae la hora y fecha
import java.time.LocalTime; //Pues solo extrae la hora
import java.util.ArrayList;

public class Entrada {

    //Atributos de la clase entrada
    public String categoria;
    public double precio = 2.5;
    public LocalDateTime fechaHora;
    public Visitante visitante;

    //Constructores de la clase Entrada
    public Entrada() {
    }

    public Entrada(String categoria, double precio, LocalDateTime fechaHora) {
        this.categoria = categoria;
        this.precio = precio = 2.5;
        this.fechaHora = fechaHora;
    }

    //Metodos de la clase entrada
    public void gestionVentas(ArrayList<Visitante> acumulacionVisitante) {
        double totalGanancias = 0;

        for (Visitante visitante : acumulacionVisitante) {
            //Se verifica la hora antes de cualquiero procedimiento
            if (this.verificarHora()) {
                this.precio = 7.0;
            }

            //Se comprueba que la cantidad de entradas ingresadas sea mayor que 0
            if (visitante.cantidadTotalEntradas > 0) {
                //Se comprueba en un condicional diferente si el visitante tiene discapacidad
                //ya que este descuento no aplica a entradas de eventos
                if (visitante.discapacidad == true) {

                    //El descuento de las personas con discapacidad es del 50%
                    visitante.descuentoAplicado = (visitante.cantEntradasEventos * this.precio) / 2;
                } else {

                    //Se aplica la multiplicacion para obtener el total a pagar
                    visitante.totalPagar += visitante.cantEntradasEventos * this.precio;
                }

                if (visitante.cantEntradasEventos > 0) {

                    //Se cambia el precio de las entradas 
                    this.precio = 5;

                    //El total a pagar se suma ya que es acumulativo el precio con el de las entradas normales
                    visitante.totalPagar += visitante.cantEntradasEventos * this.precio;
                }

            } else {
                System.out.println("La cantidad de entradas ingresadas por el usuario es 0");
            }

            //Se imprime la factura al usuario
            System.out.print(" ================= Factura ================= ");
            System.out.println("Total a pagar: " + visitante.totalPagar
                    + "\nTotal descuentos por discapacidad: " + visitante.discapacidad
                    + "\nTotal de entradas normales ventidas: " + visitante.cantEntradasNormales
                    + "\nTotal de entradas para eventos vendidas: " + visitante.cantEntradasEventos);

        }

    }

    // Método para verificar si la entrada se encuentra en el rango obligatorio de los eventos
    public boolean verificarHora() {
        //En las instrucciones estaba que trabajemos con el 2024
        //Se declaran las fechas limites
        LocalDate inicio = LocalDate.of(2024, 8, 30);
        LocalDate fin = LocalDate.of(2024, 9, 8);

        //Se obtiene la hora del dispositivo
        LocalDate fecha = fechaHora.toLocalDate();

        //Lo mismo pero con la hora
        LocalTime hora = fechaHora.toLocalTime();
        DayOfWeek diaSemana = fecha.getDayOfWeek();

        // Se verifica si el día es jueves, viernes o sábado
        boolean esDiaValido = diaSemana == DayOfWeek.THURSDAY
                || diaSemana == DayOfWeek.FRIDAY
                || diaSemana == DayOfWeek.SATURDAY;

        // Se verifica si la fecha está dentro del rango de la feria y si la hora es posterior a las 17:00
        if (!fecha.isBefore(inicio) && !fecha.isAfter(fin) && esDiaValido) {
            if (!hora.isBefore(LocalTime.of(17, 0))) {
                return true;
            }
        }

        //Verificacion para entradas entre medianoche y las 2:00 a.m. del día siguiente
        //Es necesario hacerlo en dos metodos diferentes ya que se maneja por dias entonces de la 12 a las 2am son difenrentes dias 
        LocalDate diaAnterior = fecha.minusDays(1);
        DayOfWeek diaSemanaAnterior = diaAnterior.getDayOfWeek();

        boolean esDiaAnteriorValido = diaSemanaAnterior == DayOfWeek.THURSDAY
                || diaSemanaAnterior == DayOfWeek.FRIDAY
                || diaSemanaAnterior == DayOfWeek.SATURDAY;

        //Se verifica ambos valores
        if (!diaAnterior.isBefore(inicio) && !diaAnterior.isAfter(fin) && esDiaAnteriorValido) {
            if (hora.isBefore(LocalTime.of(2, 0))) {
                return true;
            }
        }

        return false;
    }

}