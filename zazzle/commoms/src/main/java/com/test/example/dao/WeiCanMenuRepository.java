package com.test.example.dao;

import com.simple.base.dao.BaseRepository;
import com.test.example.entity.WeiCanMenu;
import org.springframework.stereotype.Repository;

@Repository
public interface WeiCanMenuRepository extends BaseRepository<WeiCanMenu,Long> {
}
