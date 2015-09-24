package org.ametiste.elo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by atlantis on 9/24/15.
 */
@SpringBootApplication
@Import(EloConfiguration.class)
public class Boot {

    public static void main(String[] args) {

        SpringApplication.run(Boot.class, args);

    }


}
