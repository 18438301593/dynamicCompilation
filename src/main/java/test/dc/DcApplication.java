package test.dc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("test.dc")
public class DcApplication {
	public static void main(String[] args) {
        SpringApplication.run(DcApplication.class, args);
    }
}
