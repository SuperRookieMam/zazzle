package com.test.example.dao;

import com.simple.base.dao.BaseRepository;
import com.test.example.log.Log;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends BaseRepository<Log,Long> {
}
