public class Traslacion {
    public double[][] trasladar (int dx, int dy, double[][] matriz) {
        int[][] matrizTransformacion = {{1, 0, dx}, {0, 1, dy}, {0, 0, 1}};

        int columnas = matriz[0].length;
        double[][] resultado = new double[3][columnas];

        for (int i = 0; i < matrizTransformacion.length; i++) {
            for (int j = 0; j < columnas; j++) {
                for (int k = 0; k < matrizTransformacion[0].length; k++) {
                    resultado[i][j] += matrizTransformacion[i][k] * matriz[k][j];
                }
            }
        }
        return resultado;
    }
}
