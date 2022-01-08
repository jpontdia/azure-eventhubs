package com.democompany.creditservices.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
public class UpdateResponse {
    UpdateRequest updateRequest;
    OffsetDateTime changeDate;
}