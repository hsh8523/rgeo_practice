package rgeo.lesson_05;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.data.DataUtilities;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.kml.KML;
import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.Encoder;
import org.opengis.feature.simple.SimpleFeature;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 11/19/2015.
 */
public class WKT_2_KML {
    public void writeWKT_to_KML_demo() throws SchemaException, ParseException, IOException {
        DefaultFeatureCollection collection = new DefaultFeatureCollection(null,null);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
        WKTReader reader = new WKTReader( geometryFactory );

        SimpleFeatureBuilder featureBuilder =
                new SimpleFeatureBuilder(DataUtilities.createType("Location",
                        "location:Polygon:srid=4326," + // <- the geometry attribute: Point type
                                "name:String," + // <- a String attribute
                                "number:Integer" // a number attribute
                ));

        Polygon polygon = (Polygon) reader.read("POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))");
        featureBuilder.add(polygon);

        SimpleFeature feature = featureBuilder.buildFeature(null);
        collection.add(feature);

        Encoder encoder = new Encoder(new KMLConfiguration());
        encoder.setIndenting(true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        encoder.encode(collection, KML.kml, bos);

        System.out.println(bos.toString());
    }

    public static void main(String[] args) throws Exception {
        new WKT_2_KML().writeWKT_to_KML_demo();
    }
}
