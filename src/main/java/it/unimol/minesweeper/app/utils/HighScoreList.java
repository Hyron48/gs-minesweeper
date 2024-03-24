package it.unimol.minesweeper.app.utils;

import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class HighScoreList implements Serializable {
    private String name;
    private int maxEntries;
    private String measurementUnit;
    private boolean lowestIsBest;
    private List<Entry> entries;

    public HighScoreList(String name, int maxEntries, String measurementUnit, boolean lowestIsBest) {
        this.name = name;
        this.maxEntries = maxEntries;
        this.measurementUnit = measurementUnit;
        this.lowestIsBest = lowestIsBest;
        entries = new ArrayList<>();
    }

    public HighScoreList(String name, int maxEntries, String measurementUnit) {
        this(name, maxEntries, measurementUnit, false);
    }

    public boolean checkIfWorthy(double value) {
        if (entries.size() < maxEntries) {
            return true;
        }

        if (!lowestIsBest) {
            if (value > entries.get(entries.size() - 1).getScore()) {
                return true;
            }
        } else {
            if (value < entries.get(entries.size() - 1).getScore()) {
                return true;
            }
        }

        return false;
    }

    public boolean add(String name, double value) {
        if (entries.size() < maxEntries) {
            entries.add(new Entry(name, value));
            if (!lowestIsBest) {
                entries.sort((e1, e2) -> Double.compare(e2.getScore(), e1.getScore()));
            } else {
                entries.sort((e1, e2) -> Double.compare(e1.getScore(), e2.getScore()));
            }
            return true;
        }

        if (!lowestIsBest) {
            if (value > entries.get(entries.size() - 1).getScore()) {
                entries.add(new Entry(name, value));
                entries.sort((e1, e2) -> Double.compare(e2.getScore(), e1.getScore()));
                entries.remove(entries.size() - 1);
                return true;
            }
        } else {
            if (value < entries.get(entries.size() - 1).getScore()) {
                entries.add(new Entry(name, value));
                entries.sort((e1, e2) -> Double.compare(e1.getScore(), e2.getScore()));
                entries.remove(entries.size() - 1);
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "\n\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
        int i = 1;
        for (Entry e : entries) {
            sb.append(i).append(": ").append(e.getName()).append(" - ")
                    .append(e.getScore()).append(" ").append(measurementUnit).append(" - ")
                    .append(e.getCreationDate().format(formatter)).append("\n");
            i++;
        }

        if (entries.isEmpty()) {
            sb.append("No entries.\n");
        }

        return sb.toString();
    }
}