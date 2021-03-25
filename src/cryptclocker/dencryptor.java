

package cryptclocker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

/**
 *
 * @author sam
 */
/**
 * This utility(class) is responsible of performing decryption
 */
public class dencryptor {
   
    TableDemo tabde;
    GPa dp;
    public dencryptor(final TableDemo tabde) throws IOException{
        this.tabde=tabde;
        this.dp=new GPa(tabde);
          try {         tabde.setGlassPane(dp);
                        dp.per.setText("Dencrypting!...Please wait.");
                        dp.setVisible(true);
                        File a =new File(tabde.pd+File.separator+tabde.userName+".zp");
                        if (a.exists()){
                        Path path = Paths.get(tabde.pd+File.separator+tabde.userName+".zp");
                        Path out = Paths.get(tabde.pd+File.separator+tabde.userName+".z");
                        tabde.deande(path,out);
                        }
			final ZipFile zipFile = new ZipFile(tabde.pd+File.separator+tabde.userName+".z");
                        if (zipFile.isEncrypted()) {zipFile.setPassword(tabde.passWord);}
			SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                        List fileHeaderList = zipFile.getFileHeaders();
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
				zipFile.extractFile(fileHeader,tabde.pd+File.separator+tabde.userName+File.separator);
                               }
                        Path path = Paths.get(tabde.pd+File.separator+tabde.userName+".z");
                        Path out = Paths.get(tabde.pd+File.separator+tabde.userName+".zp");
                        tabde.deande(path,out);
                        return null;
                        }
                        @Override
                        protected void done() { 
                        dp.setVisible(false);    
			JOptionPane.showMessageDialog(null,"DECRYPTED");
                        NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(),true,tabde);
                        dialog.setVisible(true);
                        tabde.clear();
                        }  };
                        zipWorker.execute();       
                        } catch (ZipException e) {dp.setVisible(false);
                            JOptionPane.showMessageDialog(null,e.toString());}
    }
    
}
