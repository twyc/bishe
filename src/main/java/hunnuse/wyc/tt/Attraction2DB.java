package hunnuse.wyc.tt;

import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.WebGeoName;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

/**
 * @ClassName: Attraction2DB
 * @Description: 连接数据库 将attraction中的景点坐标插入数据库 因为要用到mybatis 所以仍然需要将其标记为bean
 * @author: tycw
 * @date: 2020/4/14  15:27
 */
@Component
public class Attraction2DB {

    @Autowired
    private WebGeoNameMapper webGeoNameMapper;

    private String filePath="src/main/resources/attraction";
    public ArrayList<String> getAtt() throws IOException{
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

    public void Insert2DB() throws IOException {
        if (webGeoNameMapper == null){
            System.out.println("null");
        }else{
            System.out.println("not null");
        }
        ArrayList<String> lines = getAtt();
        int cnt=0;
        for (String tt:lines){
            String att[] = tt.split("\\s+");
            WebGeoName webGeoName = new WebGeoName();
            webGeoName.setWebgeonameid(++cnt);
            webGeoName.setName(att[0]);
            webGeoName.setLatitude(Double.valueOf(att[1]));
            webGeoName.setLongitude(Double.valueOf(att[2]));
            webGeoName.setOccurence(0);
            webGeoName.setWebgeonamecontent("");
            webGeoNameMapper.insert(webGeoName);
            System.out.println(webGeoName.toString());
        }
    }

}
