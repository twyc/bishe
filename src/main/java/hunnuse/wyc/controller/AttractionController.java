package hunnuse.wyc.controller;

import hunnuse.wyc.Server.AttractionService;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.response.CommonReturnType;
import hunnuse.wyc.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


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

    @Autowired
    private AttractionService attractionService;

    @RequestMapping("/list")
    public CommonReturnType listAttraction(){
        List<WebGeoName> list = webGeoNameMapper.listAttraction();
        return CommonReturnType.create(list);
    }

    // TODO 使用springboot定时自动任务把手动访问这个接口的工作下掉
    @RequestMapping("/insertAttraction2DB")
    public String insertAttraction2DB() throws IOException {
        attractionService.Insert2DB();
        return "插入景点成功";
    }

    /**
     * 根据request里面的参数 返回推荐的景点
     * @return 与用户输入景点在同一个频繁项集中的其他景点
     */
    @RequestMapping(value = "/getattraction",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getAttraction(@RequestParam(name = "attsWant")String attsWant){
        return attractionService.getAttraction(attsWant);
    }

    @RequestMapping(value = "/getAttractionsByChart",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getRouteByChart(@RequestParam(name = "chart")String chart){
        List<WebGeoName>list = attractionService.getByChart(chart);
        return CommonReturnType.create(list.toArray());
    }

    @RequestMapping(value = "/getHotAttractions",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getHotAttractions(@RequestParam(name = "count")Integer count) {
        List<WebGeoName>list = attractionService.getHotAttractions(count);
        return CommonReturnType.create(list.toArray());
    }

    @RequestMapping(value = "/didClickAttraction",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType didClickAttraction(@RequestParam(name = "id")Integer id) {
        attractionService.didClickAttraction(id);
        return CommonReturnType.create(null);
    }

}
