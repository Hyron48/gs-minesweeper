package it.unimol.minesweeper.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.Duration;

public class StopWatch {
    private Instant startTime;
    private Instant stopTime;

    public StopWatch() {
        reset();
    }

    public void start() {
        if (startTime == null) {
            startTime = Instant.now();
        }
    }

    public void stop() {
        if (stopTime == null && startTime != null) {
            stopTime = Instant.now();
        }
    }

    public void reset() {
        startTime = null;
        stopTime = null;
    }

    public long getMillis() {
        if (startTime != null && stopTime != null) {
            return Duration.between(startTime, stopTime).toMillis();
        }

        return 0;
    }

    public double getSeconds() {
        if (startTime != null && stopTime != null) {
            double seconds = getMillis() / 1000.0;
            BigDecimal number = new BigDecimal(seconds);
            BigDecimal rounded = number.setScale(2, RoundingMode.HALF_EVEN);
            return rounded.doubleValue();
        }

        return 0;
    }
}