import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class EventActionListener implements ActionListener {
    JTable table;
    JTextField text1, text2, text3, idx;
    
    EventActionListener() {
    }
    
    EventActionListener(JTable table, JTextField text1){
    	this.table = table;
    	this.text1 = text1;
    }
    
    EventActionListener(JTable table) {
    	this.table = table;
    }
    
    EventActionListener(JTable table,JTextField idx, JTextField text3){
    	this.table = table;
    	this.idx = idx;
    	this.text3 = text3;
    }
    EventActionListener(JTable table, JTextField text1, 
                      JTextField text2, JTextField text3) {
        this.table = table;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
    }
    
    public void actionPerformed(ActionEvent e) {
     if (e.getActionCommand().equals("조회"))
     {
      select();
     }
     else if (e.getActionCommand().equals("추가"))
     {
      insert();
     }
     else  if (e.getActionCommand().equals("삭제"))
     {
      delete();
     }
     else
     {
      update();
     }
    }
    
    void select()
    {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String url = "jdbc:mysql://localhost:3306/software?serverTimezone=UTC";
		String user = "root";
		String password = "1234";
        String sql = null;
        
        String arr[] = new String[4];
        
        System.out.println(text1.getText().trim());
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowNum = model.getRowCount();
        for (int i=0; i<rowNum; i++)
         model.removeRow(0);
        
        try {
        	
        	Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            
        	if( text1.getText().length() != 0 ) {
        		sql = "select * from userManage WHERE Name LIKE ?";
        		pstmt = con.prepareStatement(sql);
        		pstmt.setString(1,"%"+ text1.getText().trim() + "%");
                rs = pstmt.executeQuery();
        	}else {
        		sql = "select * from userManage";
        		pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery(sql);
        	}

            while(rs.next()) {
            	arr[0] = rs.getString("idx");
                arr[1] = rs.getString("name");
                arr[2] = rs.getString("birth");
                arr[3] = rs.getString("phone");
                model.addRow(arr);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace( );
        }
        finally {
         try {rs.close();} catch (Exception ignored) {}
         try {pstmt.close();} catch (Exception ignored) {}
            try {con.close();}catch (Exception ignored) {}
        }
        
        clearPanel();
    }
    
    void insert()
    {
        String arr[] = new String[3];
        arr[0] = text1.getText().trim();
        arr[1] = text2.getText().trim();
        arr[2] = text3.getText().trim();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        Connection con = null;
		PreparedStatement pstmt = null;
		
		String url = "jdbc:mysql://localhost:3306/software?serverTimezone=UTC";
		String user = "root";
		String password = "1234";

        String sql = null;
        int rowNum;

        try {
         sql = "insert into userManage (name, birth, phone) values (?, ?, ?)";
            
         Class.forName("com.mysql.cj.jdbc.Driver");
         con = DriverManager.getConnection(url, user, password);
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, arr[0]); 
         pstmt.setString(2, arr[1]);
         pstmt.setString(3, arr[2]); 
            
            rowNum = pstmt.executeUpdate();  
            if (rowNum == 0)
             System.out.println("데이터 추가 실패!");
            else
            {
            	this.select();
             //model.addRow(arr);
             System.out.println("데이터 추가 성공!");
            }
        }
        catch(Exception e1) {
            System.out.println("데이터 추가 실패!! = "+e1.getMessage());
        }
        finally {
         try {pstmt.close();} catch (Exception ignored) {}
            try {con.close();}catch (Exception ignored) {}
        }
    }
    
    void delete()
    {
     int row = table.getSelectedRow();
        if (row == -1)
            return;
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String idx = model.getValueAt(row, 0).toString().trim();
       
        Connection con = null;
		PreparedStatement pstmt = null;
		
		String url = "jdbc:mysql://localhost:3306/software?serverTimezone=UTC";
		String user = "root";
		String password = "1234";

        String sql = null;
        int rowNum;
        
        try {  
            sql = "delete from userManage where idx = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, idx);
            
            rowNum = pstmt.executeUpdate();
            if (rowNum == 0)
             System.out.println("데이터베이스 삭제 실패!");
            else
            {
             model.removeRow(row); 
             System.out.println("데이터베이스 삭제 성공!");
            }
        }
        catch(Exception e1) {
            System.out.println("데이터베이스 삭제 실패!"+e1.getMessage());
        }
        finally {
         try {pstmt.close();} catch (Exception ignored) {}
            try {con.close();}catch (Exception ignored) {}
        }
    }
    
    void update()
    {
     int i, age, rowNum;
     String m_idx = null; 
     String phone = null; 
     
     int row = table.getSelectedRow();
     if (row == -1)
         return;
     
     DefaultTableModel model = (DefaultTableModel) table.getModel();
     m_idx = model.getValueAt(row, 0).toString().trim();
     phone = model.getValueAt(row,3).toString().trim();
     
        Connection con = null;
		PreparedStatement pstmt = null;
		
		String url = "jdbc:mysql://localhost:3306/software?serverTimezone=UTC";
		String user = "root";
		String password = "1234";

        String sql = null;
        
        try {  
            
        	sql = "update userManage set phone = ? where idx = ?";
            
            System.out.println("sql : " + sql);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, phone); 
            pstmt.setString(2, m_idx); 
            rowNum = pstmt.executeUpdate();
            
            if (rowNum == 0)
             System.out.println("데이터베이스 수정 실패!");
            else
            {
            	this.select();
            /*
             rowNum = model.getRowCount();
                for (i=0; i<rowNum; i++)
                {
                 table_irum = model.getValueAt(i, 0).toString().trim();
                 if (table_irum.equals(irum))
                 {
                  model.setValueAt(irum, i, 0);
                  model.setValueAt(age, i, 1);
                  model.setValueAt(phone, i, 2);
                  break;
                 }
                }
                */
             System.out.println("데이터베이스 수정 성공!=" + rowNum);
            }
        }
        catch(Exception e1) {
            System.out.println("데이터베이스 연결 실패!"+e1.getMessage());
        }
        finally {
         try {pstmt.close();} catch (Exception ignored) {System.out.println("데이터베이스 연결 실패!" + ignored.getMessage());}
            try {con.close();}catch (Exception ignored) {System.out.println("데이터베이스 연결 실패!" + ignored.getMessage());}
            select();
        }
    }
    
    void clearPanel() {
    	
    	this.text1.setText("");
    	this.text2.setText("");
    	this.text3.setText("");
    }
}


