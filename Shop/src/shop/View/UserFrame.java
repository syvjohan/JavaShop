package shop.View;

import java.awt.Dimension;
import javax.swing.JFrame;

public class UserFrame extends JFrame {
    private View view;
    private ShopListener listener;
    
    public UserFrame(View view, ShopListener listener) {
        super("User Management");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(new Dimension(640, 480));
        this.view = view;
        this.listener = listener;
    }
    
    private void initGUI() {
        
    }
}
