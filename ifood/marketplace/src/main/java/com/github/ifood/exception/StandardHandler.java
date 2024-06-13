package com.github.ifood.exception;

public class StandardHandler {

    private String message;
    private String path;
    private Long timestamp;
    private int status;

    public StandardHandler(String message, String path, Long timestamp, int status) {
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
