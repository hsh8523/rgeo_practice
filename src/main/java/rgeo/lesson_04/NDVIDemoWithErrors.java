package rgeo.lesson_04;

import it.geosolutions.jaiext.JAIExt;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.coverage.Coverage;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.coverage.grid.GridCoverageWriter;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 10/15/2015.
 *
 * This file knowingly contains mistakes for students to correct them.
 */
public class NDVIDemoWithErrors {
    // Register JAI-ext operations
    static {
        JAIExt.initJAIEXT();
    }
    public static String NIRGTIFF = "d:\\RS_DATA\\Landsat8\\LC81790212015146-SC20150806075046\\LC81790212015146LGN00_sr_band4.tif";
    public static String REDGTIFF = "d:\\RS_DATA\\Landsat8\\LC81790212015146-SC20150806075046\\LC81790212015146LGN00_sr_band5.tif";
    public static String NDVI = "d:\\RS_DATA\\Landsat8\\LC81790212015146-SC20150806075046\\NDVI.tif";

    private RenderedImage NIR;
    private RenderedImage RED;

    private GridCoverage2D NIRC;
    private GridCoverage2D REDC;

    private GeoTiffReader reader;

    private void read() throws IOException {
        reader = new GeoTiffReader(new File(NIRGTIFF));
        NIRC = reader.read(null);
        reader = new GeoTiffReader(new File(REDGTIFF));
        REDC = reader.read(null);

        NIR = NIRC.getRenderedImage();
        RED = REDC.getRenderedImage();
    }

    public void demo() throws IOException {
        read();

        ParameterBlock pb1 = new ParameterBlock();
        pb1.addSource(NIR);
        pb1.addSource(RED);

        RenderedOp subtractedImage = JAI.create("add",pb1);

        ParameterBlock pb2 = new ParameterBlock();
        pb2.addSource(NIR);
        pb2.addSource(RED);

        RenderedOp addedImage = JAI.create("subtract", pb2);

        ParameterBlock pbNDVI = new ParameterBlock();
        pbNDVI.addSource(pb1);
        pbNDVI.addSource(pb2);


        RenderedOp NDVIop = JAI.create("divide", pbNDVI);


        GridCoverageFactory gridCoverageFactory = new GridCoverageFactory();
        ReferencedEnvelope referencedEnvelope = new ReferencedEnvelope(REDC.getEnvelope());
        Coverage coverage = gridCoverageFactory.create("Raster", NDVIop, REDC.getEnvelope());

        // write the result
        GridCoverageWriter writer = ((GeoTiffFormat) reader.getFormat()).getWriter(new File(NDVI));
        writer.write((GridCoverage) coverage, null);
        writer.dispose();
    }

    public static void main(String [] args) throws IOException {
        new NDVIDemoWithErrors().demo();
    }
}
