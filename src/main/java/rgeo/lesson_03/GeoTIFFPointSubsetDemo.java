package rgeo.lesson_03;

import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.awt.image.Raster;
import java.io.File;
import java.util.Arrays;

/**
 * Created by Antonio Rodriges on 10/1/2015.
 * rodriges@wikience.org
 */
public class GeoTIFFPointSubsetDemo {
    public static String GeoTIFFSample = "/home/troy/Downloads/LC81390452014295LGN00_B1.TIF";
    //public static String GeoTIFFSample = "d:\\RS_DATA\\Landsat8\\a.tif";

    private GridCoverage2D grid;
    private Raster gridData;

    public void demo() throws Exception {
        coordTransform();
        pointDemo();
    }

    public void pointDemo() throws Exception {
        GeoTiffReader reader = new GeoTiffReader(new File(GeoTIFFSample));

        grid = reader.read(null);
        gridData = grid.getRenderedImage().getData();

        double val = getValue(55.7, 37.5);
        System.out.println(String.format("%.2f", val));
    }

    public void coordTransform() throws FactoryException, TransformException {
        // WGS84 latitude and longitude
        CoordinateReferenceSystem src = CRS.decode("EPSG:4326");
        double[] srcProjec = {55.7, 37.5}; // (lat, lon)

        // Moscow GeoTIFF, UTM37
        CoordinateReferenceSystem dest = CRS.decode("EPSG:32637");
        double[] dstProjec = {0, 0};

        MathTransform transform = CRS.findMathTransform(src, dest, true);
        transform.transform(srcProjec, 0, dstProjec, 0, 1);

        System.out.println(Arrays.toString(dstProjec));
    }

    // direct transform to matrix/raster indices
    public double getValue(double latitude, double longitude) throws Exception {
        GridGeometry2D gg = grid.getGridGeometry();

        CoordinateReferenceSystem crs = CRS.decode("EPSG:4326");

        DirectPosition2D posWorld = new DirectPosition2D(crs, latitude, longitude);
        GridCoordinates2D posGrid = gg.worldToGrid(posWorld);
        System.out.println(posGrid);

        // envelope is the size in the target projection
        double[] pixel = new double[1];
        double[] data = gridData.getPixel(posGrid.x, posGrid.y, pixel);

        posGrid = new GridCoordinates2D(0, 0);
        System.out.println("PosWorld: " + gg.gridToWorld(posGrid));

        posWorld = new DirectPosition2D(CRS.decode("EPSG:32637"), 224385, 6322515);
        System.out.println("PosGrid: " + gg.worldToGrid(posWorld));

       // posGrid = new GridCoordinates2D(2125, 4292);
        posGrid = new GridCoordinates2D(8190, 0);
        System.out.println("PosWorld: " + gg.gridToWorld(posGrid));

        return data[0];
    }

    public static void main(String[] args) throws Exception {
        new GeoTIFFPointSubsetDemo().demo();
    }
}

