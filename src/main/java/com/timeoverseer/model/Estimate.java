package com.timeoverseer.model;

import java.time.Duration;
import java.time.LocalDate;

/**
 * The <code>Estimate</code> class represents time to complete
 * certain {@link Task} in hours;
 */
public class Estimate {

    private LocalDate start;
    private LocalDate end;

    public Estimate(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public Long getHours() {
        return this.calculateHours();
    }

    private Long calculateHours() {
        Long hours = Duration.between(this.start, this.end).toHours();
        return hours;
    }
}
