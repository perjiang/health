package com.sczy.dao;

import com.sczy.pojo.Member;

import java.util.Date;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(String date);

    Integer findMemberCountByDate(String today);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String thisWeekMonday);

    Integer reportAgeAndSexman();

    Integer reportAgeAndSexwoman();

    Integer AgeBandFunction(Date start, Date stop);

    Integer AgeBandFunctionNull(Date start);
}
