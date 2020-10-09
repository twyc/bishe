package hunnuse.wyc.controller;

import hunnuse.wyc.Server.RuleService;
import hunnuse.wyc.dao.RuleMapper;
import hunnuse.wyc.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: RuleController
 * @Description: 实现关联规则算法 将生成的规则存入数据库
 * @author: tycw
 * @date: 2020/5/12  14:49
 */
@Controller
@RequestMapping(value = "/rule",method = {RequestMethod.GET})
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
@ResponseBody
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @RequestMapping("/getRule")
    public CommonReturnType getRule(){
        System.out.println("getRule is in");
        return ruleService.apriori();
    }
}
