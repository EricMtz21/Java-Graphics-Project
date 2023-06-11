import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Proyecto extends JFrame implements Runnable, KeyListener {
    private BufferedImage bufferFondo, bufferPixel;
    private Graphics2D graphics;
    private Thread thread;

    // VARIABLES
    int[] vDireccion = {-2, 2, 5};
    private boolean isRunning = true;
    int yDistance = 1;
    double scaleValue = 0.95;
    float timeElapsed = 0;
    boolean isAlive = true;
    int yPolloDistance = 0;
    int yPolloDistanceCovered = 3100;
    int timer = 0;

    public Proyecto () {
        setSize(600, 800);
        setTitle("Crossy Road");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        bufferFondo = new BufferedImage(600, 800, BufferedImage.TYPE_INT_RGB);
        bufferPixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) bufferFondo.createGraphics();

        moveCar(-300, 0);
        moveCar2(-1200, -530);
        moveCar3(-3000, -1060);

        addKeyListener(this);
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

                if (Coordenadas.calle1Borde[1][0] == 20) {
                    Coordenadas.calle1 = new double[][]{
                            {0, 600, 600, 0},
                            {-130, -130, 20, 20},
                            {1, 1, 1, 1}
                    };
                    Coordenadas.pastoBorde1 = new double[][]{
                            {0, 600, 600, 0},
                            {-140, -140, -130, -130},
                            {1, 1, 1, 1}
                    };
                    Coordenadas.pastoOscuro1 = new double[][]{
                            {0, 600, 600, 0},
                            {-215, -215, -140, -140},
                            {1, 1, 1, 1}
                    };
                }

                if (Coordenadas.pastoOscuro1[1][0] == 20) {
                    Coordenadas.pastoClaro1 = new double[][]{
                            {0, 600, 600, 0},
                            {-55, -55, 20, 20},
                            {1, 1, 1, 1}
                    };
                }

                if (Coordenadas.pastoClaro1[1][0] == 20) {
                    Coordenadas.calleMitad1 = new double[][]{
                            {0, 600, 600, 0},
                            {-55, -55, 20, 20},
                            {1, 1, 1, 1}
                    };
                    Coordenadas.rio1 = new double[][]{
                            {0, 600, 600, 0},
                            {-205, -205, -55, -55},
                            {1, 1, 1, 1}
                    };
                    Coordenadas.calle2Borde = new double[][]{
                            {0, 600, 600, 0},
                            {-215, -215, -205, -205},
                            {1, 1, 1, 1}
                    };
                }

                if (Coordenadas.calle2Borde[1][0] == 20) {
                    Coordenadas.calle2 = new double[][]{
                            {0, 600, 600, 0},
                            {-120, -120, 20, 20},
                            {1, 1, 1, 1}
                    };
                    Coordenadas.pastoBorde2 = new double[][]{
                            {0, 600, 600, 0},
                            {-130, -130, -120, -120},
                            {1, 1, 1, 1}
                    };
                    Coordenadas.pastoOscuro2 = new double[][]{
                            {0, 600, 600, 0},
                            {-205, -205, -130, -130},
                            {1, 1, 1, 1}
                    };
                }

                if (Coordenadas.pastoOscuro2[1][0] == 20) {
                    yDistance = 0;
                }

                if (yDistance == 0) {
                    yPolloDistance = -4;
                    yPolloDistanceCovered += yPolloDistance;
                    if (yPolloDistanceCovered <= 0) {
                        isAlive = false;
                        yPolloDistance = 0;
                    }
                }

                if (!isAlive) {
                    timer += 16;
                    if (timer <= 2000) {
                        scaleChicken();
                    }
                }

                repaint();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            moveChicken(-10, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            moveChicken(10, 0);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    public void paintPath () {
        drawRectangle(Coordenadas.calle1, Colores.calleCentro);
        drawRectangle(Coordenadas.pastoBorde1, Colores.pastoBorde);
        drawRectangle(Coordenadas.pastoOscuro1, Colores.pastoOscuro);
        drawRectangle(Coordenadas.pastoClaro1, Colores.pastoClaro);
        drawRectangle(Coordenadas.calleMitad1, Colores.calleCentro);
        drawRectangle(Coordenadas.rio1, Colores.aguaClara);
        drawRectangle(Coordenadas.calle2Borde, Colores.calleBorde);
        drawRectangle(Coordenadas.calle2, Colores.calleCentro);
        drawRectangle(Coordenadas.pastoBorde2, Colores.pastoBorde);
        drawRectangle(Coordenadas.pastoOscuro2, Colores.pastoOscuro);
        drawRectangle(Coordenadas.pastoClaro2, Colores.pastoClaro);
        drawRectangle(Coordenadas.calleMitad2, Colores.calleCentro);
        drawRectangle(Coordenadas.rio2, Colores.aguaClara);
        drawRectangle(Coordenadas.calle1Borde, Colores.calleBorde);
        drawCube(Coordenadas.troncoArbol1, Colores.troncoArbolFrente, Colores.troncoArbolFrente);
        drawCube(Coordenadas.hojasArbol1, Colores.hojasFrente, Colores.hojasFrente);
        drawCube(Coordenadas.troncoArbol3, Colores.troncoArbolFrente, Colores.troncoArbolFrente);
        drawCube(Coordenadas.hojasArbol3, Colores.hojasFrente, Colores.hojasFrente);
        drawCube(Coordenadas.troncoArbol5, Colores.troncoArbolFrente, Colores.troncoArbolFrente);
        drawCube(Coordenadas.hojasArbol5, Colores.hojasFrente, Colores.hojasFrente);
        drawCube(Coordenadas.troncoArbol2, Colores.troncoArbolFrente, Colores.troncoArbolFrente);
        drawCube(Coordenadas.hojasArbol2, Colores.hojasFrente, Colores.hojasFrente);
        drawCube(Coordenadas.troncoArbol4, Colores.troncoArbolFrente, Colores.troncoArbolFrente);
        drawCube(Coordenadas.hojasArbol4, Colores.hojasFrente, Colores.hojasFrente);
        drawCube(Coordenadas.troncoArbol6, Colores.troncoArbolFrente, Colores.troncoArbolFrente);
        drawCube(Coordenadas.hojasArbol6, Colores.hojasFrente, Colores.hojasFrente);
    }
    public void paintChicken () {
        drawCube(Coordenadas.ojoI, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.pataDerecha, Colores.picoPollo, Colores.picoPollo);
        drawCube(Coordenadas.piernaDerecha, Colores.pataPollo, Colores.pataPollo);
        drawCube(Coordenadas.pataIzquierda, Colores.picoPollo, Colores.picoPollo);
        drawCube(Coordenadas.piernaIzquierda, Colores.pataPollo, Colores.pataPollo);
        drawCube(Coordenadas.alaIzquierdaPollo, Colores.polloFrente, Colores.polloFrente);
        drawCube(Coordenadas.picoPollo, Colores.picoPollo, Colores.picoPollo);
        drawCube(Coordenadas.cuerpoPollo, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.cabezaPollo, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.crestaPollo, Colores.crestaArriba, Colores.crestaArriba);
        drawCube(Coordenadas.alaDerechaPollo, Colores.polloFrente, Colores.polloFrente);
        drawCube(Coordenadas.ojoD, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.colitaPollo, Colores.polloFrente, Colores.polloFrente);
    }
    public void paintOrthogonalChicken () {
        drawOrthogonal(Coordenadas.ojoI, Color.BLACK);
        drawOrthogonal(Coordenadas.pataDerecha, Colores.picoPollo);
        drawOrthogonal(Coordenadas.piernaDerecha, Colores.pataPollo);
        drawOrthogonal(Coordenadas.pataIzquierda, Colores.picoPollo);
        drawOrthogonal(Coordenadas.piernaIzquierda, Colores.pataPollo);
        drawOrthogonal(Coordenadas.alaIzquierdaPollo, Colores.polloFrente);
        drawOrthogonal(Coordenadas.picoPollo, Colores.picoPollo);
        drawOrthogonal(Coordenadas.cuerpoPollo, Colores.polloArriba);
        drawOrthogonal(Coordenadas.cabezaPollo, Colores.polloArriba);
        drawOrthogonal(Coordenadas.crestaPollo, Colores.crestaArriba);
        drawOrthogonal(Coordenadas.alaDerechaPollo, Colores.polloFrente);
        drawOrthogonal(Coordenadas.ojoD, Color.BLACK);
        drawOrthogonal(Coordenadas.colitaPollo, Colores.polloFrente);
    }
    public void scaleChicken () {
        Coordenadas.ojoI = scale(Coordenadas.ojoI, scaleValue);
        Coordenadas.pataDerecha = scale(Coordenadas.pataDerecha, scaleValue);
        Coordenadas.piernaDerecha = scale(Coordenadas.piernaDerecha, scaleValue);
        Coordenadas.pataIzquierda = scale(Coordenadas.pataIzquierda, scaleValue);
        Coordenadas.piernaIzquierda = scale(Coordenadas.piernaIzquierda, scaleValue);
        Coordenadas.alaIzquierdaPollo = scale(Coordenadas.alaIzquierdaPollo, scaleValue);
        Coordenadas.picoPollo = scale(Coordenadas.picoPollo, scaleValue);
        Coordenadas.cuerpoPollo = scale(Coordenadas.cuerpoPollo, scaleValue);
        Coordenadas.cabezaPollo = scale(Coordenadas.cabezaPollo, scaleValue);
        Coordenadas.crestaPollo = scale(Coordenadas.crestaPollo, scaleValue);
        Coordenadas.alaDerechaPollo = scale(Coordenadas.alaDerechaPollo, scaleValue);
        Coordenadas.ojoD = scale(Coordenadas.ojoD, scaleValue);
        Coordenadas.colitaPollo = scale(Coordenadas.colitaPollo, scaleValue);
    }
    public void moveChicken (int xMov, int yMov) {
        translate(Coordenadas.ojoI, xMov, yMov, 0);
        translate(Coordenadas.pataDerecha, xMov, yMov, 0);
        translate(Coordenadas.piernaDerecha, xMov, yMov, 0);
        translate(Coordenadas.pataIzquierda, xMov, yMov, 0);
        translate(Coordenadas.piernaIzquierda, xMov, yMov, 0);
        translate(Coordenadas.alaIzquierdaPollo, xMov, yMov, 0);
        translate(Coordenadas.picoPollo, xMov, yMov, 0);
        translate(Coordenadas.cuerpoPollo, xMov, yMov, 0);
        translate(Coordenadas.cabezaPollo, xMov, yMov, 0);
        translate(Coordenadas.crestaPollo, xMov, yMov, 0);
        translate(Coordenadas.alaDerechaPollo, xMov, yMov, 0);
        translate(Coordenadas.ojoD, xMov, yMov, 0);
        translate(Coordenadas.colitaPollo, xMov, yMov, 0);
    }
    public void paintCar () {
        drawCube(Coordenadas.defensaAdelanteCarro, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.llantaAdelante, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.cofreCarro, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.cuerpoCarro, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.llantaAtras, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.defensaAtrasCarro, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.cajuelaCarro, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.pilarCabina3, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.pilarCabina4, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.ventanasCarro2, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.pilarCabina1, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.ventanasCarro1, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.pilarCabina2, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.techoCarro, Colores.polloArriba, Colores.polloArriba);
    }
    public void paintCar2 () {
        drawCube(Coordenadas.defensaAdelanteCarro2, Colores.colorCarroNaranja, Colores.colorCarroNaranja);
        drawCube(Coordenadas.llantaAdelante2, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.cofreCarro2, Colores.colorCarroNaranja, Colores.colorCarroNaranja);
        drawCube(Coordenadas.cuerpoCarro2, Colores.colorCarroNaranja, Colores.colorCarroNaranja);
        drawCube(Coordenadas.llantaAtras2, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.defensaAtrasCarro2, Colores.colorCarroNaranja, Colores.colorCarroNaranja);
        drawCube(Coordenadas.cajuelaCarro2, Colores.colorCarroNaranja, Colores.colorCarroNaranja);
        drawCube(Coordenadas.pilarCabina32, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.pilarCabina42, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.ventanasCarro22, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.pilarCabina12, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.ventanasCarro12, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.pilarCabina22,Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.techoCarro2, Colores.polloArriba, Colores.polloArriba);
    }
    public void paintCar3 () {
        drawCube(Coordenadas.defensaAdelanteCarro3, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.llantaAdelante3, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.cofreCarro3, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.cuerpoCarro3, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.llantaAtras3, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.defensaAtrasCarro3, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.cajuelaCarro3, Colores.colorCarroAzul, Colores.colorCarroAzul);
        drawCube(Coordenadas.pilarCabina33, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.pilarCabina43, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.ventanasCarro23, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.pilarCabina13, Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.ventanasCarro13, Color.BLACK, Color.BLACK);
        drawCube(Coordenadas.pilarCabina23,Colores.polloArriba, Colores.polloArriba);
        drawCube(Coordenadas.techoCarro3, Colores.polloArriba, Colores.polloArriba);
    }
    public void moveCar (int xMovement, int yMovement) {
        translate(Coordenadas.defensaAdelanteCarro, xMovement, yMovement, 0);
        translate(Coordenadas.llantaAdelante, xMovement, yMovement, 0);
        translate(Coordenadas.cofreCarro, xMovement, yMovement, 0);
        translate(Coordenadas.cuerpoCarro, xMovement, yMovement, 0);
        translate(Coordenadas.llantaAtras, xMovement, yMovement, 0);
        translate(Coordenadas.defensaAtrasCarro, xMovement, yMovement, 0);
        translate(Coordenadas.cajuelaCarro, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina3, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina4, xMovement, yMovement, 0);
        translate(Coordenadas.ventanasCarro2, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina1, xMovement, yMovement, 0);
        translate(Coordenadas.ventanasCarro1, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina2, xMovement, yMovement, 0);
        translate(Coordenadas.techoCarro, xMovement, yMovement, 0);
    }
    public void moveCar2 (int xMovement, int yMovement) {
        translate(Coordenadas.defensaAdelanteCarro2, xMovement, yMovement, 0);
        translate(Coordenadas.llantaAdelante2, xMovement, yMovement, 0);
        translate(Coordenadas.cofreCarro2, xMovement, yMovement, 0);
        translate(Coordenadas.cuerpoCarro2, xMovement, yMovement, 0);
        translate(Coordenadas.llantaAtras2, xMovement, yMovement, 0);
        translate(Coordenadas.defensaAtrasCarro2, xMovement, yMovement, 0);
        translate(Coordenadas.cajuelaCarro2, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina32, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina42, xMovement, yMovement, 0);
        translate(Coordenadas.ventanasCarro22, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina12, xMovement, yMovement, 0);
        translate(Coordenadas.ventanasCarro12, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina22, xMovement, yMovement, 0);
        translate(Coordenadas.techoCarro2, xMovement, yMovement, 0);
    }
    public void moveCar3 (int xMovement, int yMovement) {
        translate(Coordenadas.defensaAdelanteCarro3, xMovement, yMovement, 0);
        translate(Coordenadas.llantaAdelante3, xMovement, yMovement, 0);
        translate(Coordenadas.cofreCarro3, xMovement, yMovement, 0);
        translate(Coordenadas.cuerpoCarro3, xMovement, yMovement, 0);
        translate(Coordenadas.llantaAtras3, xMovement, yMovement, 0);
        translate(Coordenadas.defensaAtrasCarro3, xMovement, yMovement, 0);
        translate(Coordenadas.cajuelaCarro3, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina33, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina43, xMovement, yMovement, 0);
        translate(Coordenadas.ventanasCarro23, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina13, xMovement, yMovement, 0);
        translate(Coordenadas.ventanasCarro13, xMovement, yMovement, 0);
        translate(Coordenadas.pilarCabina23, xMovement, yMovement, 0);
        translate(Coordenadas.techoCarro3, xMovement, yMovement, 0);
    }
    public void rotateWheelds () {
        rotateZFromCenter(Coordenadas.llantaAtras, 15);
        rotateZFromCenter(Coordenadas.llantaAdelante, 15);
        rotateZFromCenter(Coordenadas.llantaAtras2, 15);
        rotateZFromCenter(Coordenadas.llantaAdelante2, 15);
        rotateZFromCenter(Coordenadas.llantaAtras3, 15);
        rotateZFromCenter(Coordenadas.llantaAdelante3, 15);
    }
    public void translatePath () {
        translate(Coordenadas.calle1 , 0, yDistance, 0);
        translate(Coordenadas.pastoBorde1 , 0, yDistance, 0);
        translate(Coordenadas.pastoOscuro1, 0, yDistance, 0);
        translate(Coordenadas.pastoClaro1, 0, yDistance, 0);
        translate(Coordenadas.calleMitad1, 0, yDistance, 0);
        translate(Coordenadas.rio1, 0, yDistance, 0);
        translate(Coordenadas.calle2Borde, 0, yDistance, 0);
        translate(Coordenadas.calle2, 0, yDistance, 0);
        translate(Coordenadas.pastoBorde2, 0, yDistance, 0);
        translate(Coordenadas.pastoOscuro2, 0, yDistance, 0);
        translate(Coordenadas.pastoClaro2, 0, yDistance, 0);
        translate(Coordenadas.calleMitad2, 0, yDistance, 0);
        translate(Coordenadas.rio2, 0, yDistance, 0);
        translate(Coordenadas.calle1Borde, 0, yDistance, 0);
        translate(Coordenadas.troncoArbol1, 0, yDistance, 0);
        translate(Coordenadas.hojasArbol1, 0, yDistance, 0);
        translate(Coordenadas.troncoArbol2, 0, yDistance, 0);
        translate(Coordenadas.hojasArbol2, 0, yDistance, 0);
        translate(Coordenadas.troncoArbol3, 0, yDistance, 0);
        translate(Coordenadas.hojasArbol3, 0, yDistance, 0);
        translate(Coordenadas.troncoArbol4, 0, yDistance, 0);
        translate(Coordenadas.hojasArbol4, 0, yDistance, 0);
        translate(Coordenadas.troncoArbol5, 0, yDistance, 0);
        translate(Coordenadas.hojasArbol5, 0, yDistance, 0);
        translate(Coordenadas.troncoArbol6, 0, yDistance, 0);
        translate(Coordenadas.hojasArbol6, 0, yDistance, 0);
        moveCar(2, yDistance);
        moveCar2(2, yDistance);
        moveCar3(2, yDistance);
    }

    public void paintLogs () {

        drawCube(Coordenadas.bordeTroncoIzq1, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq1, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq1, 10);

        drawCube(Coordenadas.tronco1, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco1, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco1, 10);

        drawCube(Coordenadas.bordeTroncoDer1, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer1, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer1, 10);

        drawCube(Coordenadas.bordeTroncoIzq2, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq2, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq2, 10);

        drawCube(Coordenadas.tronco2, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco2, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco2, 10);

        drawCube(Coordenadas.bordeTroncoDer2, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer2, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer2, 10);

        drawCube(Coordenadas.bordeTroncoIzq3, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq3, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq3, 10);

        drawCube(Coordenadas.tronco3, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco3, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco3, 10);

        drawCube(Coordenadas.bordeTroncoDer3, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer3, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer3, 10);

        drawCube(Coordenadas.bordeTroncoIzq4, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq4, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq4, 10);

        drawCube(Coordenadas.tronco4, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco4, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco4, 10);

        drawCube(Coordenadas.bordeTroncoDer4, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer4, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer4, 10);

        drawCube(Coordenadas.bordeTroncoIzq5, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq5, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq5, 10);

        drawCube(Coordenadas.tronco5, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco5, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco5, 10);

        drawCube(Coordenadas.bordeTroncoDer5, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer5, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer5, 10);

        drawCube(Coordenadas.bordeTroncoIzq6, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq6, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq6, 10);

        drawCube(Coordenadas.tronco6, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco6, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco6, 10);

        drawCube(Coordenadas.bordeTroncoDer6, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer6, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer6, 10);

        drawCube(Coordenadas.bordeTroncoIzq7, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq7, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq7, 10);

        drawCube(Coordenadas.tronco7, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco7, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco7, 10);

        drawCube(Coordenadas.bordeTroncoDer7, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer7, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer7, 10);

        drawCube(Coordenadas.bordeTroncoIzq8, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq8, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq8, 10);

        drawCube(Coordenadas.tronco8, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco8, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco8, 10);

        drawCube(Coordenadas.bordeTroncoDer8, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer8, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer8, 10);

        drawCube(Coordenadas.bordeTroncoIzq9, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoIzq9, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoIzq9, 10);

        drawCube(Coordenadas.tronco9, Colores.troncoCuerpo, Colores.troncoCuerpo);
        translate(Coordenadas.tronco9, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.tronco9, 10);

        drawCube(Coordenadas.bordeTroncoDer9, Colores.troncoBorde, Colores.troncoBorde);
        translate(Coordenadas.bordeTroncoDer9, 1, (int) (yDistance), 0);
        rotateXFromCenter(Coordenadas.bordeTroncoDer9, 10);
    }
    public void paint(Graphics g) {
        super.paint(graphics);

        paintPath();
        translatePath();
        paintLogs();

        if (isAlive) {
            paintChicken();
            moveChicken(0, yPolloDistance);
        }
        if (!isAlive) {
            paintOrthogonalChicken();
        }

        paintCar();
        paintCar2();
        paintCar3();
        rotateWheelds();

        g.drawImage(bufferFondo, 0, 0, this);
    }

    public void putPixel(int x, int y, Color pixelColor) {
        bufferPixel.setRGB(0, 0, pixelColor.getRGB());
        graphics.drawImage(bufferPixel, x, y, this);
    }
    public int getPixel(int x, int y) {
        return bufferFondo.getRGB(x, y);
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
    private void rotateXFromCenter(double[][] coords, double angle) {
        // Paso 1: Calcular el centro de la figura
        double centerX = 0.0;
        double centerY = 0.0;
        double centerZ = 0.0;

        for (int i = 0; i < coords[0].length; i++) {
            centerX += coords[0][i];
            centerY += coords[1][i];
            centerZ += coords[2][i];
        }

        centerX /= coords[0].length;
        centerY /= coords[0].length;
        centerZ /= coords[0].length;

        // Paso 2: Restar el centro a cada coordenada
        for (int i = 0; i < coords[0].length; i++) {
            coords[0][i] -= centerX;
            coords[1][i] -= centerY;
            coords[2][i] -= centerZ;
        }

        // Paso 3: Realizar la rotación
        double cosAngle = Math.cos(Math.toRadians(angle));
        double sinAngle = Math.sin(Math.toRadians(angle));

        for (int i = 0; i < coords[0].length; i++) {
            double y = coords[1][i];
            double z = coords[2][i];

            coords[1][i] = y * cosAngle - z * sinAngle;
            coords[2][i] = y * sinAngle + z * cosAngle;
        }

        // Paso 4: Sumar el centro a cada coordenada
        for (int i = 0; i < coords[0].length; i++) {
            coords[0][i] += centerX;
            coords[1][i] += centerY;
            coords[2][i] += centerZ;
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
    private void rotateYFromCenter(double[][] coords, double angle) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double maxZ = Double.MIN_VALUE;

        for (int i = 0; i < coords[0].length; i++) {
            double x = coords[0][i];
            double y = coords[1][i];
            double z = coords[2][i];

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            minZ = Math.min(minZ, z);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            maxZ = Math.max(maxZ, z);
        }

        double centerX = (minX + maxX) / 2.0;
        double centerY = (minY + maxY) / 2.0;
        double centerZ = (minZ + maxZ) / 2.0;

        for (int i = 0; i < coords[0].length; i++) {
            coords[0][i] -= centerX;
            coords[1][i] -= centerY;
            coords[2][i] -= centerZ;
        }

        double cosAngle = Math.cos(Math.toRadians(angle));
        double sinAngle = Math.sin(Math.toRadians(angle));

        for (int i = 0; i < coords[0].length; i++) {
            double x = coords[0][i];
            double z = coords[2][i];

            coords[0][i] = x * cosAngle + z * sinAngle;
            coords[2][i] = -x * sinAngle + z * cosAngle;
        }

        for (int i = 0; i < coords[0].length; i++) {
            coords[0][i] += centerX;
            coords[1][i] += centerY;
            coords[2][i] += centerZ;
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
    private void rotateZFromCenter(double[][] coords, double angle) {
        // Paso 1: Calcular el centro de la figura
        double centerX = 0.0;
        double centerY = 0.0;
        double centerZ = 0.0;

        for (int i = 0; i < coords[0].length; i++) {
            centerX += coords[0][i];
            centerY += coords[1][i];
            centerZ += coords[2][i];
        }

        centerX /= coords[0].length;
        centerY /= coords[0].length;
        centerZ /= coords[0].length;

        // Paso 2: Restar el centro a cada coordenada
        for (int i = 0; i < coords[0].length; i++) {
            coords[0][i] -= centerX;
            coords[1][i] -= centerY;
            coords[2][i] -= centerZ;
        }

        // Paso 3: Realizar la rotación
        double cosAngle = Math.cos(Math.toRadians(angle));
        double sinAngle = Math.sin(Math.toRadians(angle));

        for (int i = 0; i < coords[0].length; i++) {
            double x = coords[0][i];
            double y = coords[1][i];

            coords[0][i] = x * cosAngle - y * sinAngle;
            coords[1][i] = x * sinAngle + y * cosAngle;
        }

        // Paso 4: Sumar el centro a cada coordenada
        for (int i = 0; i < coords[0].length; i++) {
            coords[0][i] += centerX;
            coords[1][i] += centerY;
            coords[2][i] += centerZ;
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
    public void drawLine (int xStart, int yStart, int xEnd, int yEnd, Color color) {
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
                putPixel(xk, yk, color);
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
                putPixel(xk, yk, color);
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
    public void drawCube (double[][] coords, Color color, Color color2) {
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
        drawLine(xA, yA, xB, yB, color2);
        drawLine(xB, yB, xC, yC, color2);
        drawLine(xC, yC, xD, yD, color2);
        drawLine(xD, yD, xA, yA, color2);
        graphics.fillPolygon(xPoints1, yPoints1, 4);

        int[] xPoints2 = {xB, xF, xG, xC};
        int[] yPoints2 = {yB, yF, yG, yC};
        drawLine(xB, yB, xF, yF, color2);
        drawLine(xF, yF, xG, yG, color2);
        drawLine(xG, yG, xC, yC, color2);
        drawLine(xC, yC, xB, yB, color);
        graphics.fillPolygon(xPoints2, yPoints2, 4);

        int[] xPoints3 = {xE, xF, xG, xH};
        int[] yPoints3 = {yE, yF, yG, yH};
        drawLine(xE, yE, xF, yF, color2);
        drawLine(xF, yF, xG, yG, color2);
        drawLine(xG, yG, xH, yH, color2);
        drawLine(xH, yH, xE, yE, color2);
        graphics.fillPolygon(xPoints3, yPoints3, 4);

        int[] xPoints4 = {xA, xB, xF, xE};
        int[] yPoints4 = {yA, yB, yF, yE};
        drawLine(xA, yA, xB, yB, color2);
        drawLine(xB, yB, xF, yF, color2);
        drawLine(xF, yF, xE, yE, color2);
        drawLine(xE, yE, xA, yA, color2);
        graphics.fillPolygon(xPoints4, yPoints4, 4);

        int[] xPoints5 = {xD, xC, xG, xH};
        int[] yPoints5 = {yD, yC, yG, yH};
        drawLine(xD, yD, xC, yC, color2);
        drawLine(xC, yC, xG, yG, color2);
        drawLine(xG, yG, xH, yH, color2);
        drawLine(xH, yH, xD, yD, color2);
        graphics.fillPolygon(xPoints5, yPoints5, 4);
    }
    public void drawCubeVPoint (double[][] coords, Color color) {
        int[] vPuntoFuga = {110, 350, 60};

        Puntos[] puntos3D = new Puntos[8];
        Puntos[] puntos2D = new Puntos[8];

        for (int i = 0; i < coords[0].length; i++) {
            puntos3D[i] = new Puntos((int) coords[0][i], (int) coords[1][i], (int) coords[2][i]);
        }

        for (int i = 0; i < puntos3D.length; i++) {
            int u = -vPuntoFuga[2] / (puntos3D[i].getZ() - vPuntoFuga[2]);
            int x = vPuntoFuga[0] + ((puntos3D[i].getX() - vPuntoFuga[0]) * u) + 100;
            int y = vPuntoFuga[1] + ((puntos3D[i].getY() - vPuntoFuga[1]) * u) + 100;

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
        drawLine(xA, yA, xB, yB, color);
        drawLine(xB, yB, xC, yC, color);
        drawLine(xC, yC, xD, yD, color);
        drawLine(xD, yD, xA, yA, color);
        graphics.fillPolygon(xPoints1, yPoints1, 4);

        int[] xPoints2 = {xB, xF, xG, xC};
        int[] yPoints2 = {yB, yF, yG, yC};
        drawLine(xB, yB, xF, yF, color);
        drawLine(xF, yF, xG, yG, color);
        drawLine(xG, yG, xC, yC, color);
        drawLine(xC, yC, xB, yB, color);
        graphics.fillPolygon(xPoints2, yPoints2, 4);

        int[] xPoints3 = {xE, xF, xG, xH};
        int[] yPoints3 = {yE, yF, yG, yH};
        drawLine(xE, yE, xF, yF, color);
        drawLine(xF, yF, xG, yG, color);
        drawLine(xG, yG, xH, yH, color);
        drawLine(xH, yH, xE, yE, color);
        graphics.fillPolygon(xPoints3, yPoints3, 4);

        int[] xPoints4 = {xA, xB, xF, xE};
        int[] yPoints4 = {yA, yB, yF, yE};
        drawLine(xA, yA, xB, yB, color);
        drawLine(xB, yB, xF, yF, color);
        drawLine(xF, yF, xE, yE, color);
        drawLine(xE, yE, xA, yA, color);
        graphics.fillPolygon(xPoints4, yPoints4, 4);

        int[] xPoints5 = {xD, xC, xG, xH};
        int[] yPoints5 = {yD, yC, yG, yH};
        drawLine(xD, yD, xC, yC, color);
        drawLine(xC, yC, xG, yG, color);
        drawLine(xG, yG, xH, yH, color);
        drawLine(xH, yH, xD, yD, color);
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
        drawLine(xA, yA, xB, yB, color);
        drawLine(xB, yB, xC, yC, color);
        drawLine(xC, yC, xD, yD, color);
        drawLine(xD, yD, xA, yA, color);
        graphics.fillPolygon(xPoints1, yPoints1, 4);

        int[] xPoints2 = {xB, xF, xG, xC};
        int[] yPoints2 = {yB, yF, yG, yC};
        drawLine(xB, yB, xF, yF, color);
        drawLine(xF, yF, xG, yG, color);
        drawLine(xG, yG, xC, yC, color);
        drawLine(xC, yC, xB, yB, color);
        graphics.fillPolygon(xPoints2, yPoints2, 4);

        int[] xPoints3 = {xE, xF, xG, xH};
        int[] yPoints3 = {yE, yF, yG, yH};
        drawLine(xE, yE, xF, yF, color);
        drawLine(xF, yF, xG, yG, color);
        drawLine(xG, yG, xH, yH, color);
        drawLine(xH, yH, xE, yE, color);
        graphics.fillPolygon(xPoints3, yPoints3, 4);

        int[] xPoints4 = {xA, xB, xF, xE};
        int[] yPoints4 = {yA, yB, yF, yE};
        drawLine(xA, yA, xB, yB, color);
        drawLine(xB, yB, xF, yF, color);
        drawLine(xF, yF, xE, yE, color);
        drawLine(xE, yE, xA, yA, color);
        graphics.fillPolygon(xPoints4, yPoints4, 4);

        int[] xPoints5 = {xD, xC, xG, xH};
        int[] yPoints5 = {yD, yC, yG, yH};
        drawLine(xD, yD, xC, yC, color);
        drawLine(xC, yC, xG, yG, color);
        drawLine(xG, yG, xH, yH, color);
        drawLine(xH, yH, xD, yD, color);
        graphics.fillPolygon(xPoints5, yPoints5, 4);
    }
    public void drawRectangle (double[][] coords, Color color) {
        int xA = (int) coords[0][0]; int yA = (int) coords[1][0];
        int xB = (int) coords[0][1]; int yB = (int) coords[1][1];
        int xC = (int) coords[0][2]; int yC = (int) coords[1][2];
        int xD = (int) coords[0][3]; int yD = (int) coords[1][3];

        drawLine(xA, yA, xB, yB, color);
        drawLine(xB, yB, xC, yC, color);
        drawLine(xC, yC, xD, yD, color);
        drawLine(xD, yD, xA, yA, color);

        int totalPuntos = coords[0].length;

        int sumaX = 0;

        for (int i = 0; i < totalPuntos; i++) {
            sumaX += (int) coords[0][i];
        }

        int centroX = sumaX / totalPuntos;

        FloodFill(centroX, yA + 1, color, color);
        FloodFill(centroX, yC - 1, color, color);
    }
    public void FloodFill (int x, int y, Color borde, Color relleno) {
        Stack<Point> coordinates = new Stack<>();
        coordinates.push(new Point(x, y));

        while (!coordinates.isEmpty()) {
            Point coordinate = coordinates.pop();

            if (isWithinBounds(coordinate.x, coordinate.y) && getPixel(coordinate.x, coordinate.y) != borde.getRGB() && getPixel(coordinate.x, coordinate.y) != relleno.getRGB()) {
                putPixel(coordinate.x, coordinate.y, relleno);

                coordinates.push(new Point(coordinate.x + 1, coordinate.y));
                coordinates.push(new Point(coordinate.x - 1, coordinate.y));
                coordinates.push(new Point(coordinate.x, coordinate.y + 1));
                coordinates.push(new Point(coordinate.x, coordinate.y - 1));
            }
        }
    }
    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < bufferFondo.getWidth() && y >= 0 && y < bufferFondo.getHeight();
    }
}
