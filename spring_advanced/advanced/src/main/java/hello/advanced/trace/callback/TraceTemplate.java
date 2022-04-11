package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    // 메소드 제네릭.
    // 파라메터로 message와 callback을 받음
    public <T> T execute(String message, TraceCallback<T> callback ) {
        TraceStatus status = null;

        try {
            status = trace.begin(message); //로직 호출
            T result = callback.call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
