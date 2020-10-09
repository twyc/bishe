package hunnuse.wyc.Server;

import hunnuse.wyc.dao.RuleMapper;
import hunnuse.wyc.dao.TravelogueMapper;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.Rule;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName: RuleService
 * @Description: TODO
 * @author: tycw
 * @date: 2020/5/12  14:52
 */
@Service
public class RuleService {
    @Autowired
    private WebGeoNameMapper webGeoNameMapper;
    @Autowired
    private TravelogueMapper travelogueMapper;
    @Autowired
    private RuleMapper ruleMapper;
    public CommonReturnType apriori(){
        List<WebGeoName> atts = webGeoNameMapper.listAttraction();
        List<Travelogue> routes = travelogueMapper.listTravelogue();
        Map<WebGeoName,Integer> support = getSupport(atts,routes,2);
        //现在这个Map里存放的即为L1
        List<Rule>L1 = new ArrayList<>();
        for (WebGeoName att:support.keySet()){
            Rule rule = new Rule();
            rule.setAtts(att.getName());
            int occ=support.get(att);
            if (occ==0)continue;
            rule.setOccurence(occ);
            rule.setSize(1);
            L1.add(rule);
            ruleMapper.insert(rule);
        }
        dfs(L1,atts,routes,2);
        return CommonReturnType.create("sucess");
    }
    private void dfs(List<Rule>lk,List<WebGeoName>atts,List<Travelogue>routes,int miniSupport){
        ArrayList<Rule> lk1 = getLkPlus1(lk,atts,routes,miniSupport);
        if (lk1==null)return;
        for (Rule rule:lk1){
            ruleMapper.insert(rule);
        }
        dfs(lk1,atts,routes,miniSupport);
    }

    /**
     * 由Lk得到Lk+1
     * @param lk
     * @param atts
     * @param routes
     * @param miniSupport
     * @return
     */
    private ArrayList<Rule> getLkPlus1(List<Rule>lk,List<WebGeoName>atts,List<Travelogue>routes,int miniSupport){
        if (lk==null)return null;
        int len=lk.size();
        ArrayList<Rule>ret = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                Rule rule = merge(lk.get(i),lk.get(j));
                if (rule!=null){
                    int occ=getOccurence(routes,rule.getAtts());
                    rule.setOccurence(occ);
                    if (occ>1){
                        ret.add(rule);
                    }
                }
            }
        }
        ret=distinct(ret);
        return ret;
    }
    private ArrayList<Rule> distinct(ArrayList<Rule> list){
        ArrayList<Rule> ret = new ArrayList<>();
        for (Rule rule:list) {
            if (!contain(ret,rule.getAtts())){
                ret.add(rule);
            }
        }
        return ret;
    }
    boolean contain(ArrayList<Rule> ret,String Atts){
        String atts[] = Atts.split(",");
        for (Rule rule:ret){
            String atts1[] = rule.getAtts().split(",");
            if (same(atts,atts1)){
                return true;
            }
        }
        return false;
    }
    boolean same(String a[],String b[]){
        int len=a.length;
        if (len!=b.length)return false;
        for (String att:a){
            if (!Arrays.asList(b).contains(att)){
                return false;
            }
        }
        return true;
    }
    private int getOccurence(List<Travelogue>routes,String attss){
        int ret=0;
        String atts[] = attss.split(",");
        for (Travelogue route:routes){
            boolean flag=false;
            for (String att:atts){
                if (!route.getAttractions().contains(att)){
                    flag=true;
                    break;
                }
            }
            if (!flag){
                ret++;
            }
        }
        return ret;
    }
    /**
     * 判断两条规则是否可以合并
     * @param a
     * @param b
     * @return
     */
    private Rule merge(Rule a,Rule b){
        String atts1[] = a.getAtts().split(",");
        String atts2[] = b.getAtts().split(",");
        int cnt=0;
        for (String att1:atts1){
            for (String att2:atts2){
                if (att1.equals(att2)){
                    cnt++;
                    break;
                }
            }
        }
        if (cnt!=atts1.length-1){
            return null;
        }
        String atts = null;
        for (String att1:atts1){
            if (!Arrays.asList(atts2).contains(att1)){
                atts = b.getAtts()+","+att1;
                break;
            }
        }
        Rule rule = new Rule();
        rule.setSize(atts1.length+1);
        rule.setAtts(atts);
        return rule;
    }
    /**
     * 得到L0的方法
     * @param atts
     * @param routes
     * @param miniSupport
     * @return
     */
    private Map<WebGeoName,Integer> getSupport(List<WebGeoName> atts, List<Travelogue> routes,int miniSupport){
        Map<WebGeoName,Integer>ret = new HashMap<>();
        for (WebGeoName att:atts){
            int cnt=0;
            for (Travelogue route:routes){
                if (route.getAttractions().contains(att.getName())){
                    cnt++;
                }
            }
            if (cnt>=miniSupport){
                ret.put(att,cnt);
            }
        }
        return ret;
    }
}
