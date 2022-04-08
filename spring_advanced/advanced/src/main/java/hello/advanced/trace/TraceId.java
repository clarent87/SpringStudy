package hello.advanced.trace;

import java.util.UUID;

public class TraceId {

    private final String id; // this is txid
    private final int level; // this is the method level (ex: |--> )

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        // not consider that id is duplicated. because that is log-id not order id which is very important
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * id는 같고 level만 다르게 만들기 위한 method
     * @return
     */
    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
