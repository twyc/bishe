package hunnuse.wyc.Server;

import hunnuse.wyc.dao.RuleMapper;
import hunnuse.wyc.dao.TravelogueMapper;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.Rule;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.response.CommonReturnType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: AttractionService
 * @Description: WebGeoName相关的稍复杂操作
 * @author: tycw
 * @date: 2020/4/25  10:42
 */
@Service
public class AttractionService {
    @Autowired
    private WebGeoNameMapper webGeoNameMapper;
    @Autowired
    private TravelogueMapper travelogueMapper;
    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private OkHttpClient client = new OkHttpClient();

    private static final String redisKey = "hotAttraction";

    public List<WebGeoName> listAttractions() {
        return webGeoNameMapper.listAttraction();
    }

    public void didClickAttraction(Integer id) {
        this.redisTemplate.opsForZSet().incrementScore(redisKey, id, 1);
    }

    /**
     * 从redis中取出排名0~count的景点
     * @param count
     * @return
     */
    public List<WebGeoName> getHotAttractions(Integer count) {
        Set<Integer> attIDSet = this.redisTemplate.opsForZSet().reverseRange(redisKey, 0, count);
        Integer[] attIDs = attIDSet.toArray(new Integer[0]);
        List<WebGeoName> attsList = new ArrayList<WebGeoName>();
        for (Integer id : attIDs) {
            WebGeoName att = this.webGeoNameMapper.selectByPrimaryKey(id);
            attsList.add(att);
        }
        return attsList;
    }

    public List<WebGeoName> getByChart(String chart){
        List<WebGeoName> list = webGeoNameMapper.listAttraction();
        List<WebGeoName> ret = new ArrayList<>();
        if (chart.charAt(2)=='1'){
            for (WebGeoName att:list){
                if (att.getKeywords().charAt(2)=='1'){
                    ret.add(att);
                }
            }
        }
        else{
            for (WebGeoName att:list){
                if (chart.equals(att.getKeywords())){
                    ret.add(att);
                }
            }
        }
        Random random = new Random();
        while (ret.size()<6){
            ret.add(list.get(random.nextInt(list.size())));
        }
        ret=ret.stream().distinct().collect(Collectors.toList());
        if (ret.size()>3){
            ret=ret.subList(0,3);
        }
        return ret;
    }

    public ArrayList<String> getAtt() throws IOException{
//        String filePath="src/main/resources/attraction";
        String filePath=getClass().getClassLoader().getResource(".").getPath()+"attraction";
        BufferedReader reader=new BufferedReader(new FileReader(filePath));
        ArrayList<String> ret = new ArrayList<>();
        String line;
        while((line=reader.readLine())!=null)
        {
            ret.add(line);
        }
        reader.close();
        return ret;
    }

    // 景点推荐功能的核心代码
    public CommonReturnType getAttraction(String attsWant){
        List<Rule>rules = ruleMapper.listRule();
        String atts[] = attsWant.split(",");
        Collections.reverse(rules); //为了返回尽量多的景点 即lk的k尽可能大
        for (Rule rule:rules){
            boolean flag = false; //判断当前这个rule是否是想去的景点的超集
            for (String att:atts){
                if (!rule.getAtts().contains(att)){
                    flag=true;
                    break;
                }
            }
            if (!flag){
                return CommonReturnType.create(except(rule.getAtts(),attsWant).toArray());
            }
        }
        return CommonReturnType.create(null);
    }
    private List<WebGeoName>except(String atts,String d){
        String att1[] = atts.split(",");
        String att2[] = d.split(",");
        List<WebGeoName>ret = new ArrayList<>();
        for (String att:att1){
            if (!Arrays.asList(att2).contains(att)){
                ret.add(webGeoNameMapper.selectByName(att).get(0));
            }
        }
        return ret;
    }
    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response);
            return response.body().string();
        }
    }

    public void Insert2DB() throws IOException {
        if (webGeoNameMapper == null){
            System.out.println("null");
        }else{
            System.out.println("not null");
        }
        List<String> lines = getAtt();
        int cnt=0;
        for (String tt:lines){
            WebGeoName webGeoName = new WebGeoName();
            webGeoName.setWebgeonameid(++cnt);
            webGeoName.setName(tt);
            String url = "http://restapi.amap.com/v3/geocode/geo?key=dba359696d9128e480e86a54ca9944f1&address="+tt+"&city=长沙";
            String response = run(url);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray ja = jsonObject.getJSONArray("geocodes");
            JSONObject ture = null;
            if (ja.length()>0){
                ture= (JSONObject) ja.get(0);
            } else {
                continue;
            }
            if (ture==null)continue;
            HashMap<String,Object> map = (HashMap<String, Object>) ture.toMap();
            String att[] = ((String) map.get("location")).split(",");
            webGeoName.setLatitude(Double.valueOf(att[0]));
            webGeoName.setLongitude(Double.valueOf(att[1]));
            //人文 自然 美食
            int chart2,chart3;
            chart2=chart3=0;
            if (tt.contains("大学")){
                chart2=1;
            }
            if (tt.contains("园")){
                chart2=1;
            }
            String chart="1";
            if (chart2==1) chart+="1";
            else    chart+="0";
            if (chart3==1) chart+="1";
            else    chart+="0";
            webGeoName.setKeywords(chart);
            webGeoNameMapper.insert(webGeoName);
            System.out.println(webGeoName.toString());
        }
    }
}
