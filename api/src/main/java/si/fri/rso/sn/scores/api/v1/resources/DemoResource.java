package si.fri.rso.sn.scores.api.v1.resources;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.discovery.enums.AccessType;
import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.sn.scores.api.v1.dtos.HealthDto;
import si.fri.rso.sn.scores.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import java.util.Optional;

@Path("demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Log
public class DemoResource {

    private Logger log = Logger.getLogger(DemoResource.class.getName());

    @Inject
    private AppProperties appProperties;

    @Inject
    @DiscoverService(value="sn-scores", accessType=AccessType.DIRECT)
    private Optional<String> baseUrlScores;

    @POST
    @Path("healthy")
    public Response setHealth(HealthDto health) {
        appProperties.setHealthy(health.getHealthy());
        log.info("Setting health to " + health.getHealthy());
        return Response.ok().build();
    }


    @GET
    @Path("info")
    public Response info() {
        JsonObject json = Json.createObjectBuilder()
                .add("clani", Json.createArrayBuilder().add("dv9763"))
                .add("opis_projekta", "Nas projekt implementira aplikacijo za ocenjevanje piv.")
                .add("mikrostoritve", Json.createArrayBuilder().add("http://192.168.1.61" + "/v1/scores/"))
                .add("github", Json.createArrayBuilder().add("https://github.com/denisvitez/rso-scores"))
                .add("travis", Json.createArrayBuilder().add("https://travis-ci.org/denisvitez/rso-scores"))
                .add("dockerhub", Json.createArrayBuilder().add("https://hub.docker.com/repository/docker/denisvitez/rso-scores"))
                .build();

        return Response.ok(json.toString()).build();
    }
}
