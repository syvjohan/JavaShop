package shop.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import shop.Model.Item;

// This class is a layout aid for the product panel
// it displays the data for one single item and is configured
// as a horizontal panel.
public class ItemPanel extends JPanel {
    
    private Item item;
    
    private JLabel lblItemName = new JLabel();
    private JLabel lblItemStock = new JLabel();
    private JLabel lblItemPrice = new JLabel();
    private JLabel lblScore = new JLabel();
    private JButton btnAddToCart = new JButton("Buy");
    private ItemListener listener;
    
    public ItemPanel(Item item, ItemListener listener) {
        this.item = item;
        this.listener = listener;
        
        //setBackground(Color.CYAN);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        
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
        add(lblScore, gbc);
        
        gbc.gridx = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(btnAddToCart, gbc);
        
        // This action listener gets triggered when someone presses
        // the button associated with the item, for customers this means
        // to "buy" the item, for staff is means to begin editing it.
        btnAddToCart.addActionListener((ActionEvent e) -> {
            if (listener != null) {
                Item i = item.makeGenericCopy();
                i.setAmount(1);
                listener.onItem(i);
            }
        });
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        
        lblItemName.setAlignmentX(LEFT_ALIGNMENT);
        lblItemName.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        lblItemStock.setAlignmentX(LEFT_ALIGNMENT);
        lblItemStock.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        lblItemPrice.setAlignmentX(LEFT_ALIGNMENT);
        lblItemPrice.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        lblScore.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        lblScore.setAlignmentX(LEFT_ALIGNMENT);
        
        lblItemName.setText(String.format(" %-10s", item.getName()));
        lblItemStock.setText(String.format("Stock: %-4d", item.getAmount()));
        lblItemPrice.setText(String.format("Price: %-5.2fkr", item.getPrice()));
        lblScore.setText(String.format("Score: %.1f/5,0", item.getScore()));
        
        doLayout();
    }
    
    // Notifies the itempanel about changes to the user level
    // For anonymous users, we disable the buy button
    // for staff/customers, it becomes enabled and the text is set accordingly.
    public void setUserLevel(int n) {
        if ( n == 0 ) {
            btnAddToCart.setEnabled(false);
        } else if ( n >= 1 ) {
            btnAddToCart.setEnabled(true);
        }
        
        if ( n == 2 ) {
            btnAddToCart.setText("Edit");
        } else {
            btnAddToCart.setText("Buy");
        }
    }
    
    // Return the item associated with the panel.
    public Item getItem() {
        return item;
    }
}
