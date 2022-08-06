import gov.nasa.worldwind.View;
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
    public static WorldWindow wwdPublic;
    static private ArrayList <String> placeMarkData = new ArrayList<>();
    static private ArrayList<PointPlacemark> placeMarkPoints = new ArrayList<>();
    static final RenderableLayer placeMarkLayer = new RenderableLayer();
    private String username = Files.LoginInfo.getCurrentLogin();

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

    public void addPlaceMark(double lat, double lon, String name, boolean addToFile){
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

        if(addToFile){
            getPlaceMarks(p);
            new PlaceMarkDetails().writeToPlaceMarkFile(placeMarkData);
        }
        else {
            getPlaceMarks(p);
        }
        //System.out.println(placeMarkData);
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
        wwdPublic.getView().goTo(pos,200000);
    }

   private void getPlaceMarks(PointPlacemark p) {
       double latDeg = p.getPosition().latitude.degrees;
       double lonDeg = p.getPosition().longitude.degrees;

       String dataLine = String.format("%s,%s,%.2f,%.2f\n",username,p.getLabelText(),latDeg,lonDeg);
       placeMarkData.add(dataLine);
   }

   public void removePlaceMark(double lat, double lon){
       Position pos = new Position(Position.fromDegrees(lat, lon,1000));

       try {
           //System.out.println(placeMarkPoints.size());
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
       new PlaceMarkDetails().writeToPlaceMarkFile(placeMarkData);
       //System.out.println(placeMarkData);
   }

   private class PlaceMarkDetails extends Files.PlaceMarkDetails{
       void writeToPlaceMarkFile(ArrayList <String> placeMarkList){
           Files.PlaceMarkDetails.writeToPlaceMarkDetails(placeMarkList);
       }
   }
}
