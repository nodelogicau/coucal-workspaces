package au.nodelogic.coucal.workspaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;

@SpringBootApplication
@EnableCaching
@EnableAsync
@ConfigurationPropertiesScan
public class WorkspacesMain {

    public static void main(String[] args) {
        if (new File(System.getProperty("user.dir"), "build/collections").mkdirs()) {
            
        }
        SpringApplication.run(WorkspacesMain.class, args);
    }
}
