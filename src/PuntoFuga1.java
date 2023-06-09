import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PuntoFuga1 extends JFrame {
    private BufferedImage buffer;
    private double[][] cubeCoords = {
            {30, 70, 30, 70, 30, 70, 30, 70},
            {10, 10, 30, 30, 10, 10, 30, 30},
            {10, 10, 10, 10, 50, 50, 50, 50}};
    public PuntoFuga1() {
        setSize(900, 800);
        setTitle("Proyección Ortogonal");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
    public void paint(Graphics g) {
        super.paint(g);
        drawCube(cubeCoords);
    }
    public void putPixel(int x, int y, Color pixelColor) {
        buffer.setRGB(0, 0, pixelColor.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
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
                putPixel((int) xk, (int) yk, Color.BLACK);
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

    public void drawCube (double[][] coords) {
        int[] vPuntoFuga = {120, -10, 90};

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

        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xD, yD);
        drawLine(xD, yD, xC, yC);
        drawLine(xC, yC, xA, yA);
        drawLine(xE, yE, xF, yF);
        drawLine(xF, yF, xH, yH);
        drawLine(xH, yH, xG, yG);
        drawLine(xG, yG, xE, yE);
        drawLine(xA, yA, xE, yE);
        drawLine(xB, yB, xF, yF);
        drawLine(xC, yC, xG, yG);
        drawLine(xD, yD, xH, yH);
    }
}
