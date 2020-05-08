package com.sczy.test;

import com.alibaba.dubbo.common.serialize.support.fst.FstObjectOutput;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class data {
//    @Test
//    public void test() {
////        String a = "2020-3";
////        int i = a.indexOf("-");
////        System.out.println(i);
////        String year = a.substring(0, 4);
////        String month = a.substring(i+1,a.length());
////        System.out.println(month);
////        int yea = Integer.parseInt(year);
////        int mon = Integer.parseInt(month);
////        System.out.println(year);
////        System.out.println(yea);
//        Calendar c = Calendar.getInstance();
////        c.set(yea,mon,0);
////        System.out.println(c.get(Calendar.DAY_OF_MONTH));
//        String b = "2020.02";
//        String year = b.substring(0, 4);
//        String month = b.substring(b.indexOf(".") + 1);
//        int yea = Integer.parseInt(year);
//        System.out.println(c.get(Calendar.MONTH));
//        int mon = Integer.parseInt(month);
//        c.set(2020, 2, 0);
//        System.out.println(c.get(Calendar.DAY_OF_MONTH));
//    }
//}


    public static void main(String[] args) {
        List<String> moths = new ArrayList<>();
        moths.add("2020.05");
        moths.add("2020.04");
        moths.add("2020.03");
        moths.add("2020.02");
        moths.add("2020.01");
        moths.add("2019.12");
        moths.add("2019.11");
        moths.add("2019.10");
        moths.add("2019.09");
        moths.add("2019.08");
        moths.add("2019.07");
        moths.add("2019.06");
        moths.add("2019.05");

        findMemberCountByMonths(moths);
    }

    public static void findMemberCountByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = null;
            Calendar c = Calendar.getInstance();
            int year = Integer.parseInt(month.substring(0, 4));
            int mon = Integer.parseInt(month.substring(month.indexOf(".") + 1));
            c.set(year, mon, 0);
            int day = c.get(Calendar.DAY_OF_MONTH);
            System.out.println(day);
            if (day == 28) {
                date = month + ".28";
            }
            if (day == 29) {
                date = month + ".29";
            }
            if (day == 30) {
                date = month + ".30";
            } else {
                date = month + ".31";
            }
            System.out.println(date);
        }
    }
}
