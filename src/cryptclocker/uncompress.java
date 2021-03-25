/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;


import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sam
 */
/**
 * This utility(class) is responsible of performing decompression
 */
public class uncompress {
    TableDemo tade;
    final GPa gp; 
    public uncompress(final TableDemo tade ) throws IOException{
      this.gp =new GPa(tade);
      this.tade=tade;
      JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("ZIP ARCHIEVE", "zip"));
		fileChooser.setAcceptAllFileFilterUsed(true);
                int result = fileChooser.showOpenDialog(tade);
		if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
		String	fPATH = selectedFile.getAbsolutePath();
                final String zipFilePath = fPATH;
                final String destDirectory=tade.pd+File.separator+tade.userName+File.separator;
                final UnzipUtility unzipper = new UnzipUtility();
                gp.setVisible(true);
                try {  
                 SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                   @Override
                   protected Void doInBackground() throws Exception { 
                   unzipper.unzip(zipFilePath, destDirectory);
                   return null;
                   }@Override
                   protected void done() {
                   gp.setVisible(false);   
                   JOptionPane.showMessageDialog(null, "Decompression conplete search in "+destDirectory);
                   NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(), true,tade);
                   dialog.setVisible(true);
                   }  };
                 zipWorker.execute();}
		 catch (Exception ex) {gp.setVisible(false); 
	          JOptionPane.showMessageDialog(null,ex.toString());
		}
                
                }
    }
}
