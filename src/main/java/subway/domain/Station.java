package subway.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
//    @JoinColumn(name = "line_id")
    private Line line;

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void setLine(Line line) {
        if (Objects.nonNull(this.line)) {
            this.line.getStations().remove(this);
        }
        this.line = line;
        if (Objects.nonNull(line)) {
            line.getStations().add(this);
        }
    }
}
