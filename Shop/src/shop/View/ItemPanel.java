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
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(btnAddToCart, gbc);
        btnAddToCart.addActionListener((ActionEvent e) -> {
            if (listener != null) {
                listener.onItem(item);
            }
        });
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        
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
        lblItemPrice.setText(String.format("Price: %-5.2fkr", item.getPrice()));
        
        doLayout();
    }
    
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
}
