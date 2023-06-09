import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Ortogonal extends JFrame {
    private BufferedImage buffer;
    private double[][] polyCoords = {
            {100, 400, 100, 400, 100, 400, 100, 400, 400, 600, 400, 600, 400, 600, 400, 600},
            {100, 100, 100, 100, 700, 700, 700, 700, 500, 500, 500, 500, 300, 300, 300, 300},
            {300, 300, 500, 500, 300, 300, 500, 500, 300, 300, 500, 500, 300, 300, 500, 500}};
    public Ortogonal() {
        setSize(800, 800);
        setTitle("Proyección Ortogonal");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
    public void paint(Graphics g) {
        super.paint(g);
        drawPoly(polyCoords);
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
    public void drawPoly (double[][] coords) {
        Puntos[] puntos3D = new Puntos[16];
        Puntos[] puntos2D = new Puntos[16];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = 0; // xpU, ypU, zpU son iguales a 0.
            int x = puntos3D[i].getX();
            int y = puntos3D[i].getY();

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
        int xM = puntos2D[12].getX(), yM = puntos2D[12].getY();
        int xN = puntos2D[13].getX(), yN = puntos2D[13].getY();
        int xO = puntos2D[14].getX(), yO = puntos2D[14].getY();
        int xP = puntos2D[15].getX(), yP = puntos2D[15].getY();

        drawLine(xA, yA, xB, yB);
        drawLine(xB, yB, xM, yM);
        drawLine(xM, yM, xN, yN);
        drawLine(xN, yN, xJ, yJ);
        drawLine(xJ, yJ, xI, yI);
        drawLine(xI, yI, xF, yF);
        drawLine(xF, yF, xE, yE);
        drawLine(xE, yE, xA, yA);

        drawLine(xC, yC, xD, yD);
        drawLine(xD, yD, xO, yO);
        drawLine(xO, yO, xP, yP);
        drawLine(xP, yP, xL, yL);
        drawLine(xL, yL, xK, yK);
        drawLine(xK, yK, xH, yH);
        drawLine(xH, yH, xG, yG);
        drawLine(xG, yG, xC, yC);

        drawLine(xA, yA, xC, yC);
        drawLine(xB, yB, xD, yD);
        drawLine(xE, yE, xG, yG);
        drawLine(xF, yF, xH, yH);
        drawLine(xI, yI, xK, yK);
        drawLine(xJ, yJ, xL, yL);
        drawLine(xM, yM, xO, yO);
        drawLine(xN, yN, xP, yP);
    }
}
