package learn.backendserver.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result<T> {
    private ResultType resultType = ResultType.SUCCESS;
    private final ArrayList<String> messages = new ArrayList<>();
    private T payload;

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public boolean isSuccess() {
        return resultType == ResultType.SUCCESS;
    }

    public void addMessage(String message, Object... args) {
        addMessage(ResultType.INVALID, message, args);
    }

    public void addMessage(ResultType resultType, String message, Object... args) {
        this.resultType = resultType;
        this.messages.add(String.format(message, args));
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return resultType == result.resultType && Objects.equals(messages, result.messages) && Objects.equals(payload, result.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultType, messages, payload);
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultType=" + resultType +
                ", messages=" + messages +
                ", payload=" + payload +
                '}';
    }
}