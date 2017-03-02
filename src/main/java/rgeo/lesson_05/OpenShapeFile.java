package rgeo.lesson_05;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 10/13/2015.
 */
public class OpenShapeFile {
    public static String ShapeFileSample_01 = "d:\\RS_DATA\\Moscow\\mo-shape\\mo.shp";
    public static String ShapeFileSample_02 = "d:\\RS_DATA\\Moscow\\RU-MOW\\data\\building-polygon.shp";

    public static FeatureSource<SimpleFeatureType, SimpleFeature> openShapeFile(String path) throws IOException {
        File file = new File(path);
        Map<String, Object> map = new TreeMap<String, Object>();

        try {
            map.put("url", file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DataStore dataStore = DataStoreFinder.getDataStore(map);

        System.out.println("Shapefile type names: " + Arrays.toString(dataStore.getTypeNames()));

        String typeName = dataStore.getTypeNames()[0];
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);

        System.out.println("Features: ");
        FeatureIterator iterator = source.getFeatures().features();
            while (iterator.hasNext()) {
                Feature feature = iterator.next();
                System.out.println(String.format("[%s] %s",
                        feature.getIdentifier(), feature.getName()));
            }

        return source;
    }

    public static void main(String[] args) throws IOException {
        new OpenShapeFile().openShapeFile(ShapeFileSample_01);
        //new OpenShapeFile().openShapeFile(ShapeFileSample_02);
    }
}
