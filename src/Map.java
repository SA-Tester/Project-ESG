import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;
import gov.nasa.worldwindx.examples.PlaceNames;
import gov.nasa.worldwindx.examples.PlaceNamesPanel;

import javax.swing.JPanel;
import java.awt.Dimension;

//Inheritance
public class Map extends JPanel {
    int dim[] = new Home().getScreenDimensions();
    Map(){
        //new MapPanel(new Dimension((int) dim[0] - 670, dim[1] - 260),true);

        WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        wwd.setModel(new BasicModel());
        wwd.setPreferredSize(new Dimension((int) dim[0] - 670, dim[1] - 260));

        this.add(wwd);
    }
}
