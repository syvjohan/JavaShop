/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.text.StyleConstants.Size;
import shop.Model.Item;

/**
 *
 * @author Zerkish
 */
public class View extends JFrame
{
    private SidePanel sidePanel;
    private ProductPanel productPanel;
    private ShopListener listener;
    
    public View(ShopListener listener)
    {
        setTitle("JavaShop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800 ,600));
        this.listener = listener;
        initGUI();
    }    
    
    public void setSidePanel(SidePanel pnl)
    {
        if ( sidePanel != null )
            remove(sidePanel);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.2f;
        gbc.weighty = 1.0f; 
        
        sidePanel = pnl;
        add(sidePanel, gbc);
        sidePanel.setVisible(false);
        sidePanel.setVisible(true);
    }
    
    private void initGUI()
    {
        setLayout(new GridBagLayout());
                
        setSidePanel(new AnonPanel());
        
        update();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.8f;
        gbc.fill = GridBagConstraints.BOTH;

        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        
        JMenuItem loginMenu = new JMenuItem("Login");
                
        loginMenu.addActionListener((ActionEvent e)->
        {
            doLogin();
        });
        
        JMenuItem regMenu = new JMenuItem("Register");
        regMenu.addActionListener((ActionEvent e) ->
        {
            doRegister();
        });
        
        JMenuItem resetMenu = new JMenuItem("Reset Database");
        resetMenu.addActionListener((ActionEvent e)->
        {
            if (listener != null)
                listener.resetDataBaseDEBUG();
        });
        
        
        menu.add(loginMenu);
        menu.add(regMenu);
        menu.add(resetMenu);
        
        setJMenuBar(menuBar);
        
        doLayout();
        
        setSize(new Dimension(640, 480));
        setVisible(true);
        setUserLevel("Peter", 2);
    }
    
    private void doLogin()
    {       
        JTextField tfUser = new JTextField();
        JTextField tfPassword = new JTextField();
        JComponent comps[] = new JComponent[]{
           new JLabel("Username"),
           tfUser,
           new JLabel("Password"),
           tfPassword             
        };
            
        JOptionPane.showMessageDialog(null, comps, "Login", JOptionPane.OK_CANCEL_OPTION);           
        if (listener != null)
        {
            int level = listener.login(tfUser.getText(), tfPassword.getText());
            
            if (level > 0) {
                setUserLevel(tfUser.getText(), level);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or password");
            }
        }
    }
    
    private void doRegister()
    {
        JTextField tfUsername = new JTextField();
        JTextField tfPassword = new JTextField();
        JTextField tfName = new JTextField();
        JTextField tfStreet = new JTextField();
        JTextField tfZip = new JTextField();
        JTextField tfSSN = new JTextField();
        JComboBox cmLevel = new JComboBox();
        cmLevel.addItem("Customer");
        cmLevel.addItem("Employee");
        
        JComponent comps[] = new JComponent[] {
            new JLabel("Username"),
            tfUsername,
            new JLabel("Password"),
            tfPassword,
            new JLabel("Name"),
            tfName,
            new JLabel("Street"),
            tfStreet,
            new JLabel("Zip"),
            tfZip,
            new JLabel("SSN"),
            tfSSN,
            new JLabel("User level"),
            cmLevel
        };
        
        JOptionPane.showMessageDialog(null, comps, "Register", JOptionPane.OK_CANCEL_OPTION);
        
        if (listener != null)
        {
           int level = cmLevel.getSelectedIndex() + 1;
           if (!listener.register(level, tfUsername.getText(),
                   tfPassword.getText(), tfName.getText(),
                   tfStreet.getText(), tfZip.getText(), tfSSN.getText()))
           {
               JOptionPane.showMessageDialog(null, "Registration failed!");
           }
        }
    }
    
    private void setUserLevel(String user, int level)
    {
        CustomerPanel cpnl;
        EmployeePanel epnl;
        productPanel.setUserLevel(level);
        
        switch(level)
        {
            case 0:
                setSidePanel(new AnonPanel());
                break;
            case 1:
                cpnl = new CustomerPanel(listener);
                cpnl.setUser(user);
                setSidePanel(cpnl);     
                break;
            case 2:
                epnl = new EmployeePanel(this, listener);
                epnl.setUser(user);
                setSidePanel(epnl);
                break;
        }
    }
    
    public void update() {
        
        if (productPanel == null) {
            // Listener for the product panel
            // Depends on the current side panel.
            productPanel = new ProductPanel((Item i)-> {
                if ( sidePanel instanceof EmployeePanel ) {
                    ((EmployeePanel)sidePanel).editItem(i);
                } else if ( sidePanel instanceof CustomerPanel ) {
                    ((CustomerPanel)sidePanel).addItemToCart(i);
                }
            });
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.gridx = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 0.8f;
            gbc.fill = GridBagConstraints.BOTH;

            add(productPanel, gbc);
        }
        
        productPanel.clearItems();
        
        Item items[] = listener.getItems();
        for (Item i : items)
        {
            productPanel.addItem(i);
        }
        
    }
}
