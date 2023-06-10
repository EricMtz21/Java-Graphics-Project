import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Proyecto extends JFrame implements Runnable{
    private BufferedImage bufferFondo, bufferPixel;
    private Graphics2D graphics;
    private Thread thread;

    // VARIABLES
    int[] vDireccion = {-2, 2, 5};
    private boolean isRunning = true;
    int xDistance = 1;
    int yDistance = 1;
    double scaleValue = 1.01;
    float timeElapsed = 0;
    int scaleTimer = 1;

    // FIguras
    private double[][] calle = {
            {0, },
            {},
            {},
            {}
    };
    private double[][] cube = {
            {20, 60, 60, 20, 20, 60, 60, 20},
            {40, 40, 40, 40, 70, 70, 70, 70},
            { 0,  0, 60, 60, 0, 0, 60, 60},
            { 1,  1,  1,  1,  1,  1,  1,  1}
    };
    private double[][] cube2 = {
            {120, 70, 70, 120, 120, 70, 70, 120},
            {40, 40, 40, 40, 70, 70, 70, 70},
            { 0,  0, 60, 60, 0, 0, 60, 60},
            { 1,  1,  1,  1,  1,  1,  1,  1}
    };
    private double[][] cube3 = {
            {20, 60, 60, 20, 20, 60, 60, 20},
            {40, 40, 40, 40, 70, 70, 70, 70},
            { 0,  0, 60, 60, 0, 0, 60, 60},
            { 1,  1,  1,  1,  1,  1,  1,  1}
    };
    private double[][] auxCube = { };

    public Proyecto () {
        setSize(800, 800);
        setTitle("Crossy Road");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        bufferFondo = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        bufferPixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) bufferFondo.createGraphics();

        thread = new Thread(this);
        thread.start();
    }

    public static void main (String[] args) {
        new Proyecto().setVisible(true);
    }

    public void run () {
        while (isRunning) {
            try {
                thread.sleep(16);
                timeElapsed += 16;
                repaint();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(graphics);



        g.drawImage(bufferFondo, 0, 0, this);
    }

    private double[][] scale(double[][] coords, double scaleFactor) {
        double[][] result = new double[coords.length][coords[0].length];
        for (int i = 0; i < coords.length; i++) {
            for (int j = 0; j < coords[0].length; j++) {
                result[i][j] = coords[i][j] * scaleFactor;
            }
        }
        return result;
    }
    private void translate(double[][] coords, int tx, int ty, int tz) {
        for (int i = 0; i < coords[0].length; i++) {
            coords[0][i] += tx;
            coords[1][i] += ty;
            coords[2][i] += tz;
        }
    }
    private void rotateX(double[][] coords, double angle) {
        for (int i = 0; i < coords[1].length; i++) {
            double y = coords[1][i];
            double z = coords[2][i];

            coords[1][i] = y * Math.cos(Math.toRadians(angle)) - z * Math.sin(Math.toRadians(angle));
            coords[2][i] = y * Math.sin(Math.toRadians(angle)) + z * Math.cos(Math.toRadians(angle));
        }
    }
    private void rotateY(double[][] coords, double angle) {
        for (int i = 0; i < coords[0].length; i++) {
            double x = coords[0][i];
            double z = coords[2][i];

            coords[0][i] = x * Math.cos(Math.toRadians(angle)) + z * Math.sin(Math.toRadians(angle));
            coords[2][i] = -x * Math.sin(Math.toRadians(angle)) + z * Math.cos(Math.toRadians(angle));
        }
    }
    private void rotateZ(double[][] coords, double angle) {
        for (int i = 0; i < coords[0].length; i++) {
            double x = coords[0][i];
            double y = coords[1][i];

            coords[0][i] = x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
            coords[1][i] = x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));
        }
    }
    private void rotateAroundCenter(double[][] coords, double angle, double centerX, double centerY) {
        for (int i = 0; i < coords[0].length; i++) {
            double x = coords[0][i] - centerX;
            double y = coords[1][i] - centerY;

            double newX = x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
            double newY = x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));

            coords[0][i] = newX + centerX;
            coords[1][i] = newY + centerY;
        }
    }
    public void putPixel(int x, int y, Color pixelColor) {
        bufferPixel.setRGB(0, 0, pixelColor.getRGB());
        graphics.drawImage(bufferPixel, x, y, this);
    }
    public void drawLine (int xStart, int yStart, int xEnd, int yEnd) {
        int xk = xStart, yk = yStart;
        int dx = xEnd - xStart, dy = yEnd - yStart;
        int incX = 1, incY = 1, incE, incNE, pk = 0;

        if (dx < 0) {
            dx = -dx;
            incX = -1;
        }
        if (dy < 0) {
            dy = -dy;
            incY = -1;
        }

        if (Math.abs(dx) > Math.abs(dy)) {
            incE = 2 * dy;
            incNE = 2 * (dy - dx);

            while (xk != xEnd) {
                putPixel(xk, yk, Color.BLACK);
                xk += incX;
                if (2 * (pk + dy) < dx) {
                    pk += incE;
                } else {
                    pk += incNE;
                    yk += incY;
                }
            }
        } else {
            incE = 2 * dx;
            incNE = 2 * (dx - dy);

            while (yk != yEnd) {
                putPixel(xk, yk, Color.BLACK);
                yk += incY;
                if (2 * (pk + dx) < dy) {
                    pk += incE;
                } else {
                    pk += incNE;
                    xk += incX;
                }
            }
        }
    }
    public double[][] transform2D (double[][] coords) {
        Puntos[] puntos3D = new Puntos[8];
        Puntos[] puntos2D = new Puntos[8];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = (-puntos3D[i].getZ()) / vDireccion[2];
            int x = puntos3D[i].getX() + (vDireccion[0] * u) + 120;
            int y = puntos3D[i].getY() + (vDireccion[1] * u) + 320;

            puntos2D[i] = new Puntos(x, y);
        }
        int xA = puntos2D[0].getX(), yA = puntos2D[0].getY();
        int xB = puntos2D[1].getX(), yB = puntos2D[1].getY();
        int xC = puntos2D[2].getX(), yC = puntos2D[2].getY();
        int xD = puntos2D[3].getX(), yD = puntos2D[3].getY();
        int xE = puntos2D[4].getX(), yE = puntos2D[4].getY();
        int xF = puntos2D[5].getX(), yF = puntos2D[5].getY();
        int xG = puntos2D[6].getX(), yG = puntos2D[6].getY();
        int xH = puntos2D[7].getX(), yH = puntos2D[7].getY();

        return new double[][]{{xA, xB, xC, xD, xF, xE, xF, xG, xH}, {yA, yB, yC, yD, yE, yF, yG, yH}};
    }
    public void drawCube (double[][] coords, Color color) {
        Puntos[] puntos3D = new Puntos[8];
        Puntos[] puntos2D = new Puntos[8];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = (-puntos3D[i].getZ()) / vDireccion[2];
            int x = puntos3D[i].getX() + (vDireccion[0] * u) + 120;
            int y = puntos3D[i].getY() + (vDireccion[1] * u) + 320;

            puntos2D[i] = new Puntos(x, y);
        }

        int xA = puntos2D[0].getX(), yA = puntos2D[0].getY();
        int xB = puntos2D[1].getX(), yB = puntos2D[1].getY();
        int xC = puntos2D[2].getX(), yC = puntos2D[2].getY();
        int xD = puntos2D[3].getX(), yD = puntos2D[3].getY();
        int xE = puntos2D[4].getX(), yE = puntos2D[4].getY();
        int xF = puntos2D[5].getX(), yF = puntos2D[5].getY();
        int xG = puntos2D[6].getX(), yG = puntos2D[6].getY();
        int xH = puntos2D[7].getX(), yH = puntos2D[7].getY();

        graphics.setColor(color);
        int[] xPoints1 = {xA, xB, xC, xD};
        int[] yPoints1 = {yA, yB, yC, yD};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xC, yC);
        drawLine(xC, yC, xD, yD);
        drawLine(xD, yD, xA, yA);
        graphics.fillPolygon(xPoints1, yPoints1, 4);

        int[] xPoints2 = {xB, xF, xG, xC};
        int[] yPoints2 = {yB, yF, yG, yC};
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xC, yC);
        drawLine(xC, yC, xB, yB);
        graphics.fillPolygon(xPoints2, yPoints2, 4);

        int[] xPoints3 = {xE, xF, xG, xH};
        int[] yPoints3 = {yE, yF, yG, yH};
        drawLine(xE, yE, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xE, yE);
        graphics.fillPolygon(xPoints3, yPoints3, 4);

        int[] xPoints4 = {xA, xB, xF, xE};
        int[] yPoints4 = {yA, yB, yF, yE};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xE, yE);
        drawLine(xE, yE, xA, yA);
        graphics.fillPolygon(xPoints4, yPoints4, 4);

        int[] xPoints5 = {xD, xC, xG, xH};
        int[] yPoints5 = {yD, yC, yG, yH};
        drawLine(xD, yD, xC, yC);
        drawLine(xC, yC, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xD, yD);
        graphics.fillPolygon(xPoints5, yPoints5, 4);
    }
    public void drawCubeVPoint (double[][] coords, Color color) {
        int[] vPuntoFuga = {10, 10, 100};

        Puntos[] puntos3D = new Puntos[8];
        Puntos[] puntos2D = new Puntos[8];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = -vPuntoFuga[2] / (puntos3D[i].getZ() - vPuntoFuga[2]);
            int x = vPuntoFuga[0] + ((puntos3D[i].getX() - vPuntoFuga[0]) * u) + 300;
            int y = vPuntoFuga[1] + ((puntos3D[i].getY() - vPuntoFuga[1]) * u) + 300;

            puntos2D[i] = new Puntos(x, y);
        }

        int xA = puntos2D[0].getX(), yA = puntos2D[0].getY();
        int xB = puntos2D[1].getX(), yB = puntos2D[1].getY();
        int xC = puntos2D[2].getX(), yC = puntos2D[2].getY();
        int xD = puntos2D[3].getX(), yD = puntos2D[3].getY();
        int xE = puntos2D[4].getX(), yE = puntos2D[4].getY();
        int xF = puntos2D[5].getX(), yF = puntos2D[5].getY();
        int xG = puntos2D[6].getX(), yG = puntos2D[6].getY();
        int xH = puntos2D[7].getX(), yH = puntos2D[7].getY();

        graphics.setColor(color);
        int[] xPoints1 = {xA, xB, xC, xD};
        int[] yPoints1 = {yA, yB, yC, yD};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xC, yC);
        drawLine(xC, yC, xD, yD);
        drawLine(xD, yD, xA, yA);
        graphics.fillPolygon(xPoints1, yPoints1, 4);

        int[] xPoints2 = {xB, xF, xG, xC};
        int[] yPoints2 = {yB, yF, yG, yC};
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xC, yC);
        drawLine(xC, yC, xB, yB);
        graphics.fillPolygon(xPoints2, yPoints2, 4);

        int[] xPoints3 = {xE, xF, xG, xH};
        int[] yPoints3 = {yE, yF, yG, yH};
        drawLine(xE, yE, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xE, yE);
        graphics.fillPolygon(xPoints3, yPoints3, 4);

        int[] xPoints4 = {xA, xB, xF, xE};
        int[] yPoints4 = {yA, yB, yF, yE};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xE, yE);
        drawLine(xE, yE, xA, yA);
        graphics.fillPolygon(xPoints4, yPoints4, 4);

        int[] xPoints5 = {xD, xC, xG, xH};
        int[] yPoints5 = {yD, yC, yG, yH};
        drawLine(xD, yD, xC, yC);
        drawLine(xC, yC, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xD, yD);
        graphics.fillPolygon(xPoints5, yPoints5, 4);
    }
    public void drawOrthogonal (double[][] coords, Color color) {
        Puntos[] puntos3D = new Puntos[8];
        Puntos[] puntos2D = new Puntos[8];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = 0; // xpU, ypU, zpU son iguales a 0.
            int x = puntos3D[i].getX() + 120;
            int y = puntos3D[i].getY() + 320;

            puntos2D[i] = new Puntos(x, y);
        }

        int xA = puntos2D[0].getX(), yA = puntos2D[0].getY();
        int xB = puntos2D[1].getX(), yB = puntos2D[1].getY();
        int xC = puntos2D[2].getX(), yC = puntos2D[2].getY();
        int xD = puntos2D[3].getX(), yD = puntos2D[3].getY();
        int xE = puntos2D[4].getX(), yE = puntos2D[4].getY();
        int xF = puntos2D[5].getX(), yF = puntos2D[5].getY();
        int xG = puntos2D[6].getX(), yG = puntos2D[6].getY();
        int xH = puntos2D[7].getX(), yH = puntos2D[7].getY();

        graphics.setColor(color);
        int[] xPoints1 = {xA, xB, xC, xD};
        int[] yPoints1 = {yA, yB, yC, yD};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xC, yC);
        drawLine(xC, yC, xD, yD);
        drawLine(xD, yD, xA, yA);
        graphics.fillPolygon(xPoints1, yPoints1, 4);

        int[] xPoints2 = {xB, xF, xG, xC};
        int[] yPoints2 = {yB, yF, yG, yC};
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xC, yC);
        drawLine(xC, yC, xB, yB);
        graphics.fillPolygon(xPoints2, yPoints2, 4);

        int[] xPoints3 = {xE, xF, xG, xH};
        int[] yPoints3 = {yE, yF, yG, yH};
        drawLine(xE, yE, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xE, yE);
        graphics.fillPolygon(xPoints3, yPoints3, 4);

        int[] xPoints4 = {xA, xB, xF, xE};
        int[] yPoints4 = {yA, yB, yF, yE};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xE, yE);
        drawLine(xE, yE, xA, yA);
        graphics.fillPolygon(xPoints4, yPoints4, 4);

        int[] xPoints5 = {xD, xC, xG, xH};
        int[] yPoints5 = {yD, yC, yG, yH};
        drawLine(xD, yD, xC, yC);
        drawLine(xC, yC, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xD, yD);
        graphics.fillPolygon(xPoints5, yPoints5, 4);
    }
}
