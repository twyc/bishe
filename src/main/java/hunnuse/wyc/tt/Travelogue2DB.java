package hunnuse.wyc.tt;

import hunnuse.wyc.dao.TravelogueMapper;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Travelogue2DB
 * @Description: 将resources/content下面的游记插入数据库
 * @author: tycw
 * @date: 2020/4/17  11:06
 */
@Component
public class Travelogue2DB {
    public static final int Count = 247;
//            "src/main/resources/contents/";
    private ArrayList<String>travelogueUrl = new ArrayList<>();
    private List<WebGeoName> attList;
    private StringUtil util = new StringUtil();
    @Autowired
    private TravelogueMapper travelogueMapper;
    @Autowired
    private WebGeoNameMapper webGeoNameMapper;

    public Travelogue2DB() throws IOException {
//        String filePath=getClass().getClassLoader().getResource(".").getPath()+"contents/";
//        System.out.println("filepath="+filePath);
//        BufferedReader reader=new BufferedReader(new FileReader(filePath+"ygUrls.txt"));
//        String line;
//        while((line=reader.readLine())!=null)
//        {
//            travelogueUrl.add(line);
//        }
//        reader.close();
    }


    /**
     * 传入所有景点的attList和当前游记的text 得到一个数字串 代表旅游的顺序
     * 模式串数组attList和主串text
     * 返回attList里的元素在text中出现的顺序
     * @return
     */
    private String getAttractions(String text){
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
            ret+=attList.get(flag).getName();
            ret+=",";
        }
        return ret;
    }

    /**
     * 由一串景点名字得到一串坐标
     * @param str
     * @return
     */
    private String getAttsId(String str){
        String ret="";
        String ss[] = str.split(",");
        for (int i = 0; i < ss.length; i++) {
            for (WebGeoName w:attList) {
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
    public void insert2DB() throws IOException {
        String filePath=getClass().getClassLoader().getResource(".").getPath()+"contents/";
        attList=webGeoNameMapper.listAttraction();
        String text;
        BufferedReader reader;
        for (int i = 0; i < Count; i++) {

            reader=new BufferedReader(new FileReader(filePath+(i+1)+".txt"));
            text=reader.readLine();
            Travelogue travelogue = new Travelogue();
            travelogue.setDocId(i+1);
            travelogue.setSource(travelogueUrl.get(i));
            travelogue.setText(text);
            if(text!=null) {
                travelogue.setAttractions(getAttractions(text));
                travelogue.setAttsId(getAttsId(travelogue.getAttractions()));
            }else{
                travelogue.setAttractions(null);
            }
            travelogueMapper.insert(travelogue);
        }
    }
}
