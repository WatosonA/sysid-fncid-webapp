package com.example.sysid.model.resources;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;

/**
 * 他サイト向け店舗情報
 */

@Schema(name = "StoreAttachedInfoResource", description = "他サイト向け店舗情報")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class StoreAttachedInfoResource {

  private String storeId;

  private String pairStoreId;

  private String pairClientUserId;

  public StoreAttachedInfoResource storeId(String storeId) {
    this.storeId = storeId;
    return this;
  }

  /**
   * 店舗ID
   * @return storeId
  */
  
  @Schema(name = "storeId", description = "店舗ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("storeId")
  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public StoreAttachedInfoResource pairStoreId(String pairStoreId) {
    this.pairStoreId = pairStoreId;
    return this;
  }

  /**
   * 対向店舗ID
   * @return pairStoreId
  */
  
  @Schema(name = "pairStoreId", description = "対向店舗ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pairStoreId")
  public String getPairStoreId() {
    return pairStoreId;
  }

  public void setPairStoreId(String pairStoreId) {
    this.pairStoreId = pairStoreId;
  }

  public StoreAttachedInfoResource pairClientUserId(String pairClientUserId) {
    this.pairClientUserId = pairClientUserId;
    return this;
  }

  /**
   * 対向クライアントID
   * @return pairClientUserId
  */
  
  @Schema(name = "pairClientUserId", description = "対向クライアントID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pairClientUserId")
  public String getPairClientUserId() {
    return pairClientUserId;
  }

  public void setPairClientUserId(String pairClientUserId) {
    this.pairClientUserId = pairClientUserId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoreAttachedInfoResource storeAttachedInfoResource = (StoreAttachedInfoResource) o;
    return Objects.equals(this.storeId, storeAttachedInfoResource.storeId) &&
        Objects.equals(this.pairStoreId, storeAttachedInfoResource.pairStoreId) &&
        Objects.equals(this.pairClientUserId, storeAttachedInfoResource.pairClientUserId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storeId, pairStoreId, pairClientUserId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoreAttachedInfoResource {\n");
    sb.append("    storeId: ").append(toIndentedString(storeId)).append("\n");
    sb.append("    pairStoreId: ").append(toIndentedString(pairStoreId)).append("\n");
    sb.append("    pairClientUserId: ").append(toIndentedString(pairClientUserId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

