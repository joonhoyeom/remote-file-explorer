package com.estsoft.rfe.repository;

import com.estsoft.rfe.vo.AdminVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by joonho on 2017-04-05.
 */
@Repository
public class AdminRepository {
    @Autowired
    private SqlSession sqlSession;

    public AdminVo findByEmailPassword(AdminVo vo ) {
        return sqlSession.selectOne("admin.selectByEmailPassword", vo);
    }
}