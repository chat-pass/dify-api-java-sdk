package io.github.chatpass.dify.exception;

public class DifyApiException extends RuntimeException {
    private DifyApiError difyApiError;

    public DifyApiException() {
        super();
    }

    public DifyApiException(Throwable cause) {
        super(cause);
    }

    public DifyApiException(DifyApiError difyApiError) {
        this.difyApiError = difyApiError;
    }

    @Override
    public String getMessage() {
        if (difyApiError != null) {
            StringBuilder message = new StringBuilder();

            if (difyApiError.getStatus() != null) {
                message.append("[").append(difyApiError.getStatus()).append("]");
            }

            if (difyApiError.getCode() != null) {
                if (message.length() > 0)
                    message.append(" ");
                message.append(difyApiError.getCode());
            }

            if (difyApiError.getMessage() != null) {
                if (message.length() > 0)
                    message.append(" | ");
                message.append(difyApiError.getMessage());
            }

            return message.length() > 0 ? message.toString() : "Unknown API error";
        }

        return super.getMessage();
    }

    public DifyApiError getError() {
        return difyApiError;
    }
}
