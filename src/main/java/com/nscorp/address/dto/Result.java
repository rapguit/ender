package com.nscorp.address.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by raphael on 26/03/17.
 */
@Data
@Builder
public class Result implements Serializable {
    private static final long serialVersionUID = 840297644344918143L;

    private int status;
    private String message;
}
