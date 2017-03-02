package rgeo.lesson_04;

import org.geotools.coverage.processing.CoverageProcessor;

/**
 * Created by Antonio Rodriges, rodriges@wikience.org on 10/15/2015.
 */
public class JAIOperationOptions {
    public static void createOpCoverageProcessor() {
        // Getting CoverageProcessor
        CoverageProcessor processor = CoverageProcessor.getInstance();

        System.out.println(processor.getOperations());

        // Getting Subtract operation -- no such operation
        //  Operation subtract = processor.getOperation("Subtract");

        //  ParameterValueGroup params = subtract.getParameters();
        //  params.parameter("Source0").setValue(NIRC);
        // params.parameter("Source1").setValue(REDC);

       // Executing the operation
        // GridCoverage2D result = (GridCoverage2D) processor.doOperation(params);

    }

    public static void createOpImageWorker() {
        // No subtract operation
        //ImageWorker worker = new ImageWorker(RED);
        // worker.
        //AlgebraDescriptor.create(AlgebraDescriptor.Operator.SUBTRACT,
    }
}
