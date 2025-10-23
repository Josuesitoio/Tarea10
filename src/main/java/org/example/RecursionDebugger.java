package org.example;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * VisualizadorPermutaciones.
 * Objetivo: Visualizar paso a paso la ejecución recursiva al generar permutaciones.
 * Toda la salida se imprime en la consola.
 */
public class RecursionDebugger {

    public static void main(String[] args) {
        String entrada = args.length > 0 ? args[0] : "abc";
        char[] arreglo = entrada.toCharArray();

        System.out.println("=== Visualizador de Permutaciones ===");
        System.out.println("Entrada: \"" + entrada + "\"\n");

        // Contador atómico para IDs únicos por llamada
        AtomicInteger contadorId = new AtomicInteger(0);

        // Pila para simular el call stack y visualizarlo
        Stack<String> pilaLlamadas = new Stack<>();

        // Iniciar la recursión
        permutar(arreglo, 0, arreglo.length - 1, pilaLlamadas, contadorId, "");
    }

    /**
     * Genera permutaciones usando el enfoque de intercambio "in-place" (sobre el mismo arreglo).
     *
     * @param arreglo      Arreglo de caracteres que se está permutando.
     * @param l            Índice izquierdo (representa el nivel actual de recursión).
     * @param r            Índice derecho (el final del arreglo).
     * @param pilaLlamadas Pila usada para imprimir el estado de las llamadas recursivas.
     * @param contadorId   Contador atómico para asignar un ID a cada llamada.
     * @param ruta         String simple para seguir el árbol de decisiones (qué intercambios se hicieron).
     */
    static void permutar(char[] arreglo, int l, int r, Stack<String> pilaLlamadas, AtomicInteger contadorId, String ruta) {

        int idLlamada = contadorId.incrementAndGet();
        int profundidad = pilaLlamadas.size();
        String estadoArreglo = new String(arreglo);

        // Descripción única de esta llamada
        String descripcion = "Llamada#" + idLlamada + " l=" + l + " r=" + r + " arr=\"" + estadoArreglo + "\" ruta=" + ruta;

        // Añadir (push) la llamada actual a la pila para la visualización
        pilaLlamadas.push(descripcion);

        // Imprimir estado de entrada y la pila actual
        imprimirEntrada(idLlamada, l, r, arreglo, ruta, profundidad, pilaLlamadas);

        // --- Caso Base ---
        // Si el índice izquierdo (nivel) alcanza el derecho (final), hemos completado una permutación.
        if (l == r) {
            System.out.println(obtenerIndentacion(profundidad) + "--> CASO BASE: permutacion = \"" + new String(arreglo) + "\"\n");

            // Quitar (pop) de la pila al regresar
            pilaLlamadas.pop();
            System.out.println(obtenerIndentacion(profundidad) + "Regresando de " + descripcion + "\n");
            return;
        }

        // --- Paso Recursivo ---
        // Iterar sobre las posibles elecciones (intercambiar arr[l] con arr[i])
        for (int i = l; i <= r; i++) {

            System.out.println(obtenerIndentacion(profundidad) + "Eleccion: intercambiar indices " + l + " y " + i + " ( '" + arreglo[l] + "' <-> '" + arreglo[i] + "' )");

            // 1. Elegir (Hacer el intercambio)
            intercambiar(arreglo, l, i);
            System.out.println(obtenerIndentacion(profundidad) + "Despues de intercambiar -> arr = \"" + new String(arreglo) + "\"\n");

            // 2. Explorar (Llamar recursivamente al siguiente nivel)
            // Se usa "<->" como símbolo normal para la ruta
            permutar(arreglo, l + 1, r, pilaLlamadas, contadorId, ruta + "[" + l + "<->" + i + "]");

            // 3. Deshacer (Backtrack: deshacer el intercambio para probar la siguiente elección)
            intercambiar(arreglo, l, i);
            System.out.println(obtenerIndentacion(profundidad) + "Backtrack (deshacer intercambio) -> arr restaurado = \"" + new String(arreglo) + "\"\n");
        }

        // Quitar (pop) de la pila al regresar del bucle
        pilaLlamadas.pop();
        System.out.println(obtenerIndentacion(profundidad) + "Regresando de " + descripcion + "\n");
    }

    /**
     * Imprime los detalles de entrada de una llamada recursiva y el estado actual de la pila.
     */
    static void imprimirEntrada(int idLlamada, int l, int r, char[] arreglo, String ruta, int profundidad, Stack<String> pilaLlamadas) {
        System.out.println(obtenerIndentacion(profundidad) + "Entrando a Llamada#" + idLlamada + ": l=" + l + " r=" + r + " arr=\"" + new String(arreglo) + "\" ruta=" + ruta);
        System.out.println(obtenerIndentacion(profundidad) + "Estado de la pila (cima -> fondo):");

        // Imprimir la pila de arriba hacia abajo
        for (int i = pilaLlamadas.size() - 1; i >= 0; i--) {
            System.out.println(obtenerIndentacion(profundidad) + "  " + pilaLlamadas.get(i));
        }
        System.out.println(""); // Línea en blanco para separar
    }

    /**
     * Función de utilidad para intercambiar dos caracteres en un arreglo.
     */
    static void intercambiar(char[] a, int i, int j) {
        char t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * Función de utilidad para generar la indentación según la profundidad.
     */
    static String obtenerIndentacion(int profundidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < profundidad; i++) {
            sb.append("  "); // Dos espacios por nivel
        }
        return sb.toString();
    }
}