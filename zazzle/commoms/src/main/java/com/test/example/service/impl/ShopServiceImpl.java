package com.test.example.service.impl;

import com.simple.base.service.impl.BaseServiceImpl;
import com.test.example.entity.Shop;
import com.test.example.service.ShopService;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop, Long> implements ShopService {
}
