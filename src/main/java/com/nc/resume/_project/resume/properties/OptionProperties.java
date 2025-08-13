package com.nc.resume._project.resume.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "options")
public class OptionProperties {

    private List<String> role;
    private List<String> checkbox;

    public List<String> getRole() {
        return role;
    }
    public void setRole(List<String> role) {
        this.role = role;
    }

    public List<String> getCheckbox() {
        return checkbox;
    }
    public void setCheckbox(List<String> checkbox) {
        this.checkbox = checkbox;
    }
}