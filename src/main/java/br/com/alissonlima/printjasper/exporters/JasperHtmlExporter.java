package br.com.alissonlima.printjasper.exporters;

import br.com.alissonlima.printjasper.JasperData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;

/**
 * Created by alissonlima on 3/15/16.
 */
public class JasperHtmlExporter extends JasperExporter{

    public JasperHtmlExporter(JasperData jasperData) {
        super(jasperData);
    }

    public void exportToFile(String filePath) throws JRException {
        JasperExportManager.exportReportToHtmlFile(createJasperPrint(), filePath);
    }

}
