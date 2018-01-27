/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D.*;
/**
 *
 * @author lx_user
 */

public class FunctionPanel extends JPanel{
    public static String function;
    public static double lowerBoundY;
    public static double upperBoundY;
    public static double lowerBoundX;
    public static double upperBoundX;
    public static double resolution;
    public static double x_coor;
    public static double y_coor;
    public static double scale;
    public static boolean redrawFunction;   //lock redrawFunction to save time
    public static void restoreDefaultSettings(){
        function    = "";
        lowerBoundY = -10.0;
        lowerBoundX = -10.0;
        upperBoundX = 10.0;
        upperBoundY = 10.0;
        resolution  = 0.02;
        x_coor      = 0.0;
        y_coor      = 0.0;
        scale       = 0.01;
        redrawFunction = false;
        //redraw function must be set differently
    }
    public void paintComponent(Graphics g2){
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(51,59,84));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(170,64,255));
        g.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
        g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
        if(!redrawFunction || function.contains("function"))
            return;
        redrawFunction = false;
        double x1 = -1e30;
        double x2 = lowerBoundX;
        double y1 = -1e30;
        double y2 = -1e30;
        while(x2 <= upperBoundX){
            try{
                String expression = "";
                for(int i = 0; i < function.length(); i++){
                    if(function.charAt(i) == 'x'){
                        expression += x2;
                    }
                    else{
                        expression += function.charAt(i);
                    }
                }
                y2 = ExpressionSolver.evaluate(expression);
            }catch(Exception e){
                System.out.println(e);
            }
            // FUNCTION DRAW CODE STARTS HERE
            double x2Relative = (x2 - lowerBoundX) / (upperBoundX - lowerBoundX);
            double y2Relative = (y2 - lowerBoundY) / (upperBoundY - lowerBoundY);
            double x1Relative = (x2 - lowerBoundX) / (upperBoundX - lowerBoundX);
            double y1Relative = (y2 - lowerBoundY) / (upperBoundY - lowerBoundY);
            g.drawLine((int)(x1Relative * (double)this.getWidth()), (int)((double)this.getHeight() - y1Relative * (double)this.getHeight()), 
                        (int)(x2Relative * (double)this.getWidth()), (int)((double)this.getHeight() - y2Relative * (double)this.getHeight())
                    );
            // FUNCTION DRAW CODE ENDS HERE
            x1 = x2;
            y1 = y2;
            x2 += scale;
        }
    }
}

/*
public class FunctionPanel extends JPanel{
    static String function = "";
    static double lowerBoundY;
    static double upperBoundY;
    static double lowerBoundX;
    static double upperBoundX;
    static double resolution;
    static double setX = 0;
    static double setY = 0;
    static double scale;
    static boolean resize = true;
    public static void setFunction(String f){
        function = f;
        int idx = function.indexOf("=");
        String tmp = "";
        for(int i = idx+1; i < function.length(); i++){
            if(function.charAt(i) != ' ')
                tmp += function.charAt(i);
        }
        function = tmp;
    }
    @Override 
    public void paintComponent(Graphics g2){
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(new Color(51,59,84));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(170,64,255));
        //g.drawLine(this.getWidth()/2, 0, 5*this.getWidth()/10, this.getHeight());
        //g.drawLine(0,5*this.getHeight()/10,this.getWidth(),this.getHeight()/2);
        double xStart = ((upperBoundX-lowerBoundX)/2);
        double yStart = ((upperBoundY-lowerBoundY)/2);
        while(xStart <= upperBoundX){
            g.drawLine((int)Math.round(((xStart-lowerBoundX)/(upperBoundX-lowerBoundX)) * this.getWidth()),
                        0,
                        (int)Math.round(((xStart-lowerBoundX)/(upperBoundX-lowerBoundX)) * this.getWidth()),
                        this.getHeight());
                        
                        
            xStart += scale;
        }
        xStart = ((upperBoundX-lowerBoundX)/2);
        while(xStart >= lowerBoundX){
            g.drawLine((int)Math.round(((xStart-lowerBoundX)/(upperBoundX-lowerBoundX)) * this.getWidth()),
                        0,
                        (int)Math.round(((xStart-lowerBoundX)/(upperBoundX-lowerBoundX)) * this.getWidth()),
                        this.getHeight());
                        
                        
            xStart -= scale;
        }
        //
        while(yStart <= upperBoundY){
            g.drawLine(
                        0,
                        (int)Math.round(this.getHeight() - ((yStart-lowerBoundY)/(upperBoundY-lowerBoundY)) * this.getHeight()),
                        this.getWidth(),
                        (int)Math.round(this.getHeight() - ((yStart-lowerBoundY)/(upperBoundY-lowerBoundY)) * this.getHeight()));
                        
                        
            yStart += scale;
        }
        yStart = ((upperBoundY-lowerBoundY)/2);
        while(yStart >= lowerBoundY){
            g.drawLine(
                        0,
                        (int)Math.round(this.getHeight() - ((yStart-lowerBoundY)/(upperBoundY-lowerBoundY)) * this.getHeight()),
                        this.getWidth(),
                        (int)Math.round(this.getHeight() - ((yStart-lowerBoundY)/(upperBoundY-lowerBoundY)) * this.getHeight()));
                        
                        
            yStart -= scale;
        }
        if(!resize){
            System.out.println();
            double x1 = -10e15, y1 = 10e15, x2, y2 = 0;
            String tmp = new String(function);
            String value1 = "NaN", value2 = "";
            double res = resolution;
            for(x2 = lowerBoundX; x2 < upperBoundX; x2+=res){
                tmp = new String(function);
                try{
                    while(tmp.contains("x")){
                        for(int i = 0; i < tmp.length(); i++){
                            if(tmp.charAt(i) == 'x'){
                                tmp = tmp.substring(0,i) + "("+x2+")" + tmp.substring(i+1);
                                break;
                            }
                        }
                    }
                    value2 = ExpressionSolver.evaluate(ExpressionSolver.trim(tmp));
                    y2 = Double.parseDouble(value2);
                } catch (Exception e){
                }
                if(y2 != setY)
                    g.setColor(new Color(73,197,255));
                if(!value2.equals("NaN") && !value1.equals("NaN") && ((y1 <= upperBoundY && y1 >= lowerBoundY) || (y2 <= upperBoundY && y2 >= lowerBoundY))){
                    //System.out.println(x1+" "+x2+" "+y1+" "+y2);
                    
                    g.drawLine((int)Math.round(((x1-lowerBoundX)/(upperBoundX-lowerBoundX)) * this.getWidth()),
                                   (int)Math.round(this.getHeight() - ((y1-lowerBoundY)/(upperBoundY-lowerBoundY)) * this.getHeight()),
                                   (int)Math.round(((x2-lowerBoundX)/(upperBoundX-lowerBoundX)) * this.getWidth()),
                                   (int)Math.round(this.getHeight() - ((y2-lowerBoundY)/(upperBoundY-lowerBoundY)) * this.getHeight())
                                );
                    res = resolution;
                }
                //insert optimization code here
                try{
                    double xLen = x2 - x1;
                    double yLen = y2 - y1;
                    double mag = Math.sqrt(xLen*xLen + yLen*yLen);
                    double coeff = resolution/mag;
                    res = xLen*coeff;
                    if(res < 10e-6)
                        res = 10e-6;
                }catch(Exception e){
                    res = 1;
                }
                y1 = y2;
                x1 = x2;
                value1 = value2;
            }
            resize = false;
        }
    }
}
*/