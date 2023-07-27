package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    /*
    * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
    * 언체크 예외는 컴파일러가 체크하지 않는 예외를 말한다.
    * 다만 throws를 선언해두면 중요한 예외의 경우 개발자가 ide를 통해 인지할 수 있다.
    * */
    static class MyUncheckedException extends RuntimeException{
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        service.callCatch();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    /*
    * Unchecked예외는
    * 예외를 잡거나, 던지지 않아도 된다.
    * 예외를 잡지 않으면 자동으로 밖으로 던진다.
    * */
    static class Service {
        Repository repository = new Repository();

        // 필요한 경우 예외를 잡아서 처리하면 된다.
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                // 예외 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /*
        * 예외를 잡지 않아도 된다. 자연스럽게 상위로 넘어간다.
        * 체크 예외와 다르게 throws 예외 선언을 하지 않아도 된다.
        * */
        public void callThrow() {
            repository.call();
        }
    }


    static class Repository{
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }

}
