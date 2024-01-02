package com.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableAsync
@EnableMongoRepositories("com.edge.repositories")
@PropertySources({
        @PropertySource("classpath:config/core.properties"),
        @PropertySource("classpath:config/app-constants.properties"),
        @PropertySource("classpath:config/app-${APP_ENV:dev}.properties"),
        @PropertySource(value = "file:config/external.properties", ignoreResourceNotFound = true)
})
public class MeetMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetMateApplication.class, args);
    }

}

/*

-DAPP_ENV=dev

 Not required:
 -DT_ENV=Vt6zStkzqnZ3pFCSnJS9STuap19d1pBMilIBMgGQcKk -DU_ENV=UCVEQNZRHDFIF3YNJ4H4 -DD_ENV=mongodb+srv://myuser11:password77@lagnsthal-1.qnb9t70.mongodb.net/test -Dhazelcast.diagnostics.enabled=false -DAPP_DNS=lagnsthal -DAPP_DNS_EXT=com -DAPP_NAME=MarryEazy -DI_1=meetmate -DX_1=cVHp8HgRcJ -DW_1=6YPq0dnp -DY_1=5909198 -Dcache.discovery=local -DNUM=918888084629 -DENUM=918888084629 -DTAK=8NmHMf+dd2M-qwLHFoHtjAefxNhYNeGAuGlrk5Q5SP -DSSI=WYMMEI -DNAK=6ea14184 -DNK=r6pzxmR9pG8fDZpn -DB_ENV=marryeazydev -DLANG=mr

*/

// mongodb+srv://myuser11:password77@lagnsthal-1.qnb9t70.mongodb.net/test
// mongodb://127.0.0.1:27017/SmartMatri_dev
// mongod --replSet rs0 --bind_ip localhost --dbpath ~/data/mongodb - pranalee mac