package com.example.demo.domain.mapper;

import com.example.demo.domain.dao.MemoDaoImpl;
import com.example.demo.domain.dto.MemoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoMapperTest {

    @Autowired
    private MemoMapper memoMapper;

    @Test
    void insertMemo() {
        memoMapper.Insert(new MemoDto(-1,"aa","bb@naver.com"));
    }

}