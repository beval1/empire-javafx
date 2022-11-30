package com.beval.empirejavafx.exception;

public class NotEnoughResourcesException extends CustomException {
    public NotEnoughResourcesException(String message) {
        super(message);
    }

    public NotEnoughResourcesException() {
        super("Not Enough Resources!");
    }
}
