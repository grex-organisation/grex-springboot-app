package com.grex.common.messages;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GenericMessage {

    private int code;
    private HttpStatus status;
    private Object data;

    public GenericMessage(HttpStatus status, Object data) {
        this.code = status.value();
        this.status = status;
        this.data = data;
    }
}
