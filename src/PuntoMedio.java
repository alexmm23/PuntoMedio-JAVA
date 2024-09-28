import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PuntoMedio extends JFrame {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem circulo, cuadrado, linea, ovalo;
    private double centroX, centroY, radio;
    private int figuraSeleccionada = 0; // 1 - Circulo, 2 - Cuadrado, 3 - Linea, 4 - Ovalo
    private BufferedImage buffer;
    private Color red = Color.RED;
    private DrawPanel drawPanel;

    public PuntoMedio() {
        // Crear menú
        menuBar = new JMenuBar();
        menu = new JMenu("Figuras");
        circulo = new JMenuItem("Círculo");
        cuadrado = new JMenuItem("Rectangulo");
        linea = new JMenuItem("Línea");
        ovalo = new JMenuItem("Óvalo");

        menu.add(circulo);
        menu.add(cuadrado);
        menu.add(linea);
        menu.add(ovalo);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Para que no se borre el menu xd
        drawPanel = new DrawPanel();
        drawPanel.setPreferredSize(new Dimension(500, 500));
        add(drawPanel, BorderLayout.CENTER);


        circulo.addActionListener(e -> {
            PaneNumerico centroXDialog = new PaneNumerico(this, "Ingrese el centro en X");
            if (!centroXDialog.isValid()) return;
            centroX = centroXDialog.getResult();

            PaneNumerico centroYDialog = new PaneNumerico(this, "Ingrese el centro en Y");
            if (!centroYDialog.isValid()) return;
            centroY = centroYDialog.getResult();

            PaneNumerico radioDialog = new PaneNumerico(this, "Ingrese el radio");
            if (!radioDialog.isValid()) return;
            radio = radioDialog.getResult();
            //System.out.println(centroX + " " + centroY + " " + radio);
            figuraSeleccionada = 1;
            circulo((int) centroX, (int) centroY, (int) radio);
            repaint();
        });
        linea.addActionListener(e -> {
            PaneNumerico x1Dialog = new PaneNumerico(this, "Ingrese la coordenada X1");
            if (!x1Dialog.isValid()) return;
            int x1 = (int) x1Dialog.getResult();
            PaneNumerico y1Dialog = new PaneNumerico(this, "Ingrese la coordenada Y1");
            if (!y1Dialog.isValid()) return;
            int y1 = (int) y1Dialog.getResult();
            PaneNumerico x2Dialog = new PaneNumerico(this, "Ingrese la coordenada X2");
            if (!x2Dialog.isValid()) return;
            int x2 = (int) x2Dialog.getResult();
            PaneNumerico y2Dialog = new PaneNumerico(this, "Ingrese la coordenada Y2");
            if (!y2Dialog.isValid()) return;
            int y2 = (int) y2Dialog.getResult();
            figuraSeleccionada = 3;
            linea(x1, y1, x2, y2);
            repaint();
        });
        ovalo.addActionListener(e -> {
            PaneNumerico centroXDialog = new PaneNumerico(this, "Ingrese el centro en X");
            if (!centroXDialog.isValid()) return;
            centroX = centroXDialog.getResult();
            PaneNumerico centroYDialog = new PaneNumerico(this, "Ingrese el centro en Y");
            if (!centroYDialog.isValid()) return;
            centroY = centroYDialog.getResult();
            PaneNumerico radiusXDialog = new PaneNumerico(this, "Ingrese el radio en X");
            if (!radiusXDialog.isValid()) return;
            int radiusX = (int) radiusXDialog.getResult();
            PaneNumerico radiusYDialog = new PaneNumerico(this, "Ingrese el radio en Y");
            if (!radiusYDialog.isValid()) return;
            int radiusY = (int) radiusYDialog.getResult();
            figuraSeleccionada = 4;
            ovalo((int) centroX, (int) centroY, radiusX, radiusY);
            repaint();
        });
        cuadrado.addActionListener(e -> {
            PaneNumerico startXDialog = new PaneNumerico(this, "Ingrese la coordenada X inicial");
            if (!startXDialog.isValid()) return;
            int startX = (int) startXDialog.getResult();
            PaneNumerico startYDialog = new PaneNumerico(this, "Ingrese la coordenada Y inicial");
            if (!startYDialog.isValid()) return;
            int startY = (int) startYDialog.getResult();
            PaneNumerico sideLengthDialog = new PaneNumerico(this, "Ingrese el tamaño del lado");
            if (!sideLengthDialog.isValid()) return;
            int width = (int) sideLengthDialog.getResult();
            PaneNumerico heightDialog = new PaneNumerico(this, "Ingrese el alto del rectángulo");
            if (!heightDialog.isValid()) return;
            int height = (int) heightDialog.getResult();
            figuraSeleccionada = 5;
            rectangulo(startX, startY, width, height);
            repaint();
        });


        setTitle("Dibujar Figuras");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

   //Panel para que no se muera el menu
    private class DrawPanel extends JPanel {
        public DrawPanel() {
            buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
            Graphics graPixel = buffer.getGraphics();
            graPixel.setColor(Color.WHITE); // Fondo blanco
            graPixel.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(buffer, 0, 0, this);
        }
    }

    public void putPixel(int x, int y, Color color) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, color.getRGB());
        }
    }

    public void circulo(int centerX, int centerY, int radius) {
        int x = 0;
        int y = radius;
        int d = 1 - radius;

        while (x <= y) {
            putPixel(centerX + x, centerY + y, red);
            putPixel(centerX - x, centerY + y, red);
            putPixel(centerX + x, centerY - y, red);
            putPixel(centerX - x, centerY - y, red);
            putPixel(centerX + y, centerY + x, red);
            putPixel(centerX - y, centerY + x, red);
            putPixel(centerX + y, centerY - x, red);
            putPixel(centerX - y, centerY - x, red);
            x++;

            if (d < 0) {
                d = d + 2 * x + 1;
            } else {
                y--;
                d = d + 2 * x - 2 * y + 1;
            }
        }
    }

    public void cuadrado(int centerX, int centerY, int sideLength) {
        int halfSide = sideLength / 2;

        int x1 = centerX - halfSide;
        int y1 = centerY - halfSide;
        int x2 = centerX + halfSide;
        int y2 = centerY + halfSide;

        linea(x1, y1, x2, y1);
        linea(x2, y1, x2, y2);
        linea(x2, y2, x1, y2);
        linea(x1, y2, x1, y1);
    }

    public void rectangulo(int startX, int startY, int width, int height) {
        linea(startX, startY, startX + width, startY);
        linea(startX, startY, startX, startY + height);
        linea(startX + width, startY, startX + width, startY + height);
        linea(startX, startY + height, startX + width, startY + height);
    }


    public void linea(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, red);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void ovalo(int centerX, int centerY, int radiusX, int radiusY) {
        int x = 0;
        int y = radiusY;

        double dx = 2 * radiusY * radiusY * x;
        double dy = 2 * radiusX * radiusX * y;

        double d1 = (radiusY * radiusY) - (radiusX * radiusX * radiusY) + (0.25 * radiusX * radiusX);

        while (dx < dy) {
            putPixel(centerX + x, centerY + y, red);
            putPixel(centerX - x, centerY + y, red);
            putPixel(centerX + x, centerY - y, red);
            putPixel(centerX - x, centerY - y, red);

            if (d1 < 0) {
                x++;
                dx += 2 * radiusY * radiusY;
                d1 += dx + (radiusY * radiusY);
            } else {
                x++;
                y--;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                d1 += dx - dy + (radiusY * radiusY);
            }
        }
        double d2 = ((radiusY * radiusY) * (x + 0.5) * (x + 0.5)) + ((radiusX * radiusX) * (y - 1) * (y - 1)) - (radiusX * radiusX * radiusY * radiusY);

        while (y >= 0) {
            putPixel(centerX + x, centerY + y, red);
            putPixel(centerX - x, centerY + y, red);
            putPixel(centerX + x, centerY - y, red);
            putPixel(centerX - x, centerY - y, red);

            if (d2 > 0) {
                y--;
                dy -= 2 * radiusX * radiusX;
                d2 += (radiusX * radiusX) - dy;
            } else {
                x++;
                y--;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                d2 += dx - dy + (radiusX * radiusX);
            }
        }
    }


    public static void main(String[] args) {
        PuntoMedio puntoMedio = new PuntoMedio();
        puntoMedio.setVisible(true);
    }
}
