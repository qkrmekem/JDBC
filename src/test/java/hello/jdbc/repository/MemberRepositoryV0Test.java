package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member( "memberV200", 10000);
        repository.save(member);

        //findById
        Member findById = repository.findById(member.getMemberId());
        log.info("findMember={}", findById);
        // equal이라는 메서드는 toString처럼 도메인에서 오버라이딩 해줘야하는 메서드인데
        // 객체의 필들의 값이 같으면 같은 객체로 판단한다.
        assertThat(findById).isEqualTo(member);

        //update: money: 10000 -> 20000
        repository.update(member.getMemberId(), 20000);
        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() ->
                repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

    }
}