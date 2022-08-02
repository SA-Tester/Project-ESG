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

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

//This is the template for Map Display
//Source: NASA WorldWind ApplicationTemplate.java
public class MapTemplate {
    public static WorldWindow wwdPublic;
    ArrayList <String> placeMarkData = new ArrayList<>();
    ArrayList<PointPlacemark> placeMarkPoints = new ArrayList<>();
    static final RenderableLayer placeMarkLayer = new RenderableLayer();

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
            addPlaceMarkLayer();
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

    protected static void addPlaceMarkLayer(){
        placeMarkLayer.setName("placeMarkLayer");
        insertBeforeCompass(wwdPublic,placeMarkLayer);
    }

    public void addPlaceMark(float lat, float lon, String name){
        placeMarkLayer.setName("placeMarkLayer");
        PointPlacemark p = new PointPlacemark(Position.fromDegrees(lat,lon,1000));
        p.setLabelText(name);
        p.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        p.setEnableLabelPicking(true);
        PointPlacemarkAttributes attributes = new PointPlacemarkAttributes();
        attributes.setImageAddress("images/locationPin.png");
        p.setAttributes(attributes);
        placeMarkLayer.addRenderable(p);
        placeMarkPoints.add(p);

        getPlaceMarks(p);
        writeToFile();
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

   private void getPlaceMarks(PointPlacemark p) {
       double latDeg = p.getPosition().latitude.degrees;
       double lonDeg = p.getPosition().longitude.degrees;

       String dataLine = p.getLabelText() + "," + latDeg + "," + lonDeg + '\n';
       placeMarkData.add(dataLine);
   }

   public void removePlaceMark(float lat, float lon){
       Position pos = new Position(Position.fromDegrees(lat, lon,1000));

       for(int i=0; i<placeMarkPoints.size(); i++){
           if(placeMarkPoints.get(i).getPosition().equals(pos)){
               placeMarkLayer.removeRenderable(placeMarkPoints.get(i));
               placeMarkData.remove(i);
           }
       }
       writeToFile();
   }

    private void writeToFile(){
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

    public void goTo(double lat, double lon){
       Position pos = new Position(Position.fromDegrees(lat,lon));
        wwdPublic.getView().goTo(pos,1000000);
    }
}
