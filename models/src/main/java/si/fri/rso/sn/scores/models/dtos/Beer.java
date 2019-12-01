package si.fri.rso.sn.scores.models.dtos;

import java.time.Instant;
import java.util.List;

public class Beer {

    private Integer id;
    private String user1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    private String user2;


}