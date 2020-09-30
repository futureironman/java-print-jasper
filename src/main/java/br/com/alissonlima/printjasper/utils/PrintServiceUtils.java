package br.com.alissonlima.printjasper.utils;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 *
 * @author AlissonLima
 */
public class PrintServiceUtils {

    /**
     * Return the PrinterService that is installed with the printer name passed
     * by param. If it be unable to find the printer, then will return null.
     *
     * @param printerName
     * @return
     */
    public static PrintService getPrintService(String printerName) {
        PrintService printers[] = PrintServiceLookup.lookupPrintServices(null, null);

        for (PrintService printService : printers) {
            if (printService.getName().equals(printerName)) {
                return printService;
            }
        }

        return null;
    }

    public static void printText(String contentToPrint, PrintService printService) throws PrintException {
        InputStream stream = new ByteArrayInputStream(contentToPrint.getBytes());
        printStream(stream, printService);
    }

    public static void printFile(Path fileToPrint, PrintService printService) throws FileNotFoundException, PrintException {
        InputStream stream = new FileInputStream(fileToPrint.toString());
        printStream(stream, printService);
    }

    public static void printFile(Path fileToPrint, String printerName) throws FileNotFoundException, PrintException {
        printFile(fileToPrint, getPrintService(printerName));
    }

    private static void printStream(InputStream stream, PrintService printService) throws PrintException {
        DocPrintJob dpj = printService.createPrintJob();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc doc = new SimpleDoc(stream, flavor, null);
        dpj.print(doc, null);
    }
}
