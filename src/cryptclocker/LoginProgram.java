/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;

/**
 *
 * @author sam
 *
 */ 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 /**
  * This utility(class) is responsible for login and registration
  */
class  LoginForm extends JFrame implements ActionListener{
     
            JPanel panel;
            JLabel lblname;
            JLabel lblpassword;
            JLabel lblmess;
            JTextField txtname;
            JPasswordField txtpassword;
            JButton btlogin;
            JPanel panelreg;
            JLabel lblnamereg;
            JLabel lblpasswordreg;
            JLabel lblmessreg;
            JTextField txtnamereg;
            JPasswordField txtpasswordreg;
            JButton btsubmit;    
        
            LoginForm(){
                    setTitle("Member login and Registration");
                    setSize(630,250);
                    setResizable(false);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                    Container content=getContentPane();
                    JDesktopPane des=new JDesktopPane();
                    center();
                        //Login form
                    JInternalFrame flog=new JInternalFrame();
                    flog.setSize(300,200);
                    flog.setLocation(10,2);
                    flog.setTitle("Member Login");
                    lblname=new JLabel("User Name:");
                    lblpassword=new JLabel("Password:");
                    lblmess=new JLabel("");
                    btlogin=new JButton("Login");
                    btlogin.addActionListener(this);               
                    txtname=new JTextField(20);
                    txtpassword=new JPasswordField(20);
                    panel=new JPanel();
                    panel.add(lblname);
                    panel.add(txtname);
                    panel.add(lblpassword);
                    panel.add(txtpassword);
                    panel.add(btlogin);
                    panel.add(lblmess);
                    flog.add(panel);
                    flog.setVisible(true);                       
                       
                        //Registration form
                    JInternalFrame freg=new JInternalFrame();
                    freg.setSize(300,200);
                    freg.setLocation(315,2);
                    freg.setTitle("Member Registration");
                    lblnamereg=new JLabel("User Name:");
                    lblpasswordreg=new JLabel("Password:");
                    lblmessreg=new JLabel("");
                    btsubmit=new JButton("Submit");
                    btsubmit.addActionListener(this);
                    txtnamereg=new JTextField(20);
                    txtpasswordreg=new JPasswordField(20);
                    txtpasswordreg.addKeyListener(new KeyList());
                    panelreg=new JPanel();
                    panelreg.add(lblnamereg);
                    panelreg.add(txtnamereg);
                    panelreg.add(lblpasswordreg);
                    panelreg.add(txtpasswordreg);
                    panelreg.add(btsubmit);
                    panelreg.add(lblmessreg);            
                    freg.add(panelreg);
                    freg.setVisible(true);                       
                    des.add(flog);
                    des.add(freg);
                    content.add(des, BorderLayout.CENTER);
                    setVisible(true);                   
                    txtname.requestFocus();                
                       
            }

            @Override
 public void actionPerformed(ActionEvent e){
           
            if(e.getSource()==btsubmit){
                        String uname=txtnamereg.getText().trim();
                        String passw=new String(txtpasswordreg.getPassword());
                        if(!checkBlank(uname,passw, lblnamereg,lblpasswordreg)){
                                    if(!checkExist("accounts.txt",uname)){
                                                passw=new String(encrypt(passw));                   
                                                String accinfo=uname+"-"+passw;
                                                saveToFile("accounts.txt",accinfo);
                                           }
                                    }          
                                   
                        }
             else if(e.getSource()==btlogin){
                        String uname=txtname.getText();
                        String passw=new String(txtpassword.getPassword());
                        if(!checkBlank(uname,passw,lblname,lblpassword))
                                    validateUser("accounts.txt",uname,passw);
                         }
                       
            }

 public class KeyList extends KeyAdapter{
            @Override
            public void keyPressed(KeyEvent ke){
                        String passw=new String(txtpasswordreg.getPassword());
                        String mess=checkStrength(passw);
                        showMess(mess+" password",lblpasswordreg);
            }

    }


public boolean checkBlank(String name, String passw, JLabel namemess, JLabel passwmess){
            boolean hasBlank=false;
            if(name.length()<1){
                        showMess("User name is required.",namemess);
                        hasBlank=true;
                        }
            if(passw.length()<1){
                        showMess("Password is required.",passwmess);
                        hasBlank=true;
            }
            return hasBlank;
                                                                       
}


public void showMess(String mess, JLabel lbl){
                        lbl.setText(mess);
                        lbl.setForeground(Color.CYAN);                
            }

