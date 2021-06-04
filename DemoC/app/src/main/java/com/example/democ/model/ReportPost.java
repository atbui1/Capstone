package com.example.democ.model;

public class ReportPost {
    private String reportContent;
    private String postId;
    private String accountReport;

    public ReportPost(String reportContent, String postId, String accountReport) {
        this.reportContent = reportContent;
        this.postId = postId;
        this.accountReport = accountReport;
    }
}
