package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.BoardDAO;
import model.BoardDTO;

public class ViewDialog extends JDialog {
	private BoardApp boardApp;
	private JPanel pCenter, pTitle, pContent, pWriter, pSouth;
	private JTextField txtTitle, txtWriter;
	private JTextArea txtContent;
	private JButton btnUpdate, btnDelete, btnClose;
	private int bno;
	
	public ViewDialog(BoardApp boardApp, int bno) {
		this.boardApp = boardApp;
		this.bno = bno;
		this.setTitle("게시물 보기");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);	
		this.setModal(true);
		this.setSize(400, 270);
		
		this.setLocation(
			boardApp.getLocationOnScreen().x + (boardApp.getWidth()-this.getWidth())/2,
			boardApp.getLocationOnScreen().y + (boardApp.getHeight()-this.getHeight())/2
		);
		
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.setBoard();
		//
		//this.getContentPane().add(getJTable(),BorderLayout.CENTER);
		//
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			/*코드 추가*/	
			pCenter = new JPanel();
			pCenter.add(getPTitle());
			pCenter.add(getPWriter());
			pCenter.add(getPContent());
				
		}
		return pCenter;
	}
	
	public JPanel getPTitle() {
		if(pTitle==null) {
			/*코드 추가*/
			pTitle = new JPanel();
			pTitle.add(new JLabel("제목 ", JLabel.LEFT));
			txtTitle = new JTextField(30);
			pTitle.add(txtTitle);		
		}
		return pTitle;
	}	
	
	public JPanel getPWriter() {
		if(pWriter==null) {
			/*코드 추가*/
			pWriter = new JPanel(); 
			pWriter.add(new JLabel("글쓴이", JLabel.LEFT));
			txtWriter = new JTextField(30);
			pWriter.add(txtWriter);
		}
		return pWriter;
	}		
	
	public JPanel getPContent() {
		if(pContent == null) {
			/*코드 추가*/
			pContent = new JPanel();
			pContent.add(new JLabel("내용", JLabel.LEFT));
			//setsize dimension
			txtContent = new JTextArea(5,30);
			pContent.add(txtContent);
		}
		return pContent;
	}

	public JPanel getPSouth() {
		if(pSouth == null) {
			//
			pSouth = new JPanel();
			
			pSouth.add(getBtnUpdate());
			pSouth.add(getBtnDelete());
			pSouth.add(getBtnClose());		
			
		}
		return pSouth;
	}
	
	public void setBoard() {
		/*코드 추가*/
		BoardDTO board = BoardDAO.getInstance().getBoardByBno(bno);
		//BoardDAO.getInstance().addHitcount(board.getHitcount(), bno);
		txtTitle.setText(board.getTitle());
		txtWriter.setText(board.getWriter());
		txtContent.setText(board.getContent());		
	}
	
	public JButton getBtnUpdate() {
		if(btnUpdate == null) {
			//
			btnUpdate = new JButton("수정");
			
			btnUpdate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					BoardDTO board = new BoardDTO();
					board.setTitle(txtTitle.getText());   
					board.setWriter(txtWriter.getText());                
					board.setContent(txtContent.getText());    
					BoardDAO.getInstance().updateBoard(board,bno);
					ViewDialog.this.dispose();
					boardApp.refresh();
				}//수정 
			});		
		}
		return btnUpdate;
		
	}
	
	public JButton getBtnDelete() {
		if(btnDelete == null) {
			/*코드 추가*/
			btnDelete = new JButton("삭제");
			
			btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				
//				int rowIndex = boardApp.getBoardTable().getSelectedRow();
//					if(rowIndex != -1) {
//						int bno = (int) boardApp.getBoardTable().getValueAt(rowIndex, 0);
	                   BoardDAO.getInstance().deleteBoard(bno);
	                   boardApp.refresh();
	                   dispose();
//					}else {
//						JOptionPane.showMessageDialog(null, "삭제 실패했습니다","삭제 실패",JOptionPane.WARNING_MESSAGE);
//					}
				}
			});//if문으로 감쌀필요 x (rowIndex != -1)로 확인하는 과정없이(행을선택하는 과정)없이 그냥 삭제
			//만약 삭제 여부를 다시 물어봐야한다면 그때는 if문으로 감싸야할듯
		}
		return btnDelete;
		 
	}	
	
	public JButton getBtnClose() {
		if(btnClose == null) {
			/*코드 추가*/
			btnClose = new JButton("닫기");
			btnClose.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ViewDialog.this.dispose();
					
				}
			});
		}
		return btnClose;
	}	
	//p180

	//마우스로 누르면(mouseCLicked) viewDialog 창이 뜨고, viewDialog창이 떠 있는 동안 getBoard의 게시글은 노란색 바탕이 된다. .. ...... .. . . 
}

//저장버튼, 수정, 삭제 버튼 눌렀을때 새로고침되도록 

