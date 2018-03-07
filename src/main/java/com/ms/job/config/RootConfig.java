package com.ms.job.config;

import com.ms.job.PackageClazz;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan(basePackageClasses = PackageClazz.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class), @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableSwagger2.class)})
public class RootConfig {
}