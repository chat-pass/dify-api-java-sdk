package io.github.chatpass.dify.exception;

public class DifyApiException extends RuntimeException{
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
            if (difyApiError.getCode() != null && difyApiError.getStatus() != null)
                return String.format("[%s] %s | %s", difyApiError.getStatus(), difyApiError.getCode(), difyApiError.getMessage());
            if (difyApiError.getMessage() != null)
                return String.format("[%s] %s | %s", difyApiError.getStatus(), difyApiError.getCode(), difyApiError.getMessage());

            return difyApiError.getMessage();
        }

        return super.getMessage();
    }

    public DifyApiError getError() {
        return difyApiError;
    }
}
