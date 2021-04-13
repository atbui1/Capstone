package com.example.democ.model;

public class ReportPost {
    private String reportContent;
    private String shareDetailId;
    private String accountReport;

    public ReportPost(String reportContent, String shareDetailId, String accountReport) {
        this.reportContent = reportContent;
        this.shareDetailId = shareDetailId;
        this.accountReport = accountReport;
    }
}
