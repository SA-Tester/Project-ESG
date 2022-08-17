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

//This is the template for Map Display
//Source: NASA WorldWind ApplicationTemplate.java
public class MapTemplate {
    //public WorldWindow wwdPublic;
    static private final ArrayList <String> placeMarkData = new ArrayList<>();
    static private final ArrayList<PointPlacemark> placeMarkPoints = new ArrayList<>();
    static final RenderableLayer placeMarkLayer = new RenderableLayer();

   public static class MapPanel extends JPanel{
        private static WorldWindow wwd;
        protected ToolTipController toolTipController;
        protected HighlightController highlightController;

        public MapPanel(Dimension canvasSize, boolean includeStatusBar){
            //Keyword super set the parent class attributes as defined. Here Parent class is JPanel
            //Super set the JPanel Layout a BorderLayout
            super(new BorderLayout());

            wwd = this.createWorldWindow();
            ((Component) wwd).setPreferredSize(canvasSize);

            //Crete Default model
            Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
            wwd.setModel(m);

            this.add((Component) wwd, BorderLayout.CENTER);
            if(includeStatusBar){
                StatusBar statusBar = new StatusBar();
                this.add(statusBar,BorderLayout.SOUTH);
                statusBar.setEventSource(wwd);
            }

            //Enable interactions with Place marks, their labels etc.
            this.toolTipController = new ToolTipController(getWWD(),AVKey.DISPLAY_NAME, null);
            this.highlightController = new HighlightController(getWWD(), SelectEvent.ROLLOVER);
            addViewControlsLayer();
            addPlaceMarkLayer();
        }

        protected WorldWindow createWorldWindow(){
            return new WorldWindowGLCanvas();
        }
        protected static WorldWindow getWWD(){
            return wwd;
        }
    }

    protected static void addViewControlsLayer(){
        ViewControlsLayer viewControlsLayer = new ViewControlsLayer();
        insertBeforeCompass(MapPanel.getWWD(), viewControlsLayer);
        MapPanel.getWWD().addSelectListener(new ViewControlsSelectListener(MapPanel.getWWD(), viewControlsLayer));
    }

    protected static void addPlaceMarkLayer(){
        placeMarkLayer.setName("placeMarkLayer");
        insertBeforeCompass(MapPanel.getWWD(),placeMarkLayer);
    }

    public void addPlaceMark(double lat, double lon, String name, String requirement, boolean addToFile){
        placeMarkLayer.setName("placeMarkLayer");
        PointPlacemark p = new PointPlacemark(Position.fromDegrees(lat,lon,1000));
        p.setLabelText(name);
        p.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        p.setEnableLabelPicking(true);
        PointPlacemarkAttributes attributes = new PointPlacemarkAttributes();

        switch (requirement) {
            case "HAVE" -> attributes.setImageAddress("images/locationPin_GREEN.png");
            case "NEED" -> attributes.setImageAddress("images/locationPin_RED.png");
            default -> attributes.setImageAddress("images/locationPin_BLACK.png");
        }

        p.setAttributes(attributes);
        placeMarkLayer.addRenderable(p);
        placeMarkPoints.add(p);

        if(addToFile){
            getPlaceMarks(p);
            new PlaceMarkDetails().writeToPlaceMarkFile();
        }
        else {
            getPlaceMarks(p);
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

    public void goTo(double lat, double lon){
        Position pos = new Position(Position.fromDegrees(lat,lon));
        MapPanel.getWWD().getView().goTo(pos,200000);
    }

   private void getPlaceMarks(PointPlacemark p) {
       double latDeg = p.getPosition().latitude.degrees;
       double lonDeg = p.getPosition().longitude.degrees;

       String dataLine = String.format("%s,%.2f,%.2f\n",p.getLabelText(),latDeg,lonDeg);
       placeMarkData.add(dataLine);
   }

   public void removePlaceMark(double lat, double lon){
       Position pos = new Position(Position.fromDegrees(lat, lon,1000));

       try {
           for (int i = 0; i < placeMarkPoints.size(); i++) {
               if (placeMarkPoints.get(i).getPosition().equals(pos)) {
                   placeMarkLayer.removeRenderable(placeMarkPoints.get(i));
                   placeMarkData.remove(i);
                   placeMarkPoints.remove(placeMarkPoints.get(i));
               }
           }
       }catch (IndexOutOfBoundsException e){
           e.printStackTrace();
       }
       new PlaceMarkDetails().writeToPlaceMarkFile();
   }

   private static class PlaceMarkDetails extends Files.PlaceMarkDetails{
       void writeToPlaceMarkFile(){
           Files.PlaceMarkDetails.writeToPlaceMarkDetails(MapTemplate.placeMarkData);
       }
   }
}
