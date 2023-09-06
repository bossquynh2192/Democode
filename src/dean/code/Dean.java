/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dean.code;

import dean.login;

/**
 *
 * @author minhh
 */
public class Dean {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       login loginFrame = new login();
       loginFrame.setVisible(true);
       loginFrame.pack();
       loginFrame.setLocationRelativeTo(null);
    }
}
