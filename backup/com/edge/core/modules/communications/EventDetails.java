package com.edge.core.modules.communications;

public class EventDetails {

    private String subject;
    private String templatePath;
    private Boolean isSensitive;

    public EventDetails(String subject, String templatePath) {
        this.templatePath = templatePath;
        this.subject = subject;
        this.isSensitive = false;
    }

    public EventDetails(String subject, String templatePath, Boolean isSensitive) {
        this.templatePath = templatePath;
        this.subject = subject;
        this.isSensitive = isSensitive;
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public Boolean getSensitive() {
        return isSensitive;
    }
}
