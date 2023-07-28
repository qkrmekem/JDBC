package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

/*
* JdbcTemplate 사용
* */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository {

    private final JdbcTemplate template;

    public MemberRepositoryV5(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?,?)";
        // 연결부터 예외처리까지 모두 해결해줌
        template.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }

    @Override
    public Member findById(String memberId){
        String sql = "select * from member where member_id = ?";
        // queryForObject 결과가 단 하나일 경우에 사용
        // 0 또는 1초과일 때는 예외가 발생함
        // query 결과가 여러개일 때 사용
        // 쿼리문이 실패할 경우에만 예외를 발생시킨다.
        return template.queryForObject(sql, memberRowMapper(), memberId);
    }

    // RowMapper는 데이터베이스의 반환 결과인 ResultSet을 객체로 변환해주는 클래스
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }

    @Override
    public void update(String memberId, int money){
        String sql = "update member set money = ? where member_id = ?";
        template.update(sql, money,memberId);
    }

    @Override
    public void delete(String memberId){
        String sql = "delete from member where member_id = ?";
        template.update(sql, memberId);
    }

}
