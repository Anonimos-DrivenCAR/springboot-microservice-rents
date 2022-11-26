package com.anonimos.springboot.app.rents.dto;

import com.anonimos.springboot.app.rents.model.entity.RentItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RentDto {
    private List<RentItem> rentItems;
}
