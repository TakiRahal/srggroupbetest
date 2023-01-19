package com.takirahal.srfgroup;

import com.takirahal.srfgroup.modules.aboutus.controllers.AboutUsController;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SrfgroupApplication{
    public static void main(String[] args) {
        SpringApplication.run(SrfgroupApplication.class, args);
    }
}
