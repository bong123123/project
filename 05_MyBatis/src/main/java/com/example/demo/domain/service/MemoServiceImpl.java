package com.example.demo.domain.service;

import com.example.demo.domain.dao.MemoDaoImpl;
import com.example.demo.domain.dto.MemoDto;
import com.example.demo.domain.mapper.MemoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemoServiceImpl {
	
	@Autowired
	private MemoDaoImpl memoDaoImpl;

	
	public boolean memoRegistration(MemoDto memoDto) throws Exception {
		//Dao - 메모 Insert이후 true/false 반환
		int result = memoDaoImpl.insert(memoDto);
		return result > 0;
	}
	
	@Autowired
	private MemoMapper memoMapper;
	
	public boolean memoRegistration_mapper(MemoDto memoDto) throws Exception {
		//Dao - 메모 Insert이후 true/false 반환
		int result = memoMapper.Insert(memoDto);
		return result > 0;
	}
}
