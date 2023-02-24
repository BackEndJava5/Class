package com.ssamz.biz.board;

import java.sql.Date;

import lombok.Data;

@Data

// VO(Value Object) 클래스
public class BoardVO {
	private int seq;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private int cnt;

	// 검색 관련 변수
	private String searchCondition;	
	private String searchKeyword;
}
