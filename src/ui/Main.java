package ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }


                    List<int[]> list = encontrarParesConSuma(numeros, objetivo);

                    for (int[] par : list) {
                        System.out.println("(" + par[0] + ", " + par[1] + ")");
                    }
                    break;

                case 3:
                    System.out.println("Chao");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
        if (s == null){
            return true;
        }

        PilaGenerica<Character> pila = new PilaGenerica<>(s.length());

        for (char c: s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                pila.Push(c);
            }
            else if (c == ')' || c == ']' || c == '}'){
                if (pila.getTop() == 0) return false;
                char open = pila.Pop();

                boolean coincide = (open == '(' && c == ')') || (open == '[' && c == ']') || (open == '{' && c == '}');
                if (!coincide) return false;
            }
        }

        return pila.getTop() == 0;

    }

    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public List<int[]> encontrarParesConSuma(int[] numeros, int objetivo) throws Exception {
        List<int[]> resultado = new ArrayList<>();

        if (numeros == null || numeros.length == 0) return resultado;
        TablasHash tabla = new TablasHash(numeros.length);

        HashSet<String> mostrados = new HashSet<>();

        for (int x : numeros) {
            int complemento = objetivo - x;

            if (tabla.search(complemento, complemento)) {
                int menor = Math.min(x, complemento);
                int mayor = Math.max(x, complemento);
                String key = menor + ":" + mayor;
                if (!mostrados.contains(key)) {
                    mostrados.add(key);
                    resultado.add(new int[] { menor, mayor });
                }
            }

            tabla.insert(x, x);
        }

        return resultado;
    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
