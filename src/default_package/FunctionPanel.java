/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author lx_user
 */
public class FunctionPanel extends JPanel{
    static String function = "";
    static double lowerBound;
    static double upperBound;
    static double resolution;
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
    public static void setLowerBound(double lowerBound){
        FunctionPanel.lowerBound = lowerBound;
    }
    public static void setUpperBound(double upperBound){
        FunctionPanel.upperBound = upperBound;
    }
    public static void setResolution(double resolution){
        FunctionPanel.resolution = resolution;
    }
    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(51,59,84));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(170,64,255));
        g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
        g.drawLine(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
        if(!resize){
            double x1 = -10e9, y1 = 10e9, x2, y2 = 0;
            String tmp = new String(function);
            String value1 = "NaN", value2 = "";
            for(x2 = lowerBound; x2 < upperBound; x2+=resolution){
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
                if(Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2)) < 10 && !value2.equals("NaN") && !value1.equals("NaN") && ((y1 <= upperBound && y1 >= lowerBound) || (y2 <= upperBound && y2 >= lowerBound))){
                    //System.out.println(x1+" "+x2+" "+y1+" "+y2);
                    if(Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2)) > 2){
                        g.fillOval(this.getWidth()/2+(int)((x1/upperBound)*(this.getWidth()/2)),
                                this.getHeight()/2-(int)((y1/upperBound)*(this.getHeight()/2)),
                                1,1
                        );
                    }
                    else{
                        g.drawLine(this.getWidth()/2+(int)((x1/upperBound)*(this.getWidth()/2)),
                                    this.getHeight()/2-(int)((y1/upperBound)*(this.getHeight()/2)),
                                    this.getWidth()/2+(int)((x2/upperBound)*(this.getWidth()/2)),
                                    this.getHeight()/2-(int)((y2/upperBound)*(this.getHeight()/2))
                        );
                    }
                }
                y1 = y2;
                x1 = x2;
                value1 = value2;
            }
        }
    }
}
