package com.ecom.common.config.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDto<T> {
    private T data;
}
