package hunnuse.wyc.Server;

import hunnuse.wyc.dao.TravelogueMapper;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Recommend
 * @Description: TODO
 * @author: tycw
 * @date: 2020/4/17  18:36
 */
@Service
public class Recommend {
    @Autowired
    private TravelogueMapper travelogueMapper;

    private StringUtil util = new StringUtil();
    /**
     * 根据想去的景点推荐路线
     * 只要这个景点在这个路线上出现了 那么这个路线就是可选的
     * @param Atts
     * @return
     */
    public List<Travelogue> getRecommendRoute(String Atts,List<Travelogue> yjList){
        List<Travelogue> ret = new ArrayList<>();
        String atts[] = Atts.split("\\s+");
        System.out.println("打印由前端传入的景点串解析得的景点数组");
        for (int i = 0; i < atts.length; i++) {
            System.out.println(atts[i]);
        }
        for (Travelogue doc:yjList) {
            if (doc.getAttractions()==null)continue;
            int next[] = util.getNext(doc.getAttractions());
            boolean flag=false;
            for (int i = 0; i < atts.length; i++) {
                if (util.kmp(doc.getAttractions(),atts[i],next)!=-1){
                    flag=true;
                    break;
                }
            }
            if (flag){
                ret.add(doc);
            }
        }
        return ret;
    }
}
