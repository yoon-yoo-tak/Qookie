package com.a504.qookie.domain.heart.repository;

import com.a504.qookie.domain.heart.entity.Heart;
import com.a504.qookie.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    List<Heart> findByMember(Member member);
}