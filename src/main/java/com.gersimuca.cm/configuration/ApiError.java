package com.gersimuca.cm.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import lombok.Setter;

/** General error response model. */
@Setter
@Schema(name = "ApiError", description = "General error response model.")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2024-10-14T10:14:34.998451800+02:00[Europe/Berlin]",
    comments = "Generator version: 7.8.0")
public class ApiError implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer status;

  private String code;

  private String description;

  public ApiError() {
    super();
  }

  /** Constructor with only required parameters */
  public ApiError(Integer status, String code, String description) {
    this.status = status;
    this.code = code;
    this.description = description;
  }

  public ApiError status(Integer status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @NotNull
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }

  public ApiError code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code
   */
  @NotNull
  @Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public ApiError description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   *
   * @return description
   */
  @NotNull
  @Schema(name = "description", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiError apiError = (ApiError) o;
    return Objects.equals(this.status, apiError.status)
        && Objects.equals(this.code, apiError.code)
        && Objects.equals(this.description, apiError.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, code, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiError {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
