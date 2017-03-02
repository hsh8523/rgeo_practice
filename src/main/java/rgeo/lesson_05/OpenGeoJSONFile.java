package rgeo.lesson_05;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 11/6/2015.
 */
public class OpenGeoJSONFile {
    public  static String GeoJSONSample= "d:\\TRANSCEND\\hse\\rgeo\\site\\rgeo\\data\\HD_NOAA_katrina_swath_by_Wikience_org.js";

    public void open(String geojsonFileName) throws IOException {
        InputStream in = new FileInputStream(geojsonFileName);
        int decimals = 15;
        GeometryJSON gjson = new GeometryJSON(decimals);
        FeatureJSON fjson = new FeatureJSON(gjson);

        FeatureCollection<SimpleFeatureType, SimpleFeature> fc = fjson.readFeatureCollection(in);

        System.out.println("Features: ");
        FeatureIterator iterator = fc.features();
            while (iterator.hasNext()) {
                Feature feature = iterator.next();
                System.out.println(String.format("[%s] %s",
                        feature.getIdentifier(), feature.getName()));
            }
    }
    public static void main(String[] args) throws IOException {
        new OpenGeoJSONFile().open(GeoJSONSample);
    }

}
