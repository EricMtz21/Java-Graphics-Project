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

    // FIguras
    private double[][] pollo = {
            {20, 60, 60, 20, 20, 60, 60, 20},
            {40, 40, 40, 40, 70, 70, 70, 70},
            { 0,  0, 60, 60, 0, 0, 60, 60},
            { 1,  1,  1,  1,  1,  1,  1,  1}
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

        drawCube(pollo);
        drawCube(colita);

        if (   pollo[0][0] > getWidth() - 210
            || pollo[0][1] > getWidth() - 210
            || pollo[0][2] > getWidth() - 210
            || pollo[0][3] > getWidth() - 210
            || pollo[0][4] > getWidth() - 210
            || pollo[0][5] > getWidth() - 210
            || pollo[0][6] > getWidth() - 210
            || pollo[0][7] > getWidth() - 210
        ) {
            xDistance = -1;
        }
        if (   pollo[0][0] < -120
            || pollo[0][1] < -120
            || pollo[0][2] < -120
            || pollo[0][3] < -120
            || pollo[0][4] < -120
            || pollo[0][5] < -120
            || pollo[0][6] < -120
            || pollo[0][7] < -120
        ) {
            xDistance = 1;
        }
        if (pollo[1][7] < - 300) {
            yDistance = 1;
        }
        if (pollo[1][1] > getHeight() - 300) {
            yDistance = -1;
        }

        rotateX(pollo, 1);
        rotateX(colita, 1);
        //rotateY(pollo, 1);
        rotateZ(pollo, 1);
        //rotateY(colita, 1);
        rotateZ(colita, 1);

        translate(pollo, xDistance, yDistance, 0);
        translate(colita, xDistance, yDistance, 0);

        g.drawImage(bufferFondo, 0, 0, this);
    }

    private void translate(double[][] puntos, int tx, int ty, int tz) {
        for (int i = 0; i < puntos[0].length; i++) {
            puntos[0][i] += tx;
            puntos[1][i] += ty;
            puntos[2][i] += tz;
        }
    }

    private void rotateX(double[][] puntos, double angle) {
        for (int i = 0; i < puntos[1].length; i++) {
            double y = puntos[1][i];
            double z = puntos[2][i];

            puntos[1][i] = y * Math.cos(Math.toRadians(angle)) - z * Math.sin(Math.toRadians(angle));
            puntos[2][i] = y * Math.sin(Math.toRadians(angle)) + z * Math.cos(Math.toRadians(angle));
        }
    }

    private void rotateY(double[][] puntos, double angle) {
        for (int i = 0; i < puntos[0].length; i++) {
            double x = puntos[0][i];
            double z = puntos[2][i];

            puntos[0][i] = x * Math.cos(Math.toRadians(angle)) + z * Math.sin(Math.toRadians(angle));
            puntos[2][i] = -x * Math.sin(Math.toRadians(angle)) + z * Math.cos(Math.toRadians(angle));
        }
    }

    private void rotateZ(double[][] puntos, double angle) {
        for (int i = 0; i < puntos[0].length; i++) {
            double x = puntos[0][i];
            double y = puntos[1][i];

            puntos[0][i] = x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
            puntos[1][i] = x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));
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

    // Figuras
    public void drawCube (double[][] coords) {
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

        // Dibujar caras laterales del cubo
        graphics.setColor(Color.PINK);
        int[] xPoints1 = {xA, xB, xC, xD};
        int[] yPoints1 = {yA, yB, yC, yD};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xC, yC);
        drawLine(xC, yC, xD, yD);
        drawLine(xD, yD, xA, yA);
        graphics.fillPolygon(xPoints1, yPoints1, 4);

        graphics.setColor(Color.PINK);
        int[] xPoints2 = {xB, xF, xG, xC};
        int[] yPoints2 = {yB, yF, yG, yC};
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xC, yC);
        drawLine(xC, yC, xB, yB);
        graphics.fillPolygon(xPoints2, yPoints2, 4);

        graphics.setColor(Color.PINK);
        int[] xPoints3 = {xE, xF, xG, xH};
        int[] yPoints3 = {yE, yF, yG, yH};
        drawLine(xE, yE, xF, yF);
        drawLine(xF, yF, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xE, yE);
        graphics.fillPolygon(xPoints3, yPoints3, 4);

        // Dibujar caras superior e inferior del cubo
        graphics.setColor(Color.PINK);
        int[] xPoints4 = {xA, xB, xF, xE};
        int[] yPoints4 = {yA, yB, yF, yE};
        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xF, yF);
        drawLine(xF, yF, xE, yE);
        drawLine(xE, yE, xA, yA);
        graphics.fillPolygon(xPoints4, yPoints4, 4);

        graphics.setColor(Color.PINK);
        int[] xPoints5 = {xD, xC, xG, xH};
        int[] yPoints5 = {yD, yC, yG, yH};
        drawLine(xD, yD, xC, yC);
        drawLine(xC, yC, xG, yG);
        drawLine(xG, yG, xH, yH);
        drawLine(xH, yH, xD, yD);
        graphics.fillPolygon(xPoints5, yPoints5, 4);
    }
}
