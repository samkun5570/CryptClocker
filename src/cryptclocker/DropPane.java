package cryptclocker;

    import java.awt.BorderLayout;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Point;
    import java.awt.datatransfer.DataFlavor;
    import java.awt.datatransfer.Transferable;
    import java.awt.datatransfer.UnsupportedFlavorException;
    import java.awt.dnd.DnDConstants;
    import java.awt.dnd.DropTarget;
    import java.awt.dnd.DropTargetDragEvent;
    import java.awt.dnd.DropTargetDropEvent;
    import java.awt.event.ActionEvent; 
    import java.awt.event.ActionListener;
    import java.io.File;
    import java.io.IOException;
    import java.util.List;
    import javax.swing.JButton;
    import javax.swing.JDialog;
    import javax.swing.JFileChooser;
    import javax.swing.JMenuItem;
    import javax.swing.JPopupMenu;
    import javax.swing.JScrollPane;
    import javax.swing.JTable;
    import javax.swing.table.DefaultTableModel;
   /**
    *
    * @author sam
    */
   /**
    * This frame(class) is responsible for adding content via drag n drop and popup menu
    */
   /* public class DropTable {
         public static void main(String[] args) {
             new DropTable();
            }
   public DropTable() {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager
                                .getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException
                            | IllegalAccessException
                            | UnsupportedLookAndFeelException ex) {
                        JOptionPane.showMessageDialog(null,ex.toString());
                    }
                    JFrame frame = new JFrame();
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.setLayout(new BorderLayout());
                    frame.add(new DropPane());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }

            });
        }*/
public final class DropPane extends JDialog implements ActionListener {
 
            private static final long serialVersionUID = 1L;
            private JTable table = new JTable();;
            private final JScrollPane scroll = new JScrollPane(table);;
            private final DefaultTableModel tm;
            private final JPopupMenu popupMenu = new JPopupMenu();
	    private final JMenuItem menuItemAdd = new JMenuItem("Add New Row");
	    private final JMenuItem menuItemRemove = new JMenuItem("Remove Current Row");
	    private final JMenuItem menuItemRemoveAll = new JMenuItem("Remove All Rows");
            private final JButton ok = new JButton("Export");
            Object[] temp;
            TableDemo td;
            public DropPane(final TableDemo td) {
            /*int e= (td.getWidth()/2)-(this.getWidth()/2);
            int f =(td.getHeight()/2)-(this.getHeight()/2);;
            this.setPreferredSize(new java.awt.Dimension(e, f));  
            this.setLocation(e,f);*/
           // this.setLocationRelativeTo(null);   
            this.temp = new Object[3];
            this.td = td;  
            this.setModal(true);
            this.tm = new DefaultTableModel(new String[] {"Name", "Path", "Size",  }, 0);
                table.setShowGrid(true);
                table.setShowHorizontalLines(true);
                table.setShowVerticalLines(false);
                table.setGridColor(Color.GRAY);
                table.setModel(tm);
                table.setFillsViewportHeight(true);
                table.setPreferredSize(new Dimension(500, 300));
                ok.setToolTipText("Click to export content to main window for processing");
                ok.addActionListener(new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0;i<tm.getRowCount();i++) {
                     for (int j = 0; j <3 ; j++) {
                          System.out.println(i+""+j);
                          temp[j] = (String)tm.getValueAt(i,j);
                          System.out.println(temp[j]);
                       } 
                     ((DefaultTableModel) td.jTable1.getModel()).addRow(temp);
                     }
             DropPane.this.setVisible(false);
            }
        });     menuItemAdd.addActionListener((ActionListener) this);
		menuItemRemove.addActionListener((ActionListener) this);
		menuItemRemoveAll.addActionListener((ActionListener) this);
                this.add(ok,BorderLayout.SOUTH);
                popupMenu.add(menuItemAdd);
		popupMenu.add(menuItemRemove);
		popupMenu.add(menuItemRemoveAll);
                table.setComponentPopupMenu(popupMenu);
		table.addMouseListener(new TableMouseListner(table));
                table.setDropTarget(new DropTarget() {
                    @Override
                    public synchronized void dragOver(DropTargetDragEvent dtde) {
                        Point point = dtde.getLocation();
                        int row = table.rowAtPoint(point);
                        if (row < 0) {
                            table.clearSelection();
                        } else {
                            table.setRowSelectionInterval(row, row);
                        }
                        dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
                    }

                    @Override
                    public synchronized void drop(DropTargetDropEvent dtde) {
                        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                        {
                            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                            Transferable t = dtde.getTransferable();
                            List fileList = null;
                            try {
                                fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                                if (fileList.size() > 0) {
                                    table.clearSelection();
                                    Point point = dtde.getLocation(); 
                                    int row = table.rowAtPoint(point);
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    for (Object value : fileList) {
                                        if (value instanceof File) {
                                            File f = (File) value;
                                                if (row < 0) {
                                                long size = f.length();
                                                double sizemb=(double) size/1024/1024;
                                                String se="MB";
                                                if(sizemb<1){sizemb=(double)size/1024;se="KB";}
                                                float sze = (float)sizemb;
                                                model.addRow(new Object[]{f.getName(),f.getAbsolutePath(), sze+""+se});
                                            } else {
                                                model.insertRow(row, new Object[]{f.getName(),f.getAbsolutePath(),f.length()});
                                                row++;

                                            }
                                        }
                                    }
                                }
                            } catch (UnsupportedFlavorException | IOException e) {
                            }
                        } else {
                            dtde.rejectDrop();
                        }
                    }

                });

                add(scroll, BorderLayout.CENTER);
            }
            @SuppressWarnings("empty-statement")
         private void addNewRow() {
 JFileChooser fe = new JFileChooser("File Dialog");
 fe.setMultiSelectionEnabled(true);
 int returnVal=fe.showOpenDialog(fe);
 if(returnVal==JFileChooser.APPROVE_OPTION) 
 {
     File file[]=fe.getSelectedFiles();
     for (File file1 : file) {
         long size = file1.length();
         double sizemb=(double) size/1024/1024;
         String se="MB";
         if(sizemb<1){sizemb=(double)size/1024;se="KB";}
         float sze = (float)sizemb;
         tm.addRow(new Object[]{file1.getName(), file1.getAbsolutePath(), sze+""+se});
     }
}
else{
     System.out.print("cancelled by user");
 }
 }
	private void removeCurrentRow() {
		int selectedRow = table.getSelectedRow();
		tm.removeRow(selectedRow);
	}
	private void removeAllRows() {
		int rowCount = tm.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			tm.removeRow(0);
		}
	}
       @Override
         public void actionPerformed(ActionEvent event) {
		JMenuItem menu = (JMenuItem) event.getSource();
                if (menu == menuItemAdd) {
			addNewRow();
		} else if (menu == menuItemRemove) {
			removeCurrentRow();
		} else if (menu == menuItemRemoveAll) {
			removeAllRows();
		}
                }
      
            
}
    
