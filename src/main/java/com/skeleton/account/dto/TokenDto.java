package com.skeleton.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String token;
    private String tokenType;
    private Long tokenLiveTimeInSeconds;
}
