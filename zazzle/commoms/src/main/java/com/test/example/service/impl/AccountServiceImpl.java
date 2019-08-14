package com.test.example.service.impl;

import com.simple.base.service.impl.BaseServiceImpl;
import com.test.example.entity.Account;
import com.test.example.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService {
}
