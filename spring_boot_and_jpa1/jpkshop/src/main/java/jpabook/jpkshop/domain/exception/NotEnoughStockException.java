package jpabook.jpkshop.domain.exception;

public class NotEnoughStockException extends RuntimeException {

    // TODO: 근데 얘는 왜 custom exception 만든거?  IllegalStateException 같은것은 의미가 맞지 않아서 그런가?
    // TODO: RuntimeException 으로 한것은 try/catch나 throw 빼려고? 역시.. unchecked를 쓰려고 한거네/

    // override method로 받아 왔음.

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }


}
