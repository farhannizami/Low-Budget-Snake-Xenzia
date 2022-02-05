package low.budget.snake.xenzia;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author Farhan Nasif Nizami
 */
public class GameFrame extends JFrame{

   GameFrame()
   {
       GamePanel panel = new GamePanel();
       this.add(panel);
       setTitle("Low Budget Snake Xenzia");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setResizable(false);
       pack();
       setVisible(true);
       setLocationRelativeTo(null);
   }
}
