package com.github.fedeoasi.music;

import java.util.Objects;

public class ScoredScale {
    private Scale scale;
    private int rank;

    public ScoredScale(Scale scale, int rank) {
        this.scale = scale;
        this.rank = rank;
    }

    public Scale getScale() {
        return scale;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "ScoredScale(" + scale + ", " + rank + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoredScale that = (ScoredScale) o;
        return rank == that.rank &&
            Objects.equals(scale, that.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scale, rank);
    }
}
