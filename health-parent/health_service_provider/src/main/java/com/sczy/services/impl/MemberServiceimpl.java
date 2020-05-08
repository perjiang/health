package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sczy.dao.MemberDao;
import com.sczy.pojo.Member;
import com.sczy.service.MemberService;
import com.sczy.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceimpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findByTelephone(String telephone) {
        Member member = memberDao.findByTelephone(telephone);
        return member;
    }

    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if(password != null){
            //使用md5将明文密码进行加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = null;
            Calendar c = Calendar.getInstance();
            int year = Integer.parseInt(month.substring(0,4));
            int mon = Integer.parseInt(month.substring(month.indexOf(".")+1));
            c.set(year,mon,0);
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (day == 28){
                date = month+".28";
            }
            if (day == 29){
                date = month+".29";
            }
            if (day == 30){
                date = month+".30";
            }
            if (day == 31){
                date = month+".31";
            }
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }
        return memberCount;
    }
}
