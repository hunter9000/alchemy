package alch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "pipe")
@JsonIgnoreProperties(value = {"grid"})
public class Pipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "grid", nullable = false, updatable = false)
    private Grid grid;

    @Column(name = "col")
    private Integer col;

    @Column(name = "row")
    private Integer row;

    @Column(name = "in_direction")
    @Enumerated(EnumType.STRING)
    private DirectionType inDirection;

    @Column(name = "out_direction")
    @Enumerated(EnumType.STRING)
    private DirectionType outDirection;


    @Transient
    @JsonProperty(value = "isPlaced")
    public boolean isPlaced() {
        return this.col != null && this.row != null;
    }

    @Transient
    @JsonIgnore
    public DirectionType outputDirectionFromInputDirection(DirectionType incomingDirection) {
        if (inDirection.opposite() == incomingDirection) {
            return outDirection;
        }
        else if (outDirection.opposite() == incomingDirection) {
            return inDirection;
        }
        return null;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Grid getGrid() {
        return grid;
    }
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Integer getCol() {
        return col;
    }
    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }
    public void setRow(Integer row) {
        this.row = row;
    }

    public DirectionType getInDirection() {
        return inDirection;
    }
    public void setInDirection(DirectionType inDirection) {
        this.inDirection = inDirection;
    }

    public DirectionType getOutDirection() {
        return outDirection;
    }
    public void setOutDirection(DirectionType outDirection) {
        this.outDirection = outDirection;
    }
}
