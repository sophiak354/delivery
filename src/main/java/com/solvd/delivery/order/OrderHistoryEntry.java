package com.solvd.delivery.order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record OrderHistoryEntry(
        LocalDateTime at,
        String message
) {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String toString() {
        return FORMAT.format(at) + " - " + message;
    }
}
