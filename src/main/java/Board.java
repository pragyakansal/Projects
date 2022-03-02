import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Color;

public class Board extends JFrame {

    private static final long serialVersionUID = -2785069194987795891L;

    private static int RECT_WIDTH = 100;
    private static final int RECT_LENGTH = 100;
    private int playColumn;
    private Color playColor;

    public Board(int playColumn, Color playColor) {
        this.playColumn = playColumn;
        this.playColor = playColor;
        setTitle("Board");
        setSize(10000, 10000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        //super.paint(g);

        int x = 0;
        int y = 0;
        int arcWidth = RECT_WIDTH/2;
        int arcLength = RECT_LENGTH/2;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                g.drawRect(x, y, RECT_WIDTH, RECT_LENGTH);
                g.drawOval(x, y, RECT_WIDTH, RECT_LENGTH);
                g.drawRect(0, 0, RECT_WIDTH, RECT_LENGTH);
                g.setColor(Color.BLUE);
                g.fillRect(180, 0, 0, RECT_LENGTH);
                g.setColor(Color.BLUE);
                g.drawOval(0, 0, RECT_WIDTH, RECT_LENGTH);
                g.setColor(Color.WHITE);
                g.fillOval(x, y, RECT_WIDTH, RECT_LENGTH);
                g.setColor(Color.BLUE);
                x = x + RECT_WIDTH;
                //System.out.println ("x: " + x);
                if (column == playColumn) {
                    g.setColor(playColor);
                    g.fillOval(x, y, RECT_WIDTH, RECT_LENGTH);
                    g.setColor(Color.BLUE);
                }
            }
            x = 0;
            y = y + RECT_LENGTH;
            //System.out.println ("y: " + y);
        }
    }

    public static void main(String[] args) {
        Board t = new Board(2, Color.RED);
        //t.paint(null);
    }
}



