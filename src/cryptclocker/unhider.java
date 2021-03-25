/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptclocker;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author sam
 */
 /**
  * This utility(class) is responsible of un hide content
  *
  */
public class unhider {
    TableDemo tde;
    GPa gpa;

    public unhider(final TableDemo tde) throws FileNotFoundException, IOException {
        this.tde = tde;
        this.gpa = new GPa(tde);
        tde.setGlassPane(gpa);
        gpa.per.setText("Unhiding..Please wait");
        gpa.setVisible(true);
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) {
                try {
                    File p = new File(tde.pd + File.separator + tde.userName + "hidden.zph");
                    Files.setAttribute(p.toPath(), "dos:hidden", false);
                } catch (IOException | HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            } else {
                try {
                    {
                        File file2 = new File(tde.pd + File.separator + tde.userName + "hidden" + ".zph");
                        File file = new File(tde.pd + File.separator + tde.userName + "." + "hidden.zph");
                        if (file.renameTo(file2)) {
                            tde.Delete(tde.pd + File.separator + tde.userName + "hidden" + ".zph");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
            final String zipFilePath = tde.pd + File.separator + tde.userName + "hidden.zph";
            final String destDirectory = tde.pd + File.separator + tde.userName + File.separator + "unhidden";
            final UnzipUtility unzipper = new UnzipUtility();
            SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {

                    unzipper.unzip(zipFilePath, destDirectory);
                    return null;
                }

                @Override
                protected void done() {
                    gpa.setVisible(false);
                    NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(), true, tde);
                    dialog.setVisible(true);
                }
            };
            zipWorker.execute();
        } catch (HeadlessException ex) {
            gpa.setVisible(false);
            JOptionPane.showMessageDialog(null, ex.toString());
            JOptionPane.showMessageDialog(null, "the files can't be unhidden, compatibility issues");
        }
    }
}
