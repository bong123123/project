package com.example.demo.domain.dao;

import com.example.demo.domain.dto.MemoDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


@Repository
public class MemoDaoImpl {
	
	
//	@Autowired
//	private DataSource dataSource;
//
//
//
//	public int insert(MemoDto memoDto) throws Exception {
//		//tbl_memo에 insert처리 후 증가 행 반환
//		Connection conn = dataSource.getConnection();
//		PreparedStatement pstmt = conn.prepareStatement("insert into tbl_memo values(null,?,?,now())");
//		pstmt.setString(1, memoDto.getText());
//		pstmt.setString(2, memoDto.getWriter());
//		int result = pstmt.executeUpdate();
//
//		return result;
//	}

	@Autowired
	private SqlSession sqlSession;
	private static String namespace = "com.example.demo.domain.mapper.MemoMapper.";

	public int insert(MemoDto memoDto) throws SQLException {
		sqlSession.insert(namespace+"Insert",memoDto);

		return memoDto.getId();
	}
	public int insertTx(MemoDto memoDto) throws SQLException{
		sqlSession.insert(namespace+"InsertTx",memoDto);
		return memoDto.getId();
	}
	
}
