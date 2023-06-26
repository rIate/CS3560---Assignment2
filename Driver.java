import java.awt.*;

public class Driver {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminPanel.getInstance().setVisible(true);
            }
        });
    }
}
