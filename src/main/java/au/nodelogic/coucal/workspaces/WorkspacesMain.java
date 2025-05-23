/*
 *  Copyright 2025 Node Logic
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package au.nodelogic.coucal.workspaces;

import au.nodelogic.coucal.workspaces.util.Filesystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.io.File;
import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
@EnableCaching
@EnableAsync
@ConfigurationPropertiesScan
@EnableScheduling
public class WorkspacesMain {

    private final Environment env;

    public WorkspacesMain(@Autowired Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    public static void main(String[] args) {
//        if (new File(System.getProperty("user.dir"), "build/collections").mkdirs()) {
//
//        }
//        SpringApplication.run(WorkspacesMain.class, args);
        Properties props = new Properties();
        props.put("spring.datasource.url", "jdbc:sqlite:" + Filesystem.getDataDirectory() + "/Coucal/coucal.db");
        props.put("logging.file.path", Filesystem.getDataDirectory() + "/Coucal/logs");
        SpringApplication application = new SpringApplication(WorkspacesMain.class);
        application.setDefaultProperties(props);
        application.run(args);
    }
}
