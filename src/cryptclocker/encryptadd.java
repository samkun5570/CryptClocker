/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;


/**
 *
 * @author sam
 */
 /**
  * This utility(class) is responsible for showing encrypted content
  */
public class encryptadd {
   
    TableDemo eyt;
      GPa dgp; 
     public encryptadd(final TableDemo eyt) throws IOException{
                         this.eyt=eyt;
                         this.dgp=new GPa(eyt);
                      try { eyt.setGlassPane(dgp);
                            dgp.per.setText("Showing..encrypted content.Please wait");
                            dgp.setVisible(true);
                            SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {  
                          File b =new File(eyt.pd+File.separator+eyt.userName+".zp");
                        if ( b.exists()){
                            Path path = Paths.get(eyt.pd+File.separator+eyt.userName+".zp");
                            Path out = Paths.get(eyt.pd+File.separator+eyt.userName+".z");
                            eyt.deande(path,out);
                        } 
                         ZipFile zipFile = new ZipFile(eyt.pd+File.separator+eyt.userName+".z");
                         System.out.println(eyt.pd);
			 final List fileHeaderList = zipFile.getFileHeaders();
			 for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
                                long length = fileHeader.getUncompressedSize();
                                double sizem=(double) length/1024/1024;
                                String h=" MB";
                                if(sizem<1)
                                { 
                                  sizem =(double) length/1024;
                                  h=" KB";
                                }
                                float jz = (float) sizem; 
                                ((DefaultTableModel) eyt.jTable1.getModel()).addRow(new Object[]{fileHeader.getFileName(), null, jz+""+h});
                        }
                         return null;}
                        @Override
                        protected void done() { dgp.setVisible(false);
                                Path path = Paths.get(eyt.pd+File.separator+eyt.userName+".z");
                                Path out = Paths.get(eyt.pd+File.separator+eyt.userName+".zp");
                            try {
                                eyt.deande(path,out);
                            } catch (IOException ex) {
                                Logger.getLogger(encryptadd.class.getName()).log(Level.SEVERE, null, ex);
                            }
                          }};
                               zipWorker.execute();
		                } 
                                catch (Exception ex) {dgp.setVisible(false);
                                JOptionPane.showMessageDialog(null,ex.toString()+"maybe encrypted content is not present in the selected folder");}
                               }
}
