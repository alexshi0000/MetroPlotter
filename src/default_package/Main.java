package default_package;
import javax.swing.*;
import java.awt.*;
public class Main {
    public static void mainLoop(String f){
        FunctionPanel.setFunction(f);
        FunctionPanel.setY = 0;
        FunctionPanel.setX = 0;
        FunctionPanel.lowerBoundX = -9;
        FunctionPanel.upperBoundX = 9;
        FunctionPanel.lowerBoundY = -9;
        FunctionPanel.upperBoundY = 9;
        MetroGUI.window.getGraph().add(new FunctionPanel(),BorderLayout.CENTER);
        MetroGUI.window.setVisible(true);
    }
    public static void main(String[] args) {
        FunctionPanel.resolution = 0.1;
        FunctionPanel.lowerBoundX = -9;
        FunctionPanel.upperBoundX = 9;
        FunctionPanel.lowerBoundY = -9;
        FunctionPanel.upperBoundY = 9;
        FunctionPanel.scale = 1.0;
        MetroGUI.main(null);
    }
}
