
package cryptclocker;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
/**
 *
 * @author sam
 */
/**
 * This is the glass pane which shows the task being performed and blocks input while task is being performed
 */
public class GPa extends JComponent {
    

    TableDemo jf;
    JLabel per = new JLabel();
    JLabel fle = new JLabel();

    JProgressBar pbar;

    GPa(TableDemo jf) throws IOException {
        this.setLayout(new BorderLayout());
        this.jf = jf;
        int a = jf.getWidth();
        int b = jf.getHeight();
        this.setPreferredSize(new java.awt.Dimension(a, b));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon.png")));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        per.setBorder(border);
        per.setText("Performing Task please wait");
        per.setFont(new Font("Serif", Font.BOLD, 48));
        per.setForeground(Color.green);
        per.setPreferredSize(new Dimension(150, 100));
        fle.setPreferredSize(new Dimension(150, 222));
        this.pbar = new JProgressBar(0, 100);
        this.pbar.setIndeterminate(true);
        this.pbar.setPreferredSize(new Dimension(100, 45));
        this.add(pbar, BorderLayout.CENTER);
        this.add(fle, BorderLayout.NORTH);
        this.add(per, BorderLayout.SOUTH);
    }
    @Override
    public Insets getInsets() {
        return new Insets(100,222,100,222);
    }

}

    
  