 public String checkStrength(String passw){
            Pattern pat=Pattern.compile("([0-9][aA-zZ]|[aA-zZ][0-9])");
            Matcher mat=pat.matcher(passw);
            if(mat.find()) {
                        if(passw.length()>=8)
                        {lblmessreg.setForeground(Color.GREEN); return "Strong";}
                        else 
                        {lblmessreg.setForeground(Color.ORANGE); return  "Medium" ;}
                        }
            else  lblmessreg.setForeground(Color.RED);return "Weak";
           
}

 public void reset(JLabel lblname,JLabel lblpassw ){
            lblname.setText("User Name:");
            lblname.setForeground(Color.BLACK);
            lblpassw.setText("Password:");
            lblpassw.setForeground(Color.BLACK);
            }

 public void validateUser(String filename, String name, String password){
          FileReader fr;
          BufferedReader br;
          boolean valid=false;
          String accinfo;
             try{
                                                           
                                    fr=new FileReader(filename);
                                    br=new BufferedReader(fr);
                                    while ((accinfo=br.readLine())!=null){
           
                                    if(check(accinfo,name,password))
                                    {
                                                showMess("Valid login",lblmess);
                                                valid=true;
                                                this.setVisible(false);
                                                new TableDemo(name,password).setVisible(true);
                                                break;
                                     }
                                   
             
                                                                         }
           
                        if(!valid) showMess("Invalid login",lblmess);
                        reset(lblname,lblpassword);
                        br.close();
                        fr.close();    
                 } catch(IOException ie){JOptionPane.showMessageDialog(null,ie.toString());}

  }

 public boolean check(String accinfo, String name, String passw){
                        String[] info=accinfo.split("-");
                        String uname=info[0];
                        String pass=new String(decrypt(info[1]));
                return uname.equals(name) && pass.equals(passw);
                                   
            }

 public boolean checkExist(String filename, String name){
          FileReader fr;
          BufferedReader br;
          String accinfo;
          boolean exist=false;
             try{          
                                   
                                    fr=new FileReader(filename);
                                    br=new BufferedReader(fr);
                                    while ((accinfo=br.readLine())!=null){
           
                                    if(check(accinfo,name))
                                    {
                                                showMess("The account already exists.",lblmessreg);
                                                exist=true;
                                                break;
                                    }
                                   
             
                                     }
           
                        br.close();
           fr.close();    
     } catch(IOException ie){JOptionPane.showMessageDialog(null,ie.toString());}
            return exist;                          
}

public boolean check(String accinfo, String name){
                        String[] info=accinfo.split("-");
                        String uname=info[0];
                return uname.equals(name);
                                   
            }

 public void saveToFile(String filename,String text){
            try{
                        FileWriter fw=new FileWriter(filename,true); 
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(text);
                    bw.newLine();
                    bw.flush();
                }
                        showMess("The account is created.",lblmessreg);
                        reset(lblnamereg,lblpasswordreg);
             }catch(IOException ie){JOptionPane.showMessageDialog(null,ie.toString());}         
}

 public byte[] encrypt(String passw){
                        byte[] sb=passw.getBytes();
                        int i;                
                        for(i=0;i<sb.length;i++)
                        sb[i]=(byte)(sb[i]+1);
                        return(sb);
            }

 public byte[] decrypt(String passw){
                        byte[] sb=passw.getBytes();
                        int i;                
                        for(i=0;i<sb.length;i++)
                        sb[i]=(byte)(sb[i]-1);
                        return(sb);
            }
 public final void center() {
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
Dimension frameSize = getSize();
int x = (screenSize.width - frameSize.width) / 2;
int y = (screenSize.height - frameSize.height) / 2;
setLocation(x, y);
}

}

/**
 *
 * @author sam
 */
public class LoginProgram{

    /**
     *
     * @param args
     */
    public static void main(String args[]){
    
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
java.awt.EventQueue.invokeLater(new Runnable() {
     @Override
     public void run() {
     new LoginForm().setVisible(true);
   }
});
 }
 }
 