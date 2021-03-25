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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 *
 * @author sam
 */
/**
 * This utility(class) is responsible of performing encryption
 */
public class encryptor {
    TableDemo tabl;
    GPa dgp; 
   public encryptor(final TableDemo tabl) throws IOException{
                   this.tabl=tabl;
                   this.dgp=new GPa(tabl);
                   final int k = tabl.jTable1.getRowCount();
                   try {
                       tabl.setGlassPane(dgp);
                       dgp.per.setText("Encrypting...Please Wait");
                       dgp.setVisible(true);
                       File l =new File(tabl.pd+File.separator+tabl.userName+".zp");
                       if (l.exists()){
                       Path path = Paths.get(tabl.pd+File.separator+tabl.userName+".zp");
                       Path out = Paths.get(tabl.pd+File.separator+tabl.userName+".z");
                       tabl.deande(path,out);}
                       final ZipFile zipFile = new ZipFile(tabl.pd+File.separator+tabl.userName+".z");
		       final ArrayList filesToAdd = new ArrayList();
                       for(int f=0;f<k;f++){
                       String newp =(String)((DefaultTableModel) tabl.jTable1.getModel()).getValueAt(f, 1);
                       filesToAdd.add(new File(newp));
		       }
			final ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); 
		        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM); 
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			parameters.setPassword(tabl.passWord);
                        SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {  
			zipFile.addFiles(filesToAdd, parameters);
                        Path path = Paths.get(tabl.pd+File.separator+tabl.userName+".z");
                        Path out = Paths.get(tabl.pd+File.separator+tabl.userName+".zp");
                        tabl.deande(path,out);
                        return null;
                        }
                        @Override
                        protected void done() {   
                        dgp.setVisible(false);
                        try{ 
                        for(int f=0;f<k;f++){
                        String newp =(String)((DefaultTableModel) tabl.jTable1.getModel()).getValueAt(f, 1);
                        tabl.Delete(newp);
                        }
                        tabl.clear(); }
                        catch(Exception e){
                        }
                        JOptionPane.showMessageDialog(null,"ENCRYPTED");
                        }  };
                        zipWorker.execute();
                        } catch (ZipException e) {
                        dgp.setVisible(false);   
                        JOptionPane.showMessageDialog(null,e.toString()); 
		        }
                        }
		}
   

