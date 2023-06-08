import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Proyecto extends JFrame implements Runnable{
    private BufferedImage bufferFondo, bufferPixel;
    private Graphics2D graphics;
    private Thread thread;

    // VARIABLES
    int[] vDireccion = {-2, 2, 5};
    private boolean isRunning = true;

    // FIguras
    private double[][] pollo = {
            {20, 60, 60, 20, 20,   60,  60,  20, 20, 20, 60, 60},
            {40, 40, 40, 40, 120, 120, 120, 120, 70, 70, 70, 70},
            { 0,  0, 60, 60, 20,   20,  60,  60, 20,  0, 0, 20},
            { 1,  1,  1,  1,  1,    1,   1,  1,   1,  1, 1,  1}
    };
    private double[][] colita = {
            {30, 50, 50, 30, 30, 50, 50, 30},
            {50, 50, 70, 70, 50, 50, 70, 70},
            {-5, -5, -5, -5,  0,  0,  0,  0},
            { 1,  1,  1,  1,  1,  1,  1,  1}
    };

    public Proyecto () {
        setSize(400, 700);
        setTitle("Crossy Road");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        bufferFondo = new BufferedImage(400, 700, BufferedImage.TYPE_INT_RGB);
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
                repaint();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(graphics);

        drawChicken();
        rotarX(pollo, 1);
        rotarY(pollo, 1);
        rotarZ(pollo, 1);
        rotarX(colita, 1);
        rotarY(colita, 1);
        rotarZ(colita, 1);

        g.drawImage(bufferFondo, 0, 0, this);
    }

    private void rotarX(double[][] puntos, double angulo) {
        double coseno = Math.cos(Math.toRadians(angulo));
        double seno = Math.sin(Math.toRadians(angulo));

        for (int i = 0; i < puntos[1].length; i++) {
            double y = puntos[1][i];
            double z = puntos[2][i];

            puntos[1][i] = y * coseno - z * seno;
            puntos[2][i] = y * seno + z * coseno;
        }
    }

    private void rotarY(double[][] puntos, double angulo) {
        double coseno = Math.cos(Math.toRadians(angulo));
        double seno = Math.sin(Math.toRadians(angulo));

        for (int i = 0; i < puntos[0].length; i++) {
            double x = puntos[0][i];
            double z = puntos[2][i];

            puntos[0][i] = x * coseno + z * seno;
            puntos[2][i] = -x * seno + z * coseno;
        }
    }

    private void rotarZ(double[][] puntos, double angulo) {
        double coseno = Math.cos(Math.toRadians(angulo));
        double seno = Math.sin(Math.toRadians(angulo));

        for (int i = 0; i < puntos[0].length; i++) {
            double x = puntos[0][i];
            double y = puntos[1][i];

            puntos[0][i] = x * coseno - y * seno;
            puntos[1][i] = x * seno + y * coseno;
        }
    }

    public void putPixel(int x, int y, Color pixelColor) {
        bufferPixel.setRGB(0, 0, pixelColor.getRGB());
        graphics.drawImage(bufferPixel, x, y, this);
    }
    public int getPixel(int x, int y) {
        return bufferFondo.getRGB(x, y);
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

    // Figuras
    public void drawBody (double[][] coords) {

        Puntos[] puntos3D = new Puntos[12];
        Puntos[] puntos2D = new Puntos[12];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = (-puntos3D[i].getZ()) / vDireccion[2];
            int x = puntos3D[i].getX() + (vDireccion[0] * u) + 220;
            int y = puntos3D[i].getY() + (vDireccion[1] * u) + 520;

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
        int xI = puntos2D[8].getX(), yI = puntos2D[8].getY();
        int xJ = puntos2D[9].getX(), yJ = puntos2D[9].getY();
        int xK = puntos2D[10].getX(), yK = puntos2D[10].getY();
        int xL = puntos2D[11].getX(), yL = puntos2D[11].getY();

        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xC, yC);
        drawLine(xC, yC, xD, yD);
        drawLine(xD, yD, xA, yA);

        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xE, yE);
        drawLine(xE, yE, xF, yF);
        drawLine(xF, yF, xG, yG);

        drawLine(xJ, yJ, xK, yK);
        drawLine(xK, yK, xL, yL);
        drawLine(xL, yL, xI, yI);
        drawLine(xI, yI, xJ, yJ);

        drawLine(xC, yC, xG, yG);
        drawLine(xD, yD, xH, yH);

        drawLine(xA, yA, xJ, yJ);
        drawLine(xB, yB, xK, yK);

        drawLine(xI, yI, xE, yE);
        drawLine(xL, yL, xF, yF);
    }
    public void drawTail (double[][] coords) {
        Puntos[] puntos3D = new Puntos[8];
        Puntos[] puntos2D = new Puntos[8];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = (-puntos3D[i].getZ()) / vDireccion[2];
            int x = puntos3D[i].getX() + (vDireccion[0] * u) + 220;
            int y = puntos3D[i].getY() + (vDireccion[1] * u) + 520;

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

        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xC, yC);
        drawLine(xC, yC, xD, yD);
        drawLine(xD, yD, xA, yA);

        drawLine(xE, yE, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xE, yE);

        drawLine(xA, yA, xE, yE);
        drawLine(xB, yB, xF, yF);
        drawLine(xC, yC, xG, yG);
        drawLine(xD, yD, xH, yH);
    }

    public void drawChicken () {
        drawBody(pollo);
        drawTail(colita);
    }
}
