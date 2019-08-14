package com.test.example.controller;

import com.simple.base.controller.BaseController;
import com.test.example.entity.Shop;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop")
public class ShopController extends BaseController<Shop,Long> {

}
