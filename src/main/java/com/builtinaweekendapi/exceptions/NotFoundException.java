package com.builtinaweekendapi.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

public class NotFoundException extends EntityNotFoundException {

    public NotFoundException(
            @NotNull String entityType,
            @NotNull Long entityId
    ) {
        super("Entity not found: " + entityType + " with id " + entityId);
    }
}
