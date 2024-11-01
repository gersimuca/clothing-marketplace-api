package com.gersimuca.cm.configuration;

import com.gersimuca.cm.common.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.gersimuca.cm.feature",
    repositoryBaseClass = BaseRepositoryImpl.class)
public class JpaConfig {}
