package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    // service에서 예외를 잡아서 처리했기 때문에 정상 흐름으로 반환되어
    // 이후 코드도 정상적으로 처리를 진행한다.
    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        // 이렇게 처리하면 예외를 처리하지 않았으므로 컴파일 에러가 발생함
        // service.callThrow();
        // 따라서 테스트에서는 아래처럼 처리를 해야함
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);

    }

    /*
    * Exception을 상속받은 예외는 체크 예외가 된다.
    * */
    static class MyCheckedException extends Exception{
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /*
    * Checked 예외는
    * 예외를 잡아서 처리하거나, 던지거나 둘 중 하나를 필수로 선택해야 한다.
    * */
    static class Service {
        Repository repository = new Repository();

        /*
         * 예외를 잡아서 처리하는 코드
         * */
        public void callCatch() {
            // 컴파일이 해당 예외처리 여부를 체크해주는 예외가 체크 예외
            try {
                repository.call();

            } catch (MyCheckedException e) {
                // 로그로 예외 처리할 때 exception을 스택트레이스로 출력할 때는 마지막 파라미터로 넣어주면 됨
                log.info("예외 처리, message", e.getMessage(), e);
            }
        }

        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    /*
    * 체크 예외를 밖으로 던지는 코드
    * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야한다.
    * */
    static class Repository {
        // 모든 예외는 잡아서 처리를 하거나 던져야 하므로 여기서는 던짐
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }
}
