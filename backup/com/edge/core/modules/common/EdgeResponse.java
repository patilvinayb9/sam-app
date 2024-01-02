package com.edge.core.modules.common;

import com.edge.core.exception.AppRuntimeException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EdgeResponse<T> {

    private boolean success = true;
    private String type = "success";
    private T responseData = null;
    private String header = null;  // Must for UI
    private String footer = null;

    private List<String> messages = new ArrayList<String>();
    private List<String> errors = new ArrayList<String>();

    private EdgeResponse() {

    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void addError(String error) {
        type = "danger";
        success = false;
        errors.add(error);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <T> EdgeResponse<T> createDataResponse(T responseData, String successHeader) {
        return createSuccessResponse(responseData, successHeader, null, null);
    }

    public static <T> EdgeResponse<T> createInfoResponse(T responseData, String successHeader) {
        EdgeResponse<T> edgeResponse = createSuccessResponse(responseData, successHeader, null, null);
        edgeResponse.setType("info");
        return edgeResponse;
    }

    public static <T> EdgeResponse<T> createSuccessResponse(T responseData, String successHeader, String successFooter, List<String> messages) {

        EdgeResponse<T> edgeResponse = new EdgeResponse<T>();

        edgeResponse.responseData = responseData;
        edgeResponse.type = "success";
        edgeResponse.success = true;

        edgeResponse.header = successHeader;
        edgeResponse.footer = successFooter;

        if (messages != null) {
            edgeResponse.messages.addAll(messages);
        }

        return edgeResponse;
    }

    public static <T> EdgeResponse<T> createExceptionResponse(T data, AppRuntimeException ex) {
        return createErrorResponse(data, ex.getCustomMessage(), null, null);
    }

    public static EdgeResponse createExceptionResponse(AppRuntimeException ex) {
        return createErrorResponse(ex.getCustomMessage(), ex.getCustomMessage(), null, ex.getErrorMessages());
    }

    public static <T> EdgeResponse<T> createErrorResponse(T responseData, String errorHeader, String errorFooter, List<String> errors) {

        EdgeResponse<T> edgeResponse = new EdgeResponse<T>();

        edgeResponse.responseData = responseData;
        edgeResponse.type = "danger";
        edgeResponse.success = false;

        edgeResponse.header = errorHeader;
        edgeResponse.footer = errorFooter;

        if (errors != null) {
            edgeResponse.errors.addAll(errors);
        }

        return edgeResponse;
    }

}
