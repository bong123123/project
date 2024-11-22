package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.MemoDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemoMapper {
	
//	Annotation을 이용한 Mapping
	@Insert(value="insert into tbl_memo values(null,#{text},#{writer},now())")
	public int Insert(MemoDto memoDto);
	
	@Update(value="update tbl_memo set text = #{text} where id = #{id}")
	public int Update(MemoDto memoDto);
	
	@Delete(value="delete from tbl_memo where id = #{id}")
	public int Delete(@Param("id") int id);
	
	@Select(value="select * from tbl_memo where id = #{id}")
	public MemoDto SelectOne(@Param("id") int id);
	
	@Select(value="select * from tbl_memo")
	public List<MemoDto> SelectAll();
	
	@Results(id="MemoResultMap",value= {
			@Result(property = "id", column = "id"),
			@Result(property = "text", column = "text")
	})
	@Select(value="select id,text from tbl_memo")
	public List<Map<String,Object>> SelectAllByResultMap();
	
//	xml을 이용한 Mapping
	public int Insert_xml(MemoDto memoDto);
}
