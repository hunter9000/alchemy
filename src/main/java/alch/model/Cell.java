package alch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

public class Cell {
    @JsonProperty
    public Unit unit;

    @JsonProperty
    public Pipe pipe1;

    @JsonProperty
    public Pipe pipe2;

    @JsonIgnore
    public boolean isPipe() {
        return pipe1 != null || pipe2 != null;
    }
    @JsonIgnore
    public boolean isUnit() {
        return unit != null;
    }

    public Collection<Pipe> getAllPipes() {
        Collection<Pipe> pipes = new ArrayList<>();
        if (pipe1 != null) {
            pipes.add(pipe1);
        }
        if (pipe2 != null) {
            pipes.add(pipe2);
        }
        return pipes;
    }

    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Pipe getPipe1() {
        return pipe1;
    }
    public void setPipe1(Pipe pipe1) {
        this.pipe1 = pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }
    public void setPipe2(Pipe pipe2) {
        this.pipe2 = pipe2;
    }
}
