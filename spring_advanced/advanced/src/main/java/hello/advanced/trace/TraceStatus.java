package hello.advanced.trace;

/**
 * 로그의 상태정보를 나타내기 위한 class
 */
public class TraceStatus {
    private final TraceId traceId;
    private final Long startTimeMs; // 종료시간 계산을 위함
    private final String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

}
