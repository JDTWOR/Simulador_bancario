package uniandes.cupi2.simuladorBancario.mundo;

import java.util.ArrayList;

public abstract class CuentaBancaria {
    // Lista de descripciones de transacciones
    protected ArrayList<String> transacciones;
    // Lista de meses correspondientes a cada transacción
    protected ArrayList<Integer> mesesTransacciones;

    public CuentaBancaria() {
        transacciones = new ArrayList<>();
        mesesTransacciones = new ArrayList<>();
    }

    /**
     * Registra una transacción en la cuenta.
     * @param descripcion Descripción de la transacción.
     * @param mes Mes en el que se realiza la transacción.
     */
    public void registrarTransaccion(String descripcion, int mes) {
        transacciones.add(descripcion);
        mesesTransacciones.add(mes);
    }

    /**
     * Devuelve un resumen de las transacciones realizadas en el mes dado.
     * @param mes Mes a consultar.
     * @return Resumen de transacciones del mes.
     */
    public String resumenTransaccionesMes(int mes) {
        StringBuilder resumen = new StringBuilder();
        for (int i = 0; i < transacciones.size(); i++) {
            if (mesesTransacciones.get(i) == mes) {
                resumen.append("- ").append(transacciones.get(i)).append("\n");
            }
        }
        if (resumen.length() == 0) {
            return "No hay transacciones registradas para este mes.";
        }
        return resumen.toString();
    }
}
