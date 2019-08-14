package com.test.example.controller;

import com.simple.base.controller.BaseController;
import com.test.example.entity.WeiCanCaiPin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weiCanCaiPin")
public class WeiCanCaiPinController extends BaseController<WeiCanCaiPin,Long> {

}
