/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author sam
 */
  /**
  * This utility(class) is responsible of performing compression
  */
public class compress {
   TableDemo tad;
   final GPa gp;
   public compress(final TableDemo tad) throws IOException{
         this.tad=tad;
         this.gp = new GPa(tad);
         int k = tad.jTable1.getRowCount();
         final String[] myFiles = new String[k];
         try{
            for(int f=0;f<k;f++){
                myFiles[f] =(String)((DefaultTableModel) tad.jTable1.getModel()).getValueAt(f, 1);
            }   String nme=(String)((DefaultTableModel) tad.jTable1.getModel()).getValueAt(0, 0);
            final String zipFile =tad.pd+File.separator+nme+".zip";
            final ZipUtility zipUtil = new ZipUtility();
            this.tad.setGlassPane(gp);
            gp.per.setText("Compressing..Please wait.");
            gp.setVisible(true);
            SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                   @Override
                   protected Void doInBackground() throws Exception { 
                   zipUtil.zip(myFiles, zipFile);
                   return null;} 
                   @Override
                   protected void done() {gp.setVisible(false);
                   NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(), true,tad);
                   dialog.setVisible(true);
            }  };
                 zipWorker.execute();}
                 catch (Exception ex) {gp.setVisible(false);
                 JOptionPane.showMessageDialog(null,ex.toString());
                 }
               }
            } 
