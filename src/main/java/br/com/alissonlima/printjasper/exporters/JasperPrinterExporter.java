package br.com.alissonlima.printjasper.exporters;

import br.com.alissonlima.printjasper.JasperData;
import br.com.alissonlima.printjasper.utils.PrintServiceUtils;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import org.apache.commons.lang3.StringUtils;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;

/**
 * Created by alissonlima on 5/3/16.
 */
public class JasperPrinterExporter extends JasperExporter {

    public JasperPrinterExporter(JasperData jasperData) {
        super(jasperData);
    }

    public void exportToPrinter(PrinterAttributes printerAttributes) throws Exception {

        validatePrinterAttributes(printerAttributes);

        //Fill the attributes of the print request
        PrintRequestAttributeSet requestAttributes = new HashPrintRequestAttributeSet();
        requestAttributes.add(OrientationRequested.PORTRAIT);
        if (printerAttributes.copies > 0){
            requestAttributes.add(new Copies(printerAttributes.copies));
        }
        if (StringUtils.isNoneBlank(printerAttributes.jobName)){
            requestAttributes.add(new JobName(printerAttributes.jobName, null));
        }

        //Load the print service

        PrintService printService = PrintServiceUtils.getPrintService(printerAttributes.printerName);

        if(printService == null){
            throw new Exception("Impressora configurada não foi encontrada.");
        }

        //Fill the configuration of the exporter
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(requestAttributes);
        configuration.setPrintServiceAttributeSet(printService.getAttributes());

        //Create the exporter
        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(createJasperPrint()));

        //Export the report to the printer
        exporter.exportReport();
    }

    private void validatePrinterAttributes(PrinterAttributes printerAttributes) throws JasperPrinterExporterException {
        if (printerAttributes == null){
            throw new JasperPrinterExporterException("PrinterAttributes can't be null");

        } else if (StringUtils.isBlank(printerAttributes.printerName)){
            throw new JasperPrinterExporterException("PrinterName can't be null");
        }
    }

    public static class PrinterAttributes{
        public int copies;
        public String jobName;
        public String printerName;
    }

    public static class JasperPrinterExporterException extends Exception {
        public JasperPrinterExporterException(String message) {
            super(message);
        }
    }
}
