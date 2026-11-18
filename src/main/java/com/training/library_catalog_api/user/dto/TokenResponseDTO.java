package com.training.library_catalog_api.user.dto;

public record TokenResponseDTO (
        String accessToken,
        String tokenType,
        long expiresInSeconds
){
    public TokenResponseDTO{
        if(accessToken == null | accessToken.isBlank()){
            throw new IllegalArgumentException ("Access token must not be null or blank");
        }
        if (tokenType == null || tokenType.isBlank()){
            throw new IllegalArgumentException ("Token type cannot be null or blank");
        }
        if (expiresInSeconds <=0 ){
            throw new IllegalArgumentException ("Expiring seconds should be greater than 0");
        }
    }
}
