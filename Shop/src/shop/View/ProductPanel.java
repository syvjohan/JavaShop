package shop.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import shop.Model.Item;

// This class implements the functionality to display items in the shop
// it uses the helper class ItemPanel to list all items as a vertical list
// which is managed by a JScrollPane.
// It also provides filtering for items based on category.
public class ProductPanel extends JPanel {
    private ItemListener listener;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel panel = new JPanel();
    private JScrollPane scroll = new JScrollPane(panel);
    private ArrayList<ItemPanel> items = new ArrayList();
    private JComboBox comboFilters = new JComboBox();
    
    public ProductPanel(ItemListener listener)
    {
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0f;
        gbc.weighty = 0.0f;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout( new BorderLayout() );
        filterPanel.add(new JLabel("Filter:  "), BorderLayout.WEST );
        filterPanel.add(comboFilters);
        add(filterPanel, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0f;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);
        
        scroll.createVerticalScrollBar();
        this.listener = listener;
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        // Implements filtering functionality.
        comboFilters.addItemListener((ItemEvent e) -> {
            String category = e.getItem().toString();
            for (ItemPanel pnl : items) {
                if (category.equals(pnl.getItem().getCategory()) || category.equals("All")) {
                    pnl.setVisible(true);
                } else {
                    pnl.setVisible(false);
                }
            }
        });
    }
    
    // Add a new item to the panel
    public void addItem(Item item) {
        ItemPanel pnl = new ItemPanel(item, listener);
        items.add(pnl);
        pnl.setMaximumSize( new Dimension(9999, 28));
        pnl.setAlignmentX(LEFT_ALIGNMENT);
        gbc.gridy++;
        panel.add(pnl, BorderLayout.CENTER);
    }
    
    // Notify the ProductPanel of the user level of the current user
    // This just gets passed on to each ItemPanel so it sets its button state.
    public void setUserLevel(int n) {
        for ( ItemPanel pnl : items ) {
            pnl.setUserLevel( n );
        }
    }
    
    // Remove all items, used whenever we re-sync with the database.
    public void clearItems() {
        for(ItemPanel pnl : items) {
            panel.remove(pnl);
        }
    }
    
    // Create filters for all categories while making sure we don't create duplicates.
    public void initFilters() {
        comboFilters.removeAllItems();
        comboFilters.addItem("All");
        for (ItemPanel pnl : items) {
            String category = pnl.getItem().getCategory();
            boolean exists = false;
            for (int i = 0; i < comboFilters.getItemCount(); ++i) {
                if ( comboFilters.getItemAt(i).toString().equals(category) ) {
                    exists = true;
                    break;
                }
            }
            
            if (!exists) {
                comboFilters.addItem(category);
            }
        }
    }
}
