package hunnuse.wyc.controller;

import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @ClassName: AttractionController
 * @Description: 景点controller
 * @author: tycw
 * @date: 2020/4/14  15:27
 */
@Controller
@RequestMapping(value = "/attraction",method = {RequestMethod.GET})
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
@ResponseBody
public class AttractionController {
    @Autowired
    private WebGeoNameMapper webGeoNameMapper;

    @RequestMapping("/list")
    public CommonReturnType listAttraction(){
        List<WebGeoName> list = webGeoNameMapper.listAttraction();
        return CommonReturnType.create(list);
    }

}
