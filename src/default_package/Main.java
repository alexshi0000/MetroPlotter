package default_package;
import javax.swing.*;
import java.awt.*;
public class Main {
    public static void mainLoop(String f){
        FunctionPanel.setFunction(f);
       
        MetroGUI.window.getGraph().add(new FunctionPanel(),BorderLayout.CENTER);
        MetroGUI.window.setVisible(true);
    }
    public static void main(String[] args) {
        FunctionPanel.setResolution(0.001);
        FunctionPanel.setLowerBound(-40);
        FunctionPanel.setUpperBound(40);
        MetroGUI.main(null);
    }
}
