import javax.swing.SwingUtilities;

import view.MainFrame;



public class MainClass {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                new MainFrame().createGUI();
            }
        });
    }

}
