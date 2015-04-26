/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import shop.Model.Item;

/**
 *
 * @author Zerkish
 */
public class ItemPanel extends JPanel {
    
    private Item item;
    
    private JLabel lblItemName = new JLabel();
    private JLabel lblItemStock = new JLabel();
    private JLabel lblItemPrice = new JLabel();
    private JButton btnAddToCart = new JButton("Buy");
    
    public ItemPanel(Item item, ActionListener listener)
    {
        this.item = item;
        
        //setBackground(Color.CYAN);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx  = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25f;
        gbc.weighty = 1.0f;
        
        add(lblItemName, gbc);
        
        gbc.gridx  = 1;
        add(lblItemPrice, gbc);
        gbc.gridx = 2;
        add(lblItemStock, gbc);
        gbc.gridx = 3;
        add(btnAddToCart, gbc);
        btnAddToCart.addActionListener(listener);
        
        lblItemName.setAlignmentX(LEFT_ALIGNMENT);
        //lblItemName.setOpaque(true);
        //lblItemName.setBackground(Color.RED);
        lblItemName.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        lblItemStock.setAlignmentX(LEFT_ALIGNMENT);
        //lblItemStock.setOpaque(true);
        //lblItemStock.setBackground(Color.BLUE);
        lblItemStock.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        lblItemPrice.setAlignmentX(LEFT_ALIGNMENT);
        //lblItemPrice.setOpaque(true);
        //lblItemPrice.setBackground(Color.GREEN);
        lblItemPrice.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        lblItemName.setText(String.format(" %-10s", item.getName()));
        lblItemStock.setText(String.format("Stock: %-4d", item.getAmount()));
        lblItemPrice.setText(String.format("Price: %-5d", item.getPrice()));
        
        doLayout();
    }
}
