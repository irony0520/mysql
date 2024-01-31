package model;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardDTO {
	private int bno;
	private String title;
	private String writer;
	private String content;
	private Date regdate;
	private int hitcount;
}
