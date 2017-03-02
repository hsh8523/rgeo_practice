package rgeo.lesson_05;

import de.micromata.opengis.kml.v_2_2_0.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 11/19/2015.
 *
 * Modified from http://stackoverflow.com/questions/15636303/extract-coordinates-from-kml-file-in-java
 */
public class OpenKMLWithMicromata {
    public static String KML_Sample = "d:\\TRANSCEND\\hse\\rgeo\\site\\rgeo\\data\\distance.kml";

    public void open() {
        Kml kml = Kml.unmarshal(new File(KML_Sample));
        parseFeature(kml.getFeature());
    }

    private void parseFeature(Feature feature) {
        if(feature != null) {
            try {
                // this code covers both "Document" and "Folder" cases
                Method m = feature.getClass().getMethod("getFeature");
                List<Feature> featureList = (List<Feature>)m.invoke(feature, null);
                parseFeatures(featureList);
            } catch (NoSuchMethodException e) {
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }

    private void parseFeatures(List<Feature> featureList) {
        if (null == featureList ) return;

        for(Feature documentFeature : featureList) {
            System.out.println(documentFeature.getClass().getName());
            if(documentFeature instanceof Placemark) {
                Placemark placemark = (Placemark) documentFeature;
                Geometry geometry = placemark.getGeometry();
                parseGeometry(geometry);
            } else parseFeature(documentFeature);
        }
    }

    private void parseGeometry(Geometry geometry) {
        if(geometry != null) {
            if(geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if(outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if(linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if(coordinates != null) {
                            for(Coordinate coordinate : coordinates) {
                                parseCoordinate(coordinate);
                            }
                        }
                    }
                }
            }
        }
    }

    private void parseCoordinate(Coordinate coordinate) {
        if(coordinate != null) {
            System.out.println("Longitude: " +  coordinate.getLongitude());
            System.out.println("Latitude : " +  coordinate.getLatitude());
            System.out.println("Altitude : " +  coordinate.getAltitude());
            System.out.println("");
        }
    }
    public static void main(String[] args) throws Exception {
        new OpenKMLWithMicromata().open();
    }
}
