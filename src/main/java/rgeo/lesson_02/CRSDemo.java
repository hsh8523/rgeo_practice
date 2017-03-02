package rgeo.lesson_02;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import java.io.File;
import java.io.IOException;

/** Created by Antonio Rodriges on 9/24/2015.
 *  rodriges@wikience.org
 *  Demo of CRS and EPSG
 */

public class CRSDemo {
    public static String GeoTIFFSample = "d:\\RS_DATA\\Landsat8\\LC81790212015146-SC20150806075046\\LC81790212015146LGN00_sr_band1.tif";

    public static void demo1() throws FactoryException {
        CoordinateReferenceSystem crs = CRS.decode("EPSG:32637");
        String wkt = crs.toWKT();
        System.out.println(wkt);

        crs = CRS.decode("EPSG:2833");
        wkt = crs.toWKT();
        System.out.println(wkt);
    }

    public static void demo() throws IOException {
        File file = new File(GeoTIFFSample);

        AbstractGridFormat format = GridFormatFinder.findFormat(file);
        GridCoverage2DReader reader = format.getReader(file);
        GridCoverage2D coverage = (GridCoverage2D) reader.read(null);
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();

        String wkt = crs.toWKT();
        System.out.println(wkt);
    }

    public static void main( String[] args ) throws Exception {
        // They will produce identical results
        CRSDemo.demo1();
        //CRSDemo.demo();
    }
}
