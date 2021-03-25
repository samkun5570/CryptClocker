/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
 * This utility(class) is responsible for creation of split zip file
 */
 public class CreateSplitZipFile {
 TableDemo cszf;  
 GPa dgp; 
        public CreateSplitZipFile(final TableDemo cszf) throws IOException { 
	this.cszf=cszf;
        this.dgp=new GPa(cszf);
        int k = cszf.jTable1.getRowCount();
        try {         String NAME = JOptionPane.showInputDialog("Please enter NAME OF ZIP");
                      ZipFile zipFile = new ZipFile(cszf.pd+File.separator+NAME+".ze");
		        ArrayList filesToAdd = new ArrayList();
			for(int f=0;f<k;f++){
                        String newp =(String)((DefaultTableModel) cszf.jTable1.getModel()).getValueAt(f, 1);
                        filesToAdd.add(new File(newp));
		        }
		        ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
  String level = JOptionPane.showInputDialog("Please enter compresion level(NORMAL=1,MAXIMUM=2,ULTRA=3)HIGHER COMPRESSION = HIGHER TIME REQUIRED ");
            switch (level) {
                case "1":
                    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                    break;
                case "2":
                   parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);
                   break;
                case "3":
                    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
                    break;
               default:
                    JOptionPane.showMessageDialog(null,"Invalid entry,by default NORMAL is selected");
                    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                    break;
            }
                       String size = JOptionPane.showInputDialog("Please enter each split size in mb");
                        if(Integer.parseInt(size)<5){
                        JOptionPane.showMessageDialog(null,"Invalid entry,by default 10 mb size is selected");
                        cszf.setGlassPane(dgp);
                         dgp.setVisible(true);
                        zipFile.createZipFile(filesToAdd, parameters, true, 10485760);
                        output(); 
                        dgp.setVisible(false);
                        }
                        else{ cszf.setGlassPane(dgp);
                            dgp.setVisible(true);
                            zipFile.createZipFile(filesToAdd, parameters, true,Integer.parseInt(size)*1048576 );
                            dgp.setVisible(false);
                            output();   
                       }
                        cszf.clear();
                        } catch (ZipException e) {dgp.setVisible(false);
                            JOptionPane.showMessageDialog(null,e.toString());}
	}
        public void output(){
                                NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(), true, cszf);
                                dialog.setVisible(true);
        }
    }
