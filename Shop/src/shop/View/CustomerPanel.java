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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import shop.Model.Item;

// This class implements the functionality for customers
// It provides a textbox that lists all items currently in the cart
// along with the total sum of these items.
// Customers may rate items upon checking out.
public class CustomerPanel extends SidePanel {
    
    private JTextArea taItems = new JTextArea();
    private ArrayList<Item> cart = new ArrayList<Item>();
    private JLabel lblUser = new JLabel();
    private JScrollPane scroll = new JScrollPane(taItems);
    private ShopListener listener;
    private String userName;
    
    public CustomerPanel(ShopListener listener)
    {
        this.listener = listener;
        //setBackground(Color.YELLOW);
        initGUI();
    }
    
    // Creates the GUI.
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
        
        // This action listener implements the functionality for rating items upon
        // checking out.
        btnCheckout.addActionListener((ActionEvent e) -> {
            boolean result = JOptionPane.showConfirmDialog(null,
                    "Would you like to rate the items?") == JOptionPane.OK_OPTION;
            
            if (result) {
                String ssn = listener.getUserSSN(userName);
                
                boolean rated = false;
                for(Item i : cart) {
                    int rating = 0;
                    do
                    {                   
                        String str = JOptionPane.showInputDialog(null,
                                String.format("Rate %s (1-5)", i.getName()));
                        try {
                            rating = Integer.parseInt(str);
                            if (rating < 1 || rating > 5) {
                                throw new NumberFormatException();
                            }                            
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter a number from 1-5");
                            continue;
                        }
                        rated = true;
                    } while(!rated);
                    
                    // Notify the DB about the new score.
                    listener.setItemScore(i, rating, ssn);                    
                }                
            }            
        });
        
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0f;
        gbc.weighty = 0.0f;
        add(lblUser, gbc);
        lblUser.setSize(400, 20);
    }
    
    // Set the name of the current user.
    public void setUser(String username)
    {
        lblUser.setText(String.format("Inloggad: (%s)", username));
        userName = username;
    }
    
    // Adds an item to the cart.
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
            String str = String.format("(%d)%-10s%4.2f(%.2f)kr\n", i.getAmount(),
                i.getName(), i.getPrice(), i.getAmount() * i.getPrice());            
            
            taItems.append(str);
            
            totalPrice += i.getAmount() * i.getPrice();
        }
        
        taItems.append(String.format("\nTotalt: %dkr", totalPrice));
    }
}
