package si.fri.rso.sn.users.services.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.sn.scores.models.dtos.Beer;
import si.fri.rso.sn.scores.models.entities.Score;
import si.fri.rso.sn.users.services.configuration.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RequestScoped
public class ScoresBean {

    private Logger log = Logger.getLogger(ScoresBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private AppProperties appProperties;

    @Inject
    private ScoresBean scoresBean;

    private Client httpClient;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
//        baseUrlFriends = "http://localhost:8081"; // only for demonstration
    }


    public List<Score> getScores() {
        TypedQuery<Score> query = em.createNamedQuery("Score.getAll", Score.class);
        System.out.println(query.toString());
        return query.getResultList();
    }

    public List<Score> getScoresFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Score.class, queryParameters);
    }

    public Score getScore(Integer userId) {

        Score user = em.find(Score.class, userId);

        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    public Score createScore(Score score) {
        try {
            beginTx();
            em.persist(score);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        return score;
    }

    public Score putScore(String scoreId, Score score) {
        Score u = em.find(Score.class, scoreId);
        if (u == null) {
            return null;
        }
        try {
            beginTx();
            score.setId(u.getId());
            score = em.merge(score);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        return score;
    }

    public boolean deleteUser(String userId) {

        Score user = em.find(Score.class, userId);

        if (user != null) {
            try {
                beginTx();
                em.remove(user);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
