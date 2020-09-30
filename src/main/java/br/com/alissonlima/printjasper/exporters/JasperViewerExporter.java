package br.com.alissonlima.printjasper.exporters;

import br.com.alissonlima.printjasper.JasperData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Created by alissonlima on 3/15/16.
 */
public class JasperViewerExporter extends JasperExporter{

    public JasperViewerExporter(JasperData jasperData) {
        super(jasperData);
    }

    public void displayReport() throws JRException {
        displayReport(null);
    }

    public void displayReport(String viewerTitle) throws JRException {
        JasperViewer viewer = new JasperViewer(createJasperPrint(), false);
        viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

        if (viewerTitle != null){
            viewer.setTitle(viewerTitle);
        }

        //Display the viewer
        viewer.setVisible(true);
    }
}
