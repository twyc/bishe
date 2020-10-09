package hunnuse.wyc.Server;

import hunnuse.wyc.dao.TravelogueMapper;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.min;

/**
 * @ClassName: RouteService
 * @Description: TODO
 * @author: tycw
 * @date: 2020/4/25  23:52
 */
@Service
public class RouteService {
    @Autowired
    private TravelogueMapper travelogueMapper;

    public List<Travelogue> listTravelogue(){
        return travelogueMapper.listTravelogue();
    }

    private StringUtil util = new StringUtil();


    /**
     * 根据想去的景点推荐路线
     * 只要这个景点在这个路线上出现了 那么这个路线就是可选的
     * @param Atts
     * @return
     */
    public List<Travelogue> getRouteByAtt(String Atts,List<Travelogue> yjList){
        List<Travelogue> ret = new ArrayList<>();
        String atts[] = Atts.split("\\s+");
//        System.out.println("打印由前端传入的景点串解析得的景点数组");
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

    public List<Travelogue> getRouteByChart(String chart,List<Travelogue> yjList){
        List<Travelogue> ret = new ArrayList<>();

        if (chart.charAt(2)=='1'){
            for (Travelogue doc:yjList) {
                if (doc.getKeywords().charAt(2)=='1'){
                    ret.add(doc);
                }
            }
        }
        else{
            for (Travelogue doc:yjList) {
                if (doc.getKeywords().equals(chart)){
                    ret.add(doc);
                }
            }
        }

        return ret;
    }

    /**
     * 把文件中的游记url读到list中
     * @return
     * @throws FileNotFoundException
     */
    List<String>getTravelogueUrl() throws IOException {
        String filePath=getClass().getClassLoader().getResource(".").getPath()+"contents/ygUrls.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String>travelogueUrl = new ArrayList<>();
        String line;
        while ((line=reader.readLine())!=null){
            travelogueUrl.add(line);
        }
        reader.close();
        return travelogueUrl;
    }

    public void insert2DB(List<WebGeoName> attList) throws IOException {
        String filePath=getClass().getClassLoader().getResource(".").getPath()+"contents/";
        String text;
        BufferedReader reader;
        List<String>travelogueUrl = getTravelogueUrl();
        int cnt=1;
        for (int i = 0; i < 247; i++) {
            reader=new BufferedReader(new FileReader(filePath+(i+1)+".txt"));
            text=reader.readLine();
            Travelogue travelogue = new Travelogue();
            travelogue.setSource(travelogueUrl.get(i));
            if (text==null)continue;
            travelogue.setText(text);
            String atts =getAttractions(text,attList);
            if (atts==null)continue;
            travelogue.setAttractions(atts);
            if (travelogue.getAttractions().equals(""))continue;
            travelogue.setAttsId(getAttsId(atts,attList));
            travelogue.setDocId(cnt++);
            System.out.println("cnt="+Integer.toString(cnt));
            travelogueMapper.insert(getRouteChart(travelogue,attList));
        }
    }

    public Travelogue getRouteChart(Travelogue route,List<WebGeoName>attractions){
            String atts[] = route.getAttractions().split(",");
            int chart2,chart3;
            chart2=chart3=0;
            for (int i = 0; i < atts.length; i++) {
                for (WebGeoName att:attractions){
                    if (att.getName().equals(atts[i])){
                        if (att.getKeywords().charAt(1)=='1'){
                            chart2++;
                        }
                        if (att.getKeywords().charAt(2)=='1'){
                            chart3++;
                        }
                        break;
                    }
                }
            }
            String chart=null;
            if (chart3>0){
                if (chart2>0)chart="111";
                else chart="101";
            }
            else{
                if (chart2>0)chart="110";
                else chart="100";
            }
            route.setKeywords(chart);
            route.setAttractions(change(route.getAttractions()));
            return route;
    }

    /**
     * 把用逗号隔开的景点名称整理为用->隔开
     * @param str
     * @return
     */
    private String change(String str){
        String atts[] = str.split(",");
        int len=atts.length;
        String ret=atts[0];
        for (int i = 1; i < len; i++) {
            ret=ret+"->"+atts[i];
        }
        return ret;
    }

    /**
     * 由一串景点名字得到一串坐标
     * 用不到 返回null
     * @param str
     * @return
     */
    private String getAttsId(String str,List<WebGeoName> attList){
        String ret="";
        String ss[] = str.split(",");
        for (int i = 0; i < ss.length; i++) {
            for (WebGeoName w:attList) {
                if (w.getName().contains("湘江"))continue;
                if (w.getName().contains("桥"))continue;
                if (w.getName().equals(ss[i])){
                    ret+=Double.toString(w.getLatitude());
                    ret+=",";
                    ret+=Double.toString(w.getLongitude());
                    ret+="&";
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 传入所有景点的attList和当前游记的text 得到一个数字串 代表旅游的顺序
     * 模式串数组attList和主串text
     * 返回attList里的元素在text中出现的顺序
     * @return
     */
    private String getAttractions(String text,List<WebGeoName> attList){
        int attListLen=attList.size();
        int pos[] = new int[attListLen];
        int vis[] = new int[attListLen];
        int next[]= util.getNext(text);
        for (int i = 0; i < attListLen; i++) {
            pos[i]=util.kmp(text,attList.get(i).getName(),next);
        }
        /**
         * 对pos数组进行选择排序 每次选出最小的元素所在的index
         */
        int cnt=0;
        String ret="";
        for (int i = 0; i < attListLen; i++) {
            int min=0x7fffffff;
            int flag=-1;
            for (int j = 0; j < attListLen; j++) {
                if (pos[j]!=-1 && pos[j]<min&&vis[j]==0){
                    flag=j;
                    min=pos[j];
                }
            }
            if(flag==-1)break;
            vis[flag]=1;
            if (attList.get(flag).getName().contains("湘江"))continue;
            if (attList.get(flag).getName().contains("桥"))continue;
            ret+=attList.get(flag).getName();
            ret+=",";
            cnt++;
        }
        if (cnt==1)
            return null;
        System.out.println("ret="+ret);
        return ret;
    }

    /**
     * 一次性方法
     * 在Controller里面调用的
     * 用来更新数据库里的游记路线格式
     * @param tt
     */
    public void update(Travelogue tt){
        travelogueMapper.deleteByPrimaryKey(tt.getDocId());
        travelogueMapper.insert(tt);
    }
}
