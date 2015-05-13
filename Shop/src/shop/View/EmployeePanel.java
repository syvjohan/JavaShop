package shop.View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import shop.Model.Item;

    
public class EmployeePanel extends SidePanel {
    
    private JTextField tfName = new JTextField();
    private JTextField tfCategory = new JTextField();
    private JTextField tfPrice = new JTextField();
    private JTextField tfStock = new JTextField();
    private JLabel lblUser = new JLabel();
    
    private JButton btnUpdate = new JButton("Update");
    private JButton btnAdd = new JButton("Add");
    
    private ShopListener listener;
    private Item currentItem;
    
    public EmployeePanel(ShopListener listener) {
        setBackground(Color.GRAY);
        this.listener = listener;
        initGUI();
        
        btnUpdate.addActionListener((ActionEvent e)-> {
            Item item = currentItem.makeGenericCopy();
            item.setName(tfName.getText());
            item.setCategory(tfCategory.getText());
            item.setScore(0);
            
            try {
                item.setPrice(Float.parseFloat(tfPrice.getText()));
                item.setAmount(Integer.parseInt(tfStock.getText()));
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(null, "Felaktig numerisk data");
                return;
            }
            
            listener.updateItem(item);            
        });
        
        btnAdd.addActionListener((ActionEvent e)-> {
            Item item = new Item();
            item.setName(tfName.getText());
            item.setCategory(tfCategory.getText());
            item.setScore(0);
            
            try {
                item.setPrice(Float.parseFloat(tfPrice.getText()));
                item.setAmount(Integer.parseInt(tfStock.getText()));
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(null, "Felaktig numerisk data");
                return;
            }
            
            listener.addItem(item);
        });
    }
    
    private void initGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.weightx = 1.0f;
        gbc.weighty = 0.05f;
        gbc.gridwidth = 2;
        add( lblUser, gbc );
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.25f;
        gbc.weighty = 0.05f;
        gbc.gridwidth = 1;
        
        add(new JLabel("Produkt Namn:"), gbc); 
        gbc.gridy = 2;
        add(new JLabel("Product Category:"), gbc);
        gbc.gridy = 3;
        add(new JLabel("Product Price:"), gbc);
        gbc.gridy = 4;
        add(new JLabel("Product Stock:"), gbc);
        
        gbc.gridy = 5;
        add( btnUpdate, gbc );
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add( btnAdd, gbc );
        
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.weightx = 0.75f;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add( tfName, gbc );
        gbc.gridy = 2;
        add( tfCategory, gbc );
        gbc.gridy = 3;
        add( tfPrice, gbc );
        gbc.gridy = 4;
        add( tfStock, gbc );

        // Adjustment
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.8f;
        gbc.weightx = 1.0f;
        gbc.gridwidth = 2;
        JPanel pnl = new JPanel();
        pnl.setOpaque(false);
        add( pnl, gbc );
        
        btnUpdate.setEnabled(false);
    }
    
    public void editItem(Item item) {
        currentItem = item;
        tfName.setText(item.getName());
        tfCategory.setText(item.getCategory());
        tfPrice.setText(String.format("%.2f", item.getPrice()));
        tfStock.setText(String.format("%d", item.getAmount()));
        btnUpdate.setEnabled(true);
    }
    
    public void setUser(String user) {
        lblUser.setText("Inloggad: (" + user + ")");
    }
}
