package com.api.sistemachamados.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {


    String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}
