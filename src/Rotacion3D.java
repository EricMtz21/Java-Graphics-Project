public class Rotacion3D {
    public double[][] rotar (double angle, double[][] matriz) {
        double[][] matrizTransformacion = {
                {1, 0, 0, 0},
                {0, Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)), 0},
                {0, - Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}
        };

        int columnas = matriz[0].length;
        double[][] resultado = new double[4][columnas];

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
