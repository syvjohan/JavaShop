/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import shop.Model.Item;

/**
 *
 * @author Zerkish
 */
public class ProductPanel extends JPanel {
    private ActionListener listener;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel panel = new JPanel();
    private JScrollPane scroll = new JScrollPane(panel);
    
    public ProductPanel(ActionListener buyListener)
    {
        add(scroll, BorderLayout.CENTER);
        this.listener = buyListener;
        //setLayout(new GridBagLayout());
        //BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        //panel.setLayout(layout);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        //setLayout(layout);
        //setBackground(Color.RED);
//        
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.PAGE_START;
//        gbc.ipady = 0;
//        gbc.ipadx = 0;
//        gbc.weightx = 1.0f;
//        gbc.weighty = 0.01f;
//        gbc.gridwidth = 1;
//        gbc.gridheight = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
    }
    
    public void addItem(Item item)
    {
        ItemPanel pnl = new ItemPanel(item, listener);
        pnl.setAlignmentX(LEFT_ALIGNMENT);
        gbc.gridy++;
        panel.add(pnl, BorderLayout.CENTER);
    }
    
    public void clearItems()
    {
        for(Component c : getComponents())
        {
            remove(c);
        }
    }
}
