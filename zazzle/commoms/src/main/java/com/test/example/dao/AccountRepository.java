package com.test.example.dao;

import com.simple.base.dao.BaseRepository;
import com.test.example.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account,Long> {
}
