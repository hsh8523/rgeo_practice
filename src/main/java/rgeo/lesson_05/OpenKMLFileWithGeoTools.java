package rgeo.lesson_05;

import org.geotools.kml.KMLConfiguration;
import org.geotools.xml.Parser;
import org.opengis.feature.simple.SimpleFeature;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 11/6/2015.
 */
public class OpenKMLFileWithGeoTools {

    public static String KML_Sample = "d:\\TRANSCEND\\hse\\rgeo\\site\\rgeo\\data\\distance.kml";

    public void open() throws IOException, ParserConfigurationException, SAXException {
        FileInputStream inputStream = new FileInputStream(new File(KML_Sample));

        Parser parser = new Parser(new KMLConfiguration());
        SimpleFeature f = (SimpleFeature) parser.parse(inputStream);

        System.out.println(f.getClass().getName());
        System.out.println(f.getAttributes());

        Collection placemarks = (Collection) f.getAttribute("Feature");

        System.out.println(placemarks.getClass().getName());
        System.out.println(placemarks);
        System.out.println(placemarks.size());

        SimpleFeature sf = (SimpleFeature)placemarks.iterator().next();
        printAttrs(sf.getAttributes(), "");
    }

    public static void printAttrs(List<Object> attrs, String tab) {
        if (attrs == null) return;
        tab += "  ";

        for (Object o : attrs) {
            if (o instanceof SimpleFeature) {
                SimpleFeature sf = (SimpleFeature)o;
                System.out.println(tab + sf.getName() + " [");
                printAttrs(sf.getAttributes(), tab);
                System.out.println(tab + " ]");
            } else if (o instanceof  List) {
                List list = (List)o;
                System.out.println(tab + " [");
                printAttrs(list, tab);
                System.out.println(tab + " ]");
            } else {
                System.out.println(tab + (o==null?"":o.getClass().getSimpleName()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new OpenKMLFileWithGeoTools().open();
    }
}
