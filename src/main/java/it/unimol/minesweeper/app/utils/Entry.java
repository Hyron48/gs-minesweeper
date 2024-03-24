package it.unimol.minesweeper.app.utils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Entry implements Serializable {
    private String name;
    private double score;
    private LocalDateTime creationDate;

    public Entry(String name, double score) {
        this.name = name;
        this.score = score;
        this.creationDate = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}