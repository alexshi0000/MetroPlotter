/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;
import java.util.*;
/**
 *
 * @author lx_user
 */
public class PointOfInterest {
    
    //make the arraylist public
    public static ArrayList<PointOfInterest> arr = new ArrayList<PointOfInterest>();
    private double x, y;
    private String description, hiddenDescription;
    
    public static void resetPointsOfInterest(){
        FunctionPanel.drawPOI = true;
        arr.clear();
    }
    
    public static void addPointOfInterest(PointOfInterest p){
        arr.add(p);
    }
    
    public PointOfInterest(double x, double y, String description, String hiddenDescription){
        this.x = x;
        this.y = y;
        this.description = description;
        this.hiddenDescription = hiddenDescription;
    }
    
    private PointOfInterest(){
        //cannot initialize without params
    }
    
    public void setDescriptionHidden(String hiddenDescription){
        //if mouse hovers over it, then print in detail what the description is
        this.hiddenDescription = hiddenDescription;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public String getDecription(){
        return description;
    }
    
    public String getHiddenDescription(){
        return hiddenDescription;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
}
