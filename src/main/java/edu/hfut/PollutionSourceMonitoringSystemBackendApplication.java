package edu.hfut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("edu.hfut.dao")
public class PollutionSourceMonitoringSystemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollutionSourceMonitoringSystemBackendApplication.class, args);
    }

}
