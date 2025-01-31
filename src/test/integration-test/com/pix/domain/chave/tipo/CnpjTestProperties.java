package com.pix.domain.chave.tipo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cnpj.test")
public class CnpjTestProperties {

    private String valid1;
    private String valid2;
    private String valid3;
    private String valid4;
    private String valid5;
    private String valid6;

    public String getValid1() {
        return valid1;
    }

    public String getValid2() {
        return valid2;
    }

    public String getValid3() {
        return valid3;
    }

    public String getValid4() {
        return valid4;
    }

    public String getValid5() {
        return valid5;
    }

    public String getValid6() {
        return valid6;
    }

    public void setValid1(String valid1) {
        this.valid1 = valid1;
    }

    public void setValid2(String valid2) {
        this.valid2 = valid2;
    }

    public void setValid3(String valid3) {
        this.valid3 = valid3;
    }

    public void setValid4(String valid4) {
        this.valid4 = valid4;
    }

    public void setValid5(String valid5) {
        this.valid5 = valid5;
    }

    public void setValid6(String valid6) {
        this.valid6 = valid6;
    }
}
