package com.democompany.creditservices.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateRequest {

  @Pattern(regexp = "^([0-9]{15,16})$", message = "amount should contain only numbers.")
  @Schema(description="Account Id for credit update")
  private String accountId;

  @Size(min=1, max=10)
  @Pattern(regexp = "\\d+", message = "amount should contain only numbers.")
  @Schema(description="Amount limit for credit.")
  private String amount;

  @Size(min=2, max=30, message="sourceSystem must have in size 2 to 30 characters.")
  @Schema(description="System that request the update of credit limit.")
  private String sourceSystem;
}