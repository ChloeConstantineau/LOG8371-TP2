package io.swagger.api.algorithm;

import io.swagger.api.AlgorithmService;
import io.swagger.api.NotFoundException;
import io.swagger.api.factories.AlgorithmFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static io.swagger.api.Constants.SAVE_MODEL_NOTE;
import static io.swagger.api.Constants.TEXT_URILIST;

@Path("/algorithm")
//@Api(description = "Lazy algorithm API")

public class Lazy {

    private final AlgorithmService delegate;

    public Lazy(@Context ServletConfig servletContext)  {
        AlgorithmService delegate = null;

        if (servletContext != null) {
            String implClass = servletContext.getInitParameter("Algorithm.implementation");
            if (implClass != null && !"".equals(implClass.trim())) {
                try {
                    delegate = (AlgorithmService) Class.forName(implClass).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (delegate == null) {
            delegate = AlgorithmFactory.getAlgorithm();
        }
        this.delegate = delegate;
    }

    @POST
    @Path("/kNNclassification")
    @Consumes({ "multipart/form-data" })
    @Produces({ TEXT_URILIST, MediaType.APPLICATION_JSON})
    @Operation(
        summary = "REST interface to the WEKA K-nearest neighbours classifier.",
        description = "REST interface to the WEKA K-nearest neighbours classifier. " + SAVE_MODEL_NOTE,
        tags={ "algorithm", },
        extensions = {
            @Extension(properties = {@ExtensionProperty(name = "orn-@id",  value = "/algorithm/kNNclassification")}),
            @Extension(properties = {@ExtensionProperty(name = "orn-@type",  value = "x-orn:Algorithm")}),
            @Extension(name = "orn:expects", properties = {@ExtensionProperty(name = "x-orn-@id",  value = "x-orn:Dataset")}),
            @Extension(name = "orn:returns", properties = {@ExtensionProperty(name = "x-orn-@id",  value = "x-orn:Model")}),
            @Extension(name = "algorithm", properties = {
                @ExtensionProperty(name = "k-nearest neighbors algorithm", value = "https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm")
            })
        })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Resource Not Found") })
    public Response algorithmKNNclassificationPost(
        @FormDataParam("file") InputStream fileInputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail,
        @Parameter(description = "Dataset URI or local dataset ID (to the arff representation of a dataset).")@FormDataParam("datasetURI")  String datasetUri,
        @Parameter(
            description = "Gets the maximum number of instances allowed in the training pool. The addition of new instances above this value will result in old instances being removed. A value of 0 signifies no limit to the number of training instances. Must be 0 or 1 (Default: 0).",
            schema = @Schema(
                type = "integer",
                format = "int64",
                allowableValues = {"0","1"},
                defaultValue = "1"))@FormDataParam("windowSize")  Integer windowSize,
        @Parameter(
            description = "The number of neighbors to use. Must be an integer greater than 0 (Default: 1).",
            schema = @Schema(
                type = "integer",
                format = "int64",
                description = "The number of neighbors to use. Must be an integer greater than 0 (Default: 1).",
                defaultValue = "1"
            ))@FormDataParam("KNN") Integer KNN,
        @Parameter(
            description = "Whether hold-one-out cross-validation will be used to select the best k value. Must be 0 or 1 (Default: 0).",
            schema = @Schema(
                type = "integer",
                format = "int64",
                allowableValues = {"0","1"},
                defaultValue = "0"
            ))@FormDataParam("crossValidate")  Integer crossValidate,
        @Parameter(
            description = "May be 0 for no distance weighting, I for 1/distance or F for 1-distance. Must be 0, I or F (Default: 0).",
            schema = @Schema(
                type = "integer",
                format = "int64",
                allowableValues = {"0","I","F"},
                defaultValue = "0"
            ))@FormDataParam("distanceWeighting")  String distanceWeighting,
        @Parameter(
            description = "Whether the mean squared error is used rather than mean absolute error when doing cross-validation for regression problems. Must be 0 or 1 (Default: 0).",
            schema = @Schema(
                type = "integer",
                format = "int64",
                allowableValues = {"0","1"},
                defaultValue = "0"
            ))@FormDataParam("meanSquared")  Integer meanSquared,
        @Parameter(
            description = "The nearest neighbour search algorithm to use (Default: weka.core.neighboursearch.LinearNNSearch). Fixed.",
            schema = @Schema(
                allowableValues = {"LinearNNSearch"},
                defaultValue = "LinearNNSearch"
            ))@FormDataParam("nearestNeighbourSearchAlgorithm")  String nearestNeighbourSearchAlgorithm,
        @Parameter(description = "Authorization token" ) @HeaderParam("subjectid") String subjectid,
        @Context UriInfo uriInfo, @Context HttpHeaders headers, @Context SecurityContext securityContext)
        throws NotFoundException, IOException {

        HashMap<String, Object> params = new HashMap<>();
        params.put("datasetUri", datasetUri);
        params.put("windowSize", windowSize);
        params.put("KNN", KNN);
        params.put("crossValidate", crossValidate);
        params.put("distanceWeighting", distanceWeighting);
        params.put("meanSquared", meanSquared);
        params.put("nearestNeighbourSearchAlgorithm", nearestNeighbourSearchAlgorithm);

        return delegate.algorithmPost(fileInputStream, fileDetail, datasetUri, "KNN", params,
                                      headers, uriInfo, securityContext);
    }

}
