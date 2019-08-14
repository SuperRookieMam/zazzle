package com.test.example.controller;

import com.simple.base.controller.BaseController;
import com.test.example.entity.WeiCanMenu;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weiCanMenu")
public class WeiCanMenuController extends BaseController<WeiCanMenu,Long> {

}
