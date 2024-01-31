package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BoardDAO {
	private static final BoardDAO instance = new BoardDAO();
	private final String url = "jdbc:mysql://localhost/boarddemo";
	private final String user = "java";
	private final String password = "1234";
	
	//운반용 객체 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");		
			conn = DriverManager.getConnection(url, user, password);
		}catch(SQLException e) {
			e.printStackTrace();  //dialog로 메세지 생성 가능
			//JOptionPane.showmessageDialog(null,"연결할 수 없습니다.","확인",JOptionPane>WARNING_MESSAGE
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			//JOptionPane.showmessageDialog(null,"드라이버를 찾을 수 없습니다.","확인",JOptionPane>WARNING_MESSAGE
		}
		
	}
	
	private void close() throws SQLException {
		if(conn != null) conn.close();
		if(pstmt != null)pstmt.close();
		if(rs != null) rs.close();
	}
	
	public void insertBoard(BoardDTO board) {
		connect();
		sql = """
				insert into board
				(title,writer,content, regdate)
				values (?,?,?, now())
				""";
		//날짜가 not null상태일 순 없기 때문에 칸이 없더라도 입력을 해줘야 데이터베이스에 들어간다. .... .
		
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "게시글이 저장됐습니다.","입력 완료",JOptionPane.PLAIN_MESSAGE);			
			}else {
				JOptionPane.showMessageDialog(null, "게시글 저장에 실패했습니다.","입력 실패",JOptionPane.WARNING_MESSAGE);
			}
			close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}//추가 
		
	}
	
	public List<BoardDTO> getBoards() {
		connect();
		sql = """ 
				select *
				from board
				order by bno
				""";
		List<BoardDTO> list = new ArrayList<>();
		try {			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO board = new BoardDTO();
					board.setBno(rs.getInt(1));
				    board.setTitle(rs.getString(2));
				    board.setWriter(rs.getString(3));
				    board.setContent(rs.getString(4));
				    board.setRegdate(rs.getDate(5));
				    board.setHitcount(rs.getInt(6));
					list.add(board);			
			}		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}	
	public BoardDTO getBoardByBno(int bno) {
		connect();
		BoardDTO board = new BoardDTO();  //while문 바깥에서 미리 선언
		sql = "select * from board where bno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board.setBno(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setContent(rs.getString(4));
				board.setRegdate(rs.getDate(5));
				board.setHitcount(rs.getInt(6));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return board;
	}
	

	
	
	public void updateBoard(BoardDTO board, int bno) {
		
		connect();
		sql = """ 
				update board set
				title =?, writer =? , content = ? 
				where bno = ?
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4,bno);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "업데이트 완료됐습니다","업데이트 완료",JOptionPane.PLAIN_MESSAGE);				
			}else {
				JOptionPane.showMessageDialog(null, "업데이트 실패했습니다","업데이트 실패",JOptionPane.WARNING_MESSAGE);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}//추가 
	
	
	}		
	
	public void deleteBoard(int bno) {
		connect();
		sql = "delete from board where bno = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "삭제 완료됐습니다","삭제 완료",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "삭제 실패했습니다","삭제 실패",JOptionPane.WARNING_MESSAGE);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
						
	}
	
	public void addHitcount(int hitcount, int bno) {
		sql = """ 
				update board set
				hitcount = ?
				where bno=?
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hitcount +1);
			pstmt.setInt(2, bno);
			pstmt.executeUpdate();
			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	//유효성검사 적합한게 없을시 만들기(exception 패키지)
	
}
