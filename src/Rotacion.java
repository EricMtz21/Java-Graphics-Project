public class Rotacion {
    public double[][] rotarCentro (double angle, double[][] matriz, int cx, int cy) {
        double[][] matrizTransformacion = {
                {Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), cx - cx * Math.cos(Math.toRadians(angle)) + cy * Math.sin(Math.toRadians(angle))},
                {Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), cy - cx * Math.sin(Math.toRadians(angle)) - cy * Math.cos(Math.toRadians(angle))},
                {0, 0, 1}};

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
