package com.api.sistemachamados.service.impl;

import com.api.sistemachamados.service.ReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {


    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        //Realiza busca com parametros necessários que o jasper precisa

        File file = ResourceUtils.getFile("classpath:relatorioChamadoFanCoil.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource();
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport);
        return null;
    }
}
