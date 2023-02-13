package com.example.barterbarn;

import java.util.Calendar;
import java.util.Date;

public class BugReport {
    private String reporterID;
    private String reportText;
    private Date dateReported;

    public BugReport() {
    }

    public BugReport(String reporterID, String reportText) {
        this.reporterID = reporterID;
        this.reportText = reportText;
        this.dateReported = Calendar.getInstance().getTime();
    }

    public String getReporterID() {
        return reporterID;
    }

    public void setReporterID(String reporterID) {
        this.reporterID = reporterID;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }
}
