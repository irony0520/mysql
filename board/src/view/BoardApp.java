package view;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.BoardDAO;
import model.BoardDTO;

public class BoardApp extends JFrame {
	private BoardApp board;
	private JTable jTable;
	private JPanel pSouth;
	private JButton btnInsert;
	
	public BoardApp() {
		this.board = this;
		this.setTitle("게시판 리스트");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(new JScrollPane(getBoardTable()), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.setSize(600, 450);
		locationCenter();
	}
	
	public JTable getBoardTable() {
		if(jTable == null) {
			/*코드 추가*/		 
//			List<BoardDTO> boards = BoardDAO.getInstance().getBoards();
//			
//			for(int board :boards) {
//				Object[][] rowData = {BoardDTO.getBno()
//			}
			String[] columnNames = {"번호","제목","글쓴이","날짜","조회수"};
			DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
			
			
		        jTable = new JTable(tableModel);
		        refresh();
		
		        jTable.addMouseListener(new MouseAdapter() {
		        	public void mouseClicked(MouseEvent e) {
//		        	
		        		int rowIndex = jTable.getSelectedRow();
		        		int bno = (int) jTable.getValueAt(rowIndex, 0);
		        		new ViewDialog(BoardApp.this,bno).setVisible(true);
		        	}//배경 색 노란색으로 
				});//system out으로 클릭한게 인식되는지 확인 
		        // rowIndex, 0(bno의 column Index번호)
		       
		        jTable.addMouseListener(new MouseAdapter() {
		        	public void mouseClicked(MouseEvent e) {
		        		        		
		        		int rowIndex = jTable.getSelectedRow();
		        		int bno = (int) jTable.getValueAt(rowIndex, 0);
		        		int hitcount = (int) jTable.getValueAt(rowIndex, 4);
		        	
		        		BoardDAO.getInstance().addHitcount(hitcount,bno);
		        		refresh();
		        		
//		        		int hitcount = BoardDTO.getHitcount();
	        		
		        	}
		        	
		        });
		        
		}
		
		return jTable;
	}
	
	public void refresh()  {
		DefaultTableModel tableModel = (DefaultTableModel)jTable.getModel();
		tableModel.setRowCount(0);   //rowcount(가로)를 0으로 설정
		List<BoardDTO> boards = BoardDAO.getInstance().getBoards();
	       for (BoardDTO board : boards) {
	           Object[] rowData = {board.getBno(), board.getTitle(), board.getWriter(), board.getRegdate(), board.getHitcount()};
	            tableModel.addRow(rowData);
	        }
	}//새로 만든 메소드 
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			/*코드 추가*/
			pSouth = new JPanel();
			pSouth.add(getBtnInsert());
			
		}
		return pSouth;
	}

	public JButton getBtnInsert() {
		if(btnInsert == null) {
			/*코드 추가*/
			btnInsert = new JButton();
			btnInsert.setText("추가");
			btnInsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new InsertDialog(BoardApp.this).setVisible(true);
					
				}
			});
			
		}
		return btnInsert;
	}
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> {
        	BoardApp board = new BoardApp();
        	board.setVisible(true);
	    });
	}	
	
}
