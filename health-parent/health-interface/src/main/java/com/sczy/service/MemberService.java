package com.sczy.service;

import com.sczy.pojo.Member;

import java.util.List;

public interface MemberService {
    Member findByTelephone(String telephone);
    public void add(Member member);

    List<Integer> findMemberCountByMonths(List<String> months);
}
