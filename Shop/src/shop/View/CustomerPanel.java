/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Zerkish
 */
public class CustomerPanel extends SidePanel {
    
    public CustomerPanel()
    {
        setBackground(Color.YELLOW);
        initGUI();
    }
    
    private void initGUI()
    {
        JButton btn = new JButton("Purchase Stuffz!");
        
        add(btn);
        
        btn.addActionListener((ActionEvent e) -> 
        {
           btn.setText("CLICKEDZZZ");
        });
    }
    
}
