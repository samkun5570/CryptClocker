/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptclocker;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

/**
 *
 * @author sam
 */
/**
 * This utility listne's to mouse press event in the table and returns point and selected row
 */
public class TableMouseListner extends MouseAdapter {
	
	private final JTable table;
	
	public TableMouseListner(JTable table) {
		this.table = table;
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
        try{
            Point point = event.getPoint();
        
		int currentRow = table.rowAtPoint(point);
		table.setRowSelectionInterval(currentRow, currentRow);
        }
        catch(Exception e){}
	}
}
