package hunnuse.wyc;

import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.tt.Attraction2DB;
import hunnuse.wyc.tt.Travelogue2DB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"hunnuse.wyc"})
@RestController
@MapperScan("hunnuse.wyc.dao")
public class App {

    @Autowired
    private Attraction2DB attraction2DB;
    @RequestMapping("/getAttData")
    public String getAttData(){
        try {
            attraction2DB.Insert2DB();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "插入景点数据成功";
    }

    @Autowired
    private Travelogue2DB travelogue2DB;
    @RequestMapping("/getTravelogueData")
    public String getTravelogueData(){
        try {
            travelogue2DB.insert2DB();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "插入游记路线数据成功";
    }

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
