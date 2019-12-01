package si.fri.rso.sn.scores.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.sn.scores.models.entities.Score;
import si.fri.rso.sn.scores.models.dtos.Beer;
import si.fri.rso.sn.users.services.beans.ScoresBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Log
@ApplicationScoped
@Path("/scores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScoresResource {

    @Inject
    private ScoresBean scoresBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getScores() {

        List<Score> scores = scoresBean.getScores();

        return Response.ok(scores).build();
    }

    @GET
    @Path("/filtered")
    public Response getScoresFiltered() {
        List<Score> scores;
        scores = scoresBean.getScoresFilter(uriInfo);
        return Response.status(Response.Status.OK).entity(scores).build();
    }

    @GET
    @Path("/{userId}")
    public Response getScore(@PathParam("scoreId") Integer scoreId) {
        Score score = scoresBean.getScore(scoreId);
        if (score == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(score).build();
    }

    @POST
    public Response createScore(Score score) {

        if ((score.getValue() < 0 || score.getValue() > 5.0) ||
                (score.getComment() == null || score.getComment().isEmpty())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            score = scoresBean.createScore(score);
        }

        if (score.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(score).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(score).build();
        }
    }

    @PUT
    @Path("{scoreId}")
    public Response putScore(@PathParam("scoreId") String scoreId, Score score) {
        score = scoresBean.putScore(scoreId, score);
        if (score == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (score.getId() != null)
                return Response.status(Response.Status.OK).entity(score).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{scoreId}")
    public Response deleteScore(@PathParam("scoreId") String scoreId) {

        boolean deleted = scoresBean.deleteUser(scoreId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
