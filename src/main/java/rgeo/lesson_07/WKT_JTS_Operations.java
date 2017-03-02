package rgeo.lesson_07;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.util.AffineTransformation;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.geometry.jts.JTSFactoryFinder;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 12/16/2015.
 */
public class WKT_JTS_Operations {
    public void JTS_OP_demo() throws ParseException {
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        WKTReader reader = new WKTReader( geometryFactory );

        Polygon A = (Polygon) reader.read("POLYGON ((10 40, 60 40, 60 10, 10 10, 10 40), (20 35, 50 35, 50 20, 20 20, 20 35),(30 30, 40 30, 40 15, 30 15, 30 30))");
        Polygon B = (Polygon) reader.read("POLYGON ((43.4 18.5, 57.5 18.5, 57.5 11.9, 43.4 11.9, 43.4 18.5))");
        System.out.println(A.relate(B));

        Polygon polygon = (Polygon) reader.read("POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10), " +
                "(20 30, 35 35, 30 20, 20 30))");
        System.out.println(polygon);

        // Try this: exception should be thrown:
        //Polygon polygonA = (Polygon) reader.read("POLYGON (35 10, 45 45, 15 40, 10 20, 35 10)");

        Polygon polygonA = (Polygon) reader.read("POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10))");
        Polygon polygonB = (Polygon) reader.read("POLYGON ((20 30, 35 35, 30 20, 20 30))");
        System.out.println(polygonA);
        System.out.println(polygonB);

        Geometry result = polygonA.difference(polygonB);
        System.out.println(result);
        // compare result with above "polygon"

        // Egenhofer
        polygonA.covers(polygonB);
        polygonA.touches(polygonB);
        polygonA.crosses(polygonB);
        System.out.println(polygonA.relate(polygonB));

        // AFFINE TRANSFORMATIONS, view results at JTS TestBuilder

        result.apply(AffineTransformation.rotationInstance(30));
        System.out.println(result);

        // or several transformations at once
        AffineTransformation transformation = AffineTransformation.scaleInstance(2, 2).compose(AffineTransformation.rotationInstance(45)).compose(AffineTransformation.translationInstance(10, 10));
        result.apply(transformation);
        System.out.println(result);

        // BUFFER
        Geometry buffer = polygonA.buffer(10);
        System.out.println(buffer);
    }

    public static void main(String[] args) throws Exception {
        new WKT_JTS_Operations().JTS_OP_demo();
    }
}
