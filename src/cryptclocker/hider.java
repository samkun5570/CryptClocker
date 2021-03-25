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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sam
 */
/**
 * This utility(class) is responsible of performing hiding
 *
 */
public class hider {

    
    TableDemo fe;
    GPa gpe;

    public hider(final TableDemo fe) throws FileNotFoundException, IOException {
        this.fe = fe;
        this.gpe=new GPa(fe);
        this.fe.setGlassPane(gpe);
        gpe.per.setText("hiding..Please wait.");
        gpe.setVisible(true);
        int k = fe.jTable1.getRowCount();
        final String[] mFiles = new String[k];
        try {
         
            for (int f = 0; f < k; f++) {
                mFiles[f] = (String) ((DefaultTableModel) fe.jTable1.getModel()).getValueAt(f, 1);
            }
            final String zipFile = fe.pd + File.separator + fe.userName + "hidden" + ".zph";
            final ZipUtility ziUtil = new ZipUtility();
            this.fe.setGlassPane(gpe);
            gpe.per.setText("hiding..Please wait.");
            gpe.setVisible(true);
            
            SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    ziUtil.zip(mFiles, zipFile);
                    return null;
                }

                @Override
                protected void done() {
                    for (String file : mFiles) {
                        fe.Delete(file);
                    }
                    gpe.setVisible(false);
                    String os = System.getProperty("os.name").toLowerCase();
                    if (os.contains("windows")) {
                        try {
                            File p = new File(fe.pd + File.separator + fe.userName + "hidden" + ".zph");
                            Files.setAttribute(p.toPath(), "dos:hidden", true);
                            JOptionPane.showMessageDialog(null, "the files are hidden");
                        } catch (IOException | HeadlessException ex) {
                            JOptionPane.showMessageDialog(null, ex.toString());
                        }
                    } else {
                        try {
                            {
                                File file = new File(fe.pd + File.separator + fe.userName + "hidden" + ".zph");
                                File file2 = new File(fe.pd + File.separator + fe.userName + "." + "hidden" + ".zph");
                                if (file.renameTo(file2)) {
                                    fe.Delete(fe.pd + File.separator + fe.userName + "hidden" + ".zph");
                                    JOptionPane.showMessageDialog(null, "the files are hidden");
                                }
                            }
                        } catch (HeadlessException ex) {
                            JOptionPane.showMessageDialog(null, ex.toString());
                        }
                    }
                }
            };
            zipWorker.execute();
        } catch (Exception ex) {
            this.gpe.setVisible(false);
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
}
