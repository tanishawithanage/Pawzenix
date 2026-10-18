/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pawzenix;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import view.ViewMain;


/**
 *
 * @author TANISHA SANJANA
 */
public class Pawzenix {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {

            JFrame window = new JFrame("Pawzenix");

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setContentPane(new ViewMain());
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);

        });
    }
    
}
