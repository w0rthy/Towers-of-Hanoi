package towers.of.hanoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import static towers.of.hanoi.TowersOfHanoi.*;

final public class HanoiPane {
    
    static JFrame frame;
    static BufferedImage bi;
    static Font fon;

    static Point lastswap;
    static Point lastlastswap;
    
    static void open(){
        frame = new JFrame();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(d.width,d.height-36);
        frame.setLocation(0, 0);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bi = new BufferedImage(1024,1024,BufferedImage.TYPE_INT_RGB);
        fon = new Font("TimesRoman", Font.PLAIN, (int)(512.0/PIECES));
        
        new Thread() {
            public void run(){
                while(true)
                    drawHanoi();
            }
        }.start();
    }
    
    static void drawHanoi(){
        Graphics2D g = (Graphics2D)bi.getGraphics();
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1024, 1024);
        g.setColor(Color.BLUE);
        for(int i = 0; i < towers.length; i++)
            for(int j = 0; j < towers[i].size(); j++){
                int val = 0;
                try{val = towers[i].get(j);}catch(ArrayIndexOutOfBoundsException e){}
                int w = val*(int)(1024.0/PIECES/4);
                int h = (int)(994.0/PIECES);
                int x = (int)((i+1)/4.0*1024) - w/2;
                int y = (int)(1024-(j+1)*(994.0/PIECES));
                g.setColor(Color.getHSBColor(val*(255f/8), 1f, 1f));
                g.fillRect(x, y, w, h);
                g.setColor(Color.WHITE);
                g.setFont(fon);
                String str = ""+val;
                g.drawString(str, x+w/2-fon.getSize()/4*str.length(), y+h/2+fon.getSize()/6);
            }
//        if(lastlastswap!=null){
//            g.setColor(new Color(255,0,0,64));
//            int one = lastlastswap.x;
//            int two = lastlastswap.y;
//            int x1 = (int)((one+1)/4.0*1024);
//            int y1 = 1011-towers[one].size()*15;
//            int x2 = (int)((two+1)/4.0*1024);
//            int y2 = 1011-towers[two].size()*15;
//            g.drawLine(x1, y1, x2, y2);
//        }
        if(lastswap!=null){
            g.setColor(Color.RED);
            int one = lastswap.x;
            int two = lastswap.y;
            int x1 = (int)((one+1)/4.0*1024);
            int y1 = (int)(1011-towers[one].size()*(994.0/PIECES));
            int x2 = (int)((two+1)/4.0*1024);
            int y2 = (int)(1011-towers[two].size()*(994.0/PIECES));
            g.drawLine(x1, y1, x2, y2);
        }
        frame.getGraphics().drawImage(bi, 0, 0, frame.getWidth(), frame.getHeight(), null);
    }
    
    static void setLastSwap(Point p){
        if(lastswap!=null)
            lastlastswap = lastswap;
        lastswap = p;
    }
}
