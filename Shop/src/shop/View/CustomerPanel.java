/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import shop.Model.Item;

/**
 *
 * @author Zerkish
 */
public class CustomerPanel extends SidePanel {
    
    private JTextArea taItems = new JTextArea();
    private ArrayList<Item> cart = new ArrayList<Item>();
    private JLabel lblUser = new JLabel();
    private JScrollPane scroll = new JScrollPane(taItems);
    
    public CustomerPanel()
    {
        //setBackground(Color.YELLOW);
        initGUI();
    }
    
    private void initGUI()
    {
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
      
        
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1.0f;
        gbc.weighty = 1.0f;
        gbc.fill = GridBagConstraints.BOTH;
        
        taItems.setSize(400, 300);
        taItems.setEditable(false);
        taItems.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(scroll, gbc);
        
        JButton btnCheckout = new JButton("Checkout");
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.5f;
        add(btnCheckout, gbc);
        
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0f;
        gbc.weighty = 0.0f;
        add(lblUser, gbc);
        lblUser.setSize(400, 20);
    }
    
    public void setUser(String username)
    {
        lblUser.setText(String.format("Inloggad: (%s)", username));
    }
    
    public void addItemToCart(Item item)
    {
        boolean addNew = true;
        for(Item i : cart)
        {
            if (i.getProductId() == item.getProductId())
            {
                i.setAmount(i.getAmount() + item.getAmount());
                addNew = false;
            }
        }
        
        if (addNew)
        {
            Item i = item.makeGenericCopy();
            i.setAmount(item.getAmount());
            cart.add(i);
        }
        
        taItems.setText("");
        int totalPrice = 0;
        for(Item i : cart)
        {
            String str = String.format("(%d)%-10s%4d(%d)kr\n", i.getAmount(),
                i.getName(), i.getPrice(), i.getAmount() * i.getPrice());            
            
            taItems.append(str);
            
            totalPrice += i.getAmount() * i.getPrice();
        }
        
        taItems.append(String.format("\nTotalt: %dkr", totalPrice));
    }
    
}
