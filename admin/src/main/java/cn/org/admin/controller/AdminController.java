package cn.org.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 测试1
 */
@RestController
public class AdminController {



    @RequestMapping({"/login","/"})
    public String login(){
        return  "登陆成功！";
    }

}
