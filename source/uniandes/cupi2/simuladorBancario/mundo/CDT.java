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
 * Clase que representa un CDT.
 */
public class CDT extends CuentaBancaria
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Valor inicial del CDT.
     */
    private double valorInvertido;

    /**
     * Interés mensual que del CDT
     */
    private double interesMensual;

    /**
     * Mes de apertura del CDT.
     */
    private int mesApertura;

    /**
     * Historial de saldos por mes (índice = mes-1).
     */
    private ArrayList<Double> historialSaldos;

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicializa el CDT. <br>
     * <b>post: </b> El valor invertido, el interés mensual y la fecha se inicializaron en 0.
     */
    public CDT( )
    {
        super();
        valorInvertido = 0;
        interesMensual = 0;
        mesApertura = 0;
        historialSaldos = new ArrayList<>();
        historialSaldos.add(0.0); // mes 1
    }

    /**
     * Retorna el interés que paga el banco mensualmente por este CDT.
     * @return Interés mensual del CDT.
     */
    public double darInteresMensual( )
    {
        return interesMensual;
    }

    /**
     * Inicia una inversión en un CDT .<br>
     * <b>post: </b> Se cambian los valores del CDT, con los valores recibidos. <br>
     * @param pMontoInvertido Monto de dinero que se va a invertir en el CDT. pMontoInvertido > 0.
     * @param pInteresMensual Interés mensual que va a ganar el CDT. pInteresMensual > 0.
     * @param pMes Mes de apertura del CDT. pMes > 0.
     */
    public void invertir( double pMontoInvertido, double pInteresMensual, int pMes )
    {
        valorInvertido = pMontoInvertido;
        interesMensual = pInteresMensual;
        mesApertura = pMes;
        registrarTransaccion("Apertura CDT: $" + pMontoInvertido + ", Interés mensual: " + (pInteresMensual * 100) + "%", pMes);
        registrarSaldoMensual(pMes, pMes);
    }

    /**
     * Calcula el valor presente de la inversión teniendo en cuenta el interés de la cuenta. <br>
     * @param pMesActual Mes actual del simulador. pMesActual > 0.
     * @return Valor presente del dinero invertido en CDT.
     */
    public double calcularValorPresente( int pMesActual )
    {
        int mesesTranscurridos = pMesActual - mesApertura;
        return ( double ) ( valorInvertido + ( mesesTranscurridos * interesMensual * valorInvertido ) );
    }

    /**
     * Cierra el CDT y retorna el valor invertido más los intereses. <br>
     * <b>post: </b> Se retornó el rendimiento del CDT, y se reinició sus atributos a 0.
     * @param pMesActual Mes de cierre para calcular el rendimiento del CDT.
     * @return Valor de cierre del CDT.
     */
    public double cerrar( int pMesActual )
    {
        double valorCierre = calcularValorPresente( pMesActual );
        registrarTransaccion("Cierre CDT: $" + valorCierre, pMesActual);
        registrarSaldoMensual(pMesActual, pMesActual);
        valorInvertido = 0;
        interesMensual = 0;
        mesApertura = 0;
        return valorCierre;
    }

    /**
     * Registra el saldo del mes correspondiente en el historial.
     * @param mes Mes a registrar (1-indexado).
     * @param mesActual Mes actual de la simulación.
     */
    public void registrarSaldoMensual(int mes, int mesActual) {
        double valor = calcularValorPresente(mes);
        while (historialSaldos.size() < mes) {
            historialSaldos.add(valor);
        }
        historialSaldos.set(mes-1, valor);
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
        int meses = mesFin - mesInicio + 1;
        for (int i = 0; i < meses; i++) {
            int mes = mesInicio + i;
            suma += calcularValorPresente(mes);
        }
        return suma / meses;
    }
}