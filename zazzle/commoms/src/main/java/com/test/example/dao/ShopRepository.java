package com.test.example.dao;

import com.simple.base.dao.BaseRepository;
import com.test.example.entity.Shop;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends BaseRepository<Shop,Long> {
}
