package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

// 인터페이스와 체크 예외를 사용할 경우
// 인터페이스에도 예외를 선언해줘야 한다.
public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
