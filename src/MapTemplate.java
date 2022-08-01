import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.util.StatusBar;
import gov.nasa.worldwind.Model;
import gov.nasa.worldwindx.examples.util.HighlightController;
import gov.nasa.worldwindx.examples.util.ToolTipController;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

//This is the template for Map Display
//Source: NASA WorldWind ApplicationTemplate.java
public class MapTemplate {
    public static WorldWindow wwdPublic;
    ArrayList <String> placeMarkData = new ArrayList<>();

   public static class MapPanel extends JPanel{
        protected WorldWindow wwd;
        protected StatusBar statusBar;
        protected ToolTipController toolTipController;
        protected HighlightController highlightController;

        public MapPanel(Dimension canvasSize, boolean includeStatusBar){
            //Keyword super set the parent class attributes as defined. Here Parent class is JPanel
            //Super set the JPanel Layout a BorderLayout
            super(new BorderLayout());

            this.wwd = this.createWorldWindow();
            ((Component) this.wwd).setPreferredSize(canvasSize);

            wwdPublic = this.wwd;

            //Crete Default model
            Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
            this.wwd.setModel(m);

            this.add((Component) this.wwd, BorderLayout.CENTER);
            if(includeStatusBar){
                this.statusBar = new StatusBar();
                this.add(statusBar,BorderLayout.SOUTH);
                this.statusBar.setEventSource(wwd);
            }

            //Enable interactions with Place marks, their labels etc.
            this.toolTipController = new ToolTipController(this.getWWD(),AVKey.DISPLAY_NAME, null);
            this.highlightController = new HighlightController(this.getWWD(), SelectEvent.ROLLOVER);
            addViewControlsLayer();
        }

        protected WorldWindow createWorldWindow(){
            return new WorldWindowGLCanvas();
        }
        protected WorldWindow getWWD(){
            return wwd;
        }
    }

    protected static void addViewControlsLayer(){
        ViewControlsLayer viewControlsLayer = new ViewControlsLayer();
        insertBeforeCompass(wwdPublic, viewControlsLayer);
        wwdPublic.addSelectListener(new ViewControlsSelectListener(wwdPublic, viewControlsLayer));
    }
    public void addPlaceMark(float lat, float lon, String name){
        final RenderableLayer placeMarkLayer = new RenderableLayer();

        PointPlacemark p = new PointPlacemark(Position.fromDegrees(lat,lon,1000));
        p.setLabelText(name);
        p.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        p.setEnableLabelPicking(true);
        PointPlacemarkAttributes attributes = new PointPlacemarkAttributes();
        attributes.setImageAddress("images/locationPin.png");
        p.setAttributes(attributes);
        placeMarkLayer.addRenderable(p);

        insertBeforeCompass(wwdPublic,placeMarkLayer);
        new ViewPlaceMarkDetails().getPlaceMarks(p);
        writeToFile();
    }

    abstract static class PlaceMarkDetails{
       abstract void getPlaceMarks(PointPlacemark p);
    }

    public class ViewPlaceMarkDetails extends PlaceMarkDetails{
       public void getPlaceMarks(PointPlacemark p){
           String dataLine = p.getLabelText() + "," + p.getPosition().latitude.toString() + "," + p.getPosition().longitude.toString()+'\n';
           placeMarkData.add(dataLine);
       }
    }

    public void writeToFile(){
        try{
            FileWriter placeMarkFileW = new FileWriter("data/PlaceMarkDetails.csv");
            for(String d: placeMarkData){
                placeMarkFileW.write(d);
            }
            placeMarkFileW.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void insertBeforeCompass(WorldWindow wwd, Layer layer){
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for(Layer l: layers){
            if(l instanceof CompassLayer){
                compassPosition = layers.indexOf(l);
            }
        }
        layers.add(compassPosition,layer);
    }
}
