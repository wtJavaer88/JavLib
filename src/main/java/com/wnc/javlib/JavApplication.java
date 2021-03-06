package com.wnc.javlib;


import com.wnc.javlib.spy.JavSpy;
import com.wnc.javlib.utils.ProxyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.wnc"})
@EnableJpaAuditing
@EnableTransactionManagement
public class JavApplication implements CommandLineRunner {
    @Autowired
    private JavSpy javSpy;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(JavApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("项目启动...");

        if (args != null && args.length > 0) {
            new ProxyUtil().initProxyPool();
            spyFromTagDir();

            while (true) {
                Thread.sleep(10000000L);
            }
        }
    }

    private void spyFromTagDir() {
        javSpy.getAll();
    }
}
