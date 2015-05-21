
package shop.View;

import java.awt.Color;
import javax.swing.JLabel;

// This class is a basic placeholder for users that aren't logged in.
// They can browse items but not buy or edit them.
public class AnonPanel extends SidePanel {
    
    public AnonPanel()
    {
        add(new JLabel("Please login."));
    }
    
}
