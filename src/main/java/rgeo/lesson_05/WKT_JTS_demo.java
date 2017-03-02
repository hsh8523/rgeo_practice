package rgeo.lesson_05;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.geometry.jts.JTSFactoryFinder;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 11/19/2015.
 */
public class WKT_JTS_demo {
    public void readWKT_demo() throws ParseException {
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        WKTReader reader = new WKTReader( geometryFactory );
        LineString line = (LineString) reader.read("LINESTRING(0 2, 2 0, 8 6)");
        Polygon polygon = (Polygon) reader.read("POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))");

        System.out.println(line);
        System.out.println(polygon);
    }

    public static void main(String[] args) throws Exception {
        new WKT_JTS_demo().readWKT_demo();
    }
}
