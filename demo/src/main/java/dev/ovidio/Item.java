package dev.ovidio;

import java.math.BigDecimal;

public record Item(BigDecimal price, String name, Long id) {
}
