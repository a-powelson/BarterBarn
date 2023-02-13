package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BugReportTest {
    private static BugReport bugReport;
    private final String user1ID = "1234";
    private final String text = "foo123 bugs";

    @Before
    public void setUp() {
        bugReport = new BugReport(user1ID, text);
    }

    @After
    public void tearDown() {
        System.gc();
    }

    @Test
    public void getReporterID() {
        assertEquals(bugReport.getReporterID(), user1ID);
    }

    @Test
    public void getReportText() {
        assertEquals(bugReport.getReportText(), text);
    }

    @Test
    public void setReporterID() {
        bugReport.setReporterID("0000");
        assertEquals(bugReport.getReporterID(), "0000");
    }

    @Test
    public void setReportText() {
        bugReport.setReportText("text changed");
        assertEquals(bugReport.getReportText(), "text changed");
    }
}
