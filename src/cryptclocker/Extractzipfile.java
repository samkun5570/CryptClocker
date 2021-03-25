/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author sam
 */
/**
 * This utility(class) is responsible of merging and extracting split zip 
 */
public class Extractzipfile {
    TableDemo ezf;
    GPa gp;
    public  Extractzipfile(final TableDemo ezf) throws IOException{
  
        this.ezf=ezf;
        this.gp=new GPa(ezf);
        try {   JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("SPlIT ZIP ARCHIEVE", "z"));
		fileChooser.setAcceptAllFileFilterUsed(true);
                int result = fileChooser.showOpenDialog(ezf);
		if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
		String	fPATH = selectedFile.getAbsolutePath();
                         ezf.setGlassPane(gp);
                         gp.per.setText("Merging and Extracting...split archieves.Please wait.");
                         gp.setVisible(true);
			 final ZipFile zipFile = new ZipFile(fPATH);
                         if(zipFile.isSplitArchive()){
                         ArrayList list=zipFile.getSplitZipFiles();
                         for(int i=0;i<list.size();i++){
                                System.out.println(list.get(i));
                       } final File output=new File(ezf.pd+File.separator+ezf.userName+".output");
                                 SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                          zipFile.mergeSplitFiles(output);
                          ZipFile zip=new ZipFile(output);
                          zip.extractAll(ezf.pd+File.separator);
                          return null;
                        }
                        @Override
                        protected void done() {
                           ezf.Delete(ezf.pd+File.separator+ezf.userName+".output");
                           gp.setVisible(false);
                           NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(),true,ezf);
                           dialog.setVisible(true);
                           }  };
                           zipWorker.execute();
                           }else{JOptionPane.showMessageDialog(null,"The selected file is not a split archieve"); }
                }
                } catch (HeadlessException | ZipException e) {gp.setVisible(false);
			JOptionPane.showMessageDialog(null,e.toString()); 
		}
    }
}
