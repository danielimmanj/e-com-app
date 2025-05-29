package com.ecom.inventory.common.config.batch;

import jakarta.annotation.Nullable;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class CustomInventorySkipPolicy implements SkipPolicy {

    private static final int MAX_SKIP_COUNT = Integer.MAX_VALUE;

    @Override
    public boolean shouldSkip(@Nullable Throwable t, long skipCount) throws SkipLimitExceededException {
        if (skipCount >= MAX_SKIP_COUNT) {
            throw new SkipLimitExceededException(MAX_SKIP_COUNT, t);
        }

        return t instanceof IllegalArgumentException;
    }
}

