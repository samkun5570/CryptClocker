/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;

/**
 *
 * @author sam
 */
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.unzip.UnzipUtil;
   /**
    * This utility(class) is responsible of performing decryption of single selected encrypted file
    */

public class ExtractSelectFiles {
   
	private final int BUFF_SIZE = 4096;
	TableDemo d;
        GPa dgp;
	public ExtractSelectFiles(final TableDemo d) throws IOException {
		this.d=d;
                this.dgp=new GPa(d);
		ZipInputStream is = null;
		OutputStream os = null;
		try {   File a =new File(d.pd+File.separator+d.userName+".zp");
                        if (a.exists()){
                        Path path = Paths.get(d.pd+File.separator+d.userName+".zp");
                        Path out = Paths.get(d.pd+File.separator+d.userName+".z");
                        d.deande(path,out);
                        }
			ZipFile zipFile = new ZipFile(d.pd+File.separator+d.userName+".z");
			String destinationPath = d.pd;
			if (zipFile.isEncrypted()) {
			    zipFile.setPassword(d.passWord);
			}
                        int slRow = d.jTable1.getSelectedRow();
                        Object singlename = d.jTable1.getValueAt(slRow,0);
			final FileHeader fileHeader = zipFile.getFileHeader((String)singlename);
			if (fileHeader != null) {
                                d.setGlassPane(dgp);
                                dgp.per.setText("Decrypting..Selected file.Please wait.");
                                dgp.setVisible(true);
				String outFilePath = destinationPath + System.getProperty("file.separator") + fileHeader.getFileName();
				final File outFile = new File(outFilePath);
				if (fileHeader.isDirectory()) {
					outFile.mkdirs();
					return;
				}
				File parentDir = outFile.getParentFile();
				if (!parentDir.exists()) {
				    parentDir.mkdirs(); 
				}
				is = zipFile.getInputStream(fileHeader);
				os = new FileOutputStream(outFile);
				int readLen = -1;
				byte[] buff = new byte[BUFF_SIZE];
				while ((readLen = is.read(buff)) != -1) {
					os.write(buff, 0, readLen);
				}
				is.close();
				os.close();
                                SwingWorker<Void, Void> zipWorker = new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception { 
				UnzipUtil.applyFileAttributes(fileHeader, outFile);
                                Path path = Paths.get(d.pd+File.separator+d.userName+".z");
                                Path out = Paths.get(d.pd+File.separator+d.userName+".zp");
                                d.deande(path,out);
                                return null;
                                }
                                @Override
                                protected void done() {  dgp.setVisible(false);
				JOptionPane.showMessageDialog(null,"Done extracting: " + fileHeader.getFileName());
                                NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(), true, d);
                                dialog.setVisible(true);
                                }  };
                                zipWorker.execute();
			        } else {dgp.setVisible(false);
				JOptionPane.showMessageDialog(null,"FileHeader does not exist");
			        }
		                } catch (HeadlessException | IOException | ZipException e) {
			        JOptionPane.showMessageDialog(null,e.toString());
		                }
	                       }
                             }

