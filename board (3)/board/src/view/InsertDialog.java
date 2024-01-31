package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.BoardDAO;
import model.BoardDTO;

public class InsertDialog extends JDialog {
	private BoardApp boardApp;
	private JPanel pCenter, pTitle, pContent, pWriter, pSouth;
	private JTextField txtTitle, txtWriter;
	private JTextArea txtContent;
	private JButton btnOk, btnCancel;
	
	public InsertDialog(BoardApp boardApp) {
		this.boardApp = boardApp;
		this.setTitle("게시물 작성");					
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
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			/*코드 추가*/
			//pCenter.setLayout(new GridLayout(3,1));
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
			
		}//사이즈 설정하려면 label, txtTitle 다 변수 따로 준다음 setAlignMment, preferredSIze, 로 크기조절
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
			/*코드 추가*/
			
			//pSouth.setBackgruond(Color,WHITE);
		   pSouth = new JPanel();
           pSouth.add(getBtnOk());
           pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new JButton("저장");
			/*코드 추가*/
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					BoardDTO board = new BoardDTO();
					board.setTitle(txtTitle.getText());   
					board.setWriter(txtWriter.getText());                
					board.setContent(txtContent.getText());    
					BoardDAO.getInstance().insertBoard(board);
					InsertDialog.this.dispose();
					boardApp.refresh();
					
				}
			});
			//1.tTitle, txtWriter,txtContent 3개의 text를 가져온다(getText)
			//2.BoardDto 인스턴스 생성 
			//3.board에 setTitle 이용해서 그대로 값을 넣어준다
			
		
			//저장된 후 getBoard 부분을 한번 새로고침 해줘야됨 
		}
		return btnOk;
	}
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton("취소");
			/*코드 추가*/
			btnCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					InsertDialog.this.dispose();
					//누를때 효과 
					
				}
			});
		}
		return btnCancel;
	}	
	//텍스트 필드 크기 조정 
	
}


