package hunnuse.wyc;

import hunnuse.wyc.Server.AttractionService;
import hunnuse.wyc.Server.RouteService;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.ObjectUtils.min;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"hunnuse.wyc"})
@RestController
@MapperScan("hunnuse.wyc.dao")
public class App {

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
