package shop.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import shop.Model.Person;

// This class implements the user management GUI
// It can only be opened by staff and allows adding/removing new users
// as well as viewing their current information.
public class UserFrame extends JFrame {
    private View view;
    private ShopListener listener;
    private JList list;
    private DefaultListModel model = new DefaultListModel();
    private JTextField tfUserName = new JTextField();
    private JTextField tfPassword = new JTextField();
    private JTextField tfSSN = new JTextField();
    private JTextField tfAddr = new JTextField();
    private JTextField tfZip = new JTextField();
    private JTextField tfName = new JTextField();
    private JComboBox  cmbUserLevel = new JComboBox();
    
    public UserFrame(View view, ShopListener listener) {
        super("User Management");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(new Dimension(420, 240));
        setResizable(false);
        this.view = view;
        this.listener = listener;
        initGUI();
    }
    
    private void initGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weightx = 0.0f;
        gbc.weighty = 1.0f;
        gbc.gridwidth = 1;
        
        list = new JList();
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane( list );
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add( scroll, gbc );
        
        JPanel panel = new JPanel();
        //panel.setBackground(Color.red);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0f;
        add(panel, gbc );
        
        panel.setLayout( new GridBagLayout() );
        
        gbc.weightx = 0.1f;
        gbc.weighty = 0.1f;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Username: "), gbc );
        gbc.gridy = 1;
        panel.add(new JLabel("Password: "), gbc );
        gbc.gridy = 2;
        panel.add(new JLabel("Name: "), gbc );
        gbc.gridy = 3;
        panel.add(new JLabel("SSN: "), gbc );
        gbc.gridy = 4;
        panel.add(new JLabel("Address: "), gbc );
        gbc.gridy = 5;
        panel.add(new JLabel("Zip: "), gbc );
        gbc.gridy = 6;
        panel.add(new JLabel("UserLevel: "), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.9f;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(tfUserName, gbc );
        gbc.gridy = 1;
        panel.add(tfPassword, gbc );
        gbc.gridy = 2;
        panel.add(tfName, gbc );
        gbc.gridy = 3;
        panel.add(tfSSN, gbc );
        gbc.gridy = 4;
        panel.add(tfAddr, gbc );
        gbc.gridy = 5;
        panel.add(tfZip, gbc );
        gbc.gridy = 6;
        panel.add(cmbUserLevel, gbc );
        
        JButton btnAdd = new JButton("Add User");
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(btnAdd, gbc);
        
        JButton btnRemove = new JButton("Remove User");        
        gbc.gridx = 1;
        panel.add(btnRemove, gbc);
        
        JButton btnClear = new JButton("Clear");
        gbc.gridx = 2;
        panel.add(btnClear, gbc);
        
        // Clear all the text fields.
        btnClear.addActionListener((ActionEvent e)-> {
            tfUserName.setText("");
            tfPassword.setText("");
            tfSSN.setText("");
            tfAddr.setText("");
            tfZip.setText("");
            tfName.setText("");
        });
        
        cmbUserLevel.addItem(1);
        cmbUserLevel.addItem(2);
        
        // This handler updates the textfields to display the information
        // about the user that just got selected.
        list.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting() || list.getSelectedIndex() == -1)
                return;
            Person p = (Person)model.get(list.getSelectedIndex());
            tfUserName.setText(p.getUsername());
            //tfPassword.setText(p.getPassword());
            tfSSN.setText(p.getSsn());
            tfAddr.setText(p.getStreet());
            tfZip.setText(p.getZip());
            tfName.setText(p.getName());
            cmbUserLevel.setSelectedIndex(p.getUserLvl()-1);
        });
        
        // This handler attempts to add a new user to the database
        // and displays a message if it was succesful or if it failed.
        btnAdd.addActionListener((ActionEvent e)-> {
            boolean result = listener.register(
                    cmbUserLevel.getSelectedIndex() + 1,
                    tfUserName.getText(),
                    tfPassword.getText(),
                    tfName.getText(),
                    tfAddr.getText(),
                    tfZip.getText(),
                    tfSSN.getText());
            if (result) {
                JOptionPane.showMessageDialog(null, "User added!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add user,\n" + 
                        "The user most likely already exists.");
            }
            refreshData();
        });
        
        // This handler attempts to remove a user from the database
        // and displays a message if the operation failed.
        btnRemove.addActionListener((ActionEvent e)->{
            if ( !listener.deleteUser(tfUserName.getText())) {
                JOptionPane.showMessageDialog(null, "Could not remove user!");
            }
            list.setSelectedIndex(999);
            refreshData();
        });
    }
    
    // Re-Sync with the database.
    public void refreshData() {
        model.removeAllElements();
        Person[] persons = listener.getAllPersons();
        System.out.printf("Persons: %d\n", persons.length);
        for ( int i = 0; i < persons.length; ++i ) {
            model.addElement(persons[i]);
        }        
    }
}
