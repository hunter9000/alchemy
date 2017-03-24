package alch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.List;

public class Cell {
    public Unit unit;
    public Pipe pipe1;
    public Pipe pipe2;

    @JsonIgnore
    public boolean isPipe() {
        return pipe1 != null || pipe2 != null;
    }
    @JsonIgnore
    public boolean isUnit() {
        return unit != null;
    }

    public List<Pipe> getAllPipes() {
        return Arrays.asList(pipe1, pipe2);
    }
}
