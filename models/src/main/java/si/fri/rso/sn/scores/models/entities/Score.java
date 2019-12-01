package si.fri.rso.sn.scores.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "scores")
@NamedQueries(value =
{
    @NamedQuery(name = "Score.getAll", query = "SELECT s FROM scores s")
})
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private double value;

    @Column(name = "last_name")
    private String comment;

    @Column(name = "date_inserted")
    private Instant dateInserted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getDateInserted() {
        return dateInserted;
    }

    public void setDateInserted(Instant dateInserted) {
        this.dateInserted = dateInserted;
    }
}