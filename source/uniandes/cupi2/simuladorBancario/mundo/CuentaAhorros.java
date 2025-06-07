/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n1_simuladorBancario
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.simuladorBancario.mundo;

import java.util.ArrayList;

/**
 * Clase que representa la cuenta de ahorro de un cliente.
 */
public class CuentaAhorros extends CuentaBancaria
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Saldo actual de la cuenta de ahorro.
     */
    private double saldo;

    /**
     * Interés mensual que paga la cuenta de ahorro.
     */
    private double interesMensual;

    /**
     * Historial de saldos por mes (índice = mes-1).
     */
    private ArrayList<Double> historialSaldos;

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicializa la cuenta de ahorro con el interés mensual que paga el banco. <br>
     * <b>post: </b> Se inicializó el saldo en 0 y el interés mensual en 0.006.
     */
    public CuentaAhorros( )
    {
        super();
        saldo = 0;
        interesMensual = 0.006;
        historialSaldos = new ArrayList<>();
        historialSaldos.add(saldo); // mes 1
    }

    /**
     * Retorna el saldo del cliente. <br>
     * @return Saldo de la cuenta de ahorros.
     */
    public double darSaldo( )
    {
        return saldo;
    }

    /**
     * Retorna el interés mensual. <br>
     * @return Interés mensual de la cuenta de ahorros.
     */
    public double darInteresMensual( )
    {
        return interesMensual;
    }

    /**
     * Consigna un monto de dinero en la cuenta del cliente. <br>
     * <b>post: </b> El saldo se incrementó en el monto de dinero ingresado. <br>
     * @param pMonto Monto de dinero a consignar en la cuenta. pMonto > 0.
     * @param mes Mes actual de la simulación.
     */
    public void consignarMonto( double pMonto, int mes )
    {
        saldo = saldo + pMonto;
        registrarTransaccion("Consignación: $" + pMonto, mes);
        registrarSaldoMensual(mes);
    }

    /**
     * Retira un monto de dinero de la cuenta de ahorros. <br>
     * <b>post: </b> El saldo se redujo en el valor dado.
     * @param pMonto Monto de dinero a retirar de la cuenta de ahorros. pMonto > 0.
     * @param mes Mes actual de la simulación.
     */
    public void retirarMonto( double pMonto, int mes )
    {
        saldo = saldo - pMonto;
        registrarTransaccion("Retiro: $" + pMonto, mes);
        registrarSaldoMensual(mes);
    }

    /**
     * Actualiza el saldo de la cuenta de ahorros sumándole los intereses (ha pasado un mes). <br>
     * <b>post: </b> El saldo actual se actualizó aplicando el porcentaje de interés mensual respectivo.
     * @param mes Mes actual de la simulación.
     */
    public void actualizarSaldoPorPasoMes( int mes )
    {
        double interes = saldo * interesMensual;
        saldo = saldo + interes;
        registrarTransaccion("Interés mensual: $" + interes, mes);
        registrarSaldoMensual(mes);
    }

    /**
     * Registra el saldo del mes correspondiente en el historial.
     * @param mes Mes actual (1-indexado).
     */
    public void registrarSaldoMensual(int mes) {
        // Asegura que la lista tenga tamaño suficiente
        while (historialSaldos.size() < mes) {
            historialSaldos.add(saldo);
        }
        historialSaldos.set(mes-1, saldo);
    }

    /**
     * Calcula el saldo promedio entre dos meses (inclusive) usando la fórmula matemática.
     * @param mesInicio Mes de inicio (1-indexado).
     * @param mesFin Mes de fin (1-indexado).
     * @return Saldo promedio en el intervalo, o -1 si el rango es inválido.
     */
    public double calcularSaldoPromedio(int mesInicio, int mesFin) {
        if (mesInicio < 1 || mesFin < mesInicio) {
            return -1;
        }
        double suma = 0;
        double saldoMes = saldo;
        int meses = mesFin - mesInicio + 1;
        // Simular el saldo de cada mes futuro
        for (int i = 0; i < meses; i++) {
            suma += saldoMes;
            saldoMes = saldoMes * (1 + interesMensual);
        }
        return suma / meses;
    }
}