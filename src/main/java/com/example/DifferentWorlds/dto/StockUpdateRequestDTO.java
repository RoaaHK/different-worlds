package com.example.DifferentWorlds.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockUpdateRequestDTO {

    @NotNull(message = "Delta is required")
    @Min(value = 1, message = "Delta must be at least 1")
    private Integer delta;
}