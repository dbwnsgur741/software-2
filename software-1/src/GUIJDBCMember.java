import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class GUIJDBCMember {
	public static void main(String[] args) {
        JFrame frame = new JFrame("소공_과제#1");
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setLocation(500, 400);
        Container contentPane = frame.getContentPane();
        
        String colNames[] = {"idx", "이름", "생년월일", "전화번호" };
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        JTable table = new JTable(model);  
        
        int widths[] = {10,100, 100, 100};
        for (int i=0; i<4; i++) {
         TableColumn column = table.getColumnModel().getColumn(i);
         column.setPreferredWidth(widths[i]);
        }
        
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
        
        JTextField idx = new JTextField(3);
        JTextField text1 = new JTextField(4);
        JTextField text2 = new JTextField(6);
        JTextField text3 = new JTextField(6);
        JButton button1 = new JButton("조회");
        JButton button2 = new JButton("추가");
        JButton button3 = new JButton("삭제");
        JButton button4 = new JButton("수정");
        
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("이름"));
        panel1.add(text1);
        panel1.add(new JLabel("생년월일"));
        panel1.add(text2);
        panel1.add(new JLabel("전화번호"));
        panel1.add(text3);
       
        JPanel panel2 = new JPanel();
        panel2.add(button1);
        panel2.add(button2);
        panel2.add(button3);
        panel2.add(button4);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(panel1, BorderLayout.CENTER);
        panel.add(panel2, BorderLayout.SOUTH);
        contentPane.add(panel, BorderLayout.SOUTH);
        
        button1.addActionListener(new EventActionListener(table,text1));
        button2.addActionListener(new EventActionListener(table, text1, text2, text3));
        button3.addActionListener(new EventActionListener(table));
        button4.addActionListener(new EventActionListener(table,idx,text3));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


