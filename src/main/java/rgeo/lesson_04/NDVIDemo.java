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
import javax.media.jai.operator.FormatDescriptor;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 10/15/2015.
 *
 * This is the example of raster algebra. We use JAI for simple band arithmetic
 * to calculate NDVI = (NIR - RED) / (NIR + RED).
 *
 * Note, that I use NIR and RED bands from Landsat 8 satellite
 * (bands 5 and 4 respectively) which I have preprocessed in advance
 * using GRASS GIS.
 *
 * Note, that since Landsat 8 bands are of type INT16, we must convert
 * at least one of them to a floating point type to avoid information loss
 * (NDVI is in range -1..+1).
 */
public class NDVIDemo {
    // Register JAI-ext operations
    static {
        JAIExt.initJAIEXT();
    }

    // preprocessed bands
    public static String NIRGTIFF   = "/home/troy/Downloads/B5.TIF";
    public static String  REDGTIFF = "/home/troy/Downloads/B4.TIF";

//    public static String NIRGTIFF = "/home/troy/Downloads/LC81390452014295LGN00_B5.TIF";
//    public static String REDGTIFF = "/home/troy/Downloads/LC81390452014295LGN00_B4.TIF";

    public static String NDVI = "/home/troy/Downloads/NDVI3.tif";

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

        ParameterBlock pbSubtracted = new ParameterBlock();
        pbSubtracted.addSource(NIR);
        pbSubtracted.addSource(RED);

        RenderedOp subtractedImage = JAI.create("subtract",pbSubtracted);

        ParameterBlock pbAdded = new ParameterBlock();
        pbAdded.addSource(NIR);
        pbAdded.addSource(RED);

        RenderedOp addedImage = JAI.create("add", pbAdded);

        // !! new data type (try commenting this out...)
        RenderedOp typeAdd = FormatDescriptor.create(addedImage, DataBuffer.TYPE_DOUBLE, null);
        RenderedOp typeSub = FormatDescriptor.create(subtractedImage, DataBuffer.TYPE_DOUBLE, null);

        ParameterBlock pbNDVI = new ParameterBlock();
        pbNDVI.addSource(typeSub);
        pbNDVI.addSource(typeAdd);


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
        new NDVIDemo().demo();
    }
}
