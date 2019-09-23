package com.zhrb.testDemo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestInstant
 * @Description TODO
 * @Author zhrb
 * @Date 2019/9/23 14:28
 * @Version
 */
public class TestInstant {
    public static void main(String[] args) {
        Instant instantNow = Instant.now();
        System.out.println("now:"+instantNow);

        Clock clock = Clock.systemUTC();
        System.out.println(clock);
        System.out.println(clock.instant());

        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
        System.out.println("秒数:"+now.getEpochSecond());
        System.out.println("毫秒数:"+now.toEpochMilli());
        System.out.println(now);

        //定义localDateTime
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate now1 = LocalDate.now();
        LocalTime now2 = LocalTime.now();
        System.out.println("localDateTime:"+localDateTime);
        System.out.println("now1:"+now1);
        System.out.println("now2:"+now2);
        System.out.println("localDateTimeFormat:"+localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //定义localDateTime2
        LocalDateTime localDateTime2 = localDateTime.minus(23, ChronoUnit.MONTHS);
        localDateTime.atZone(ZoneId.systemDefault());
        localDateTime2 = localDateTime2.withHour(3);
        localDateTime2 = localDateTime2.withYear(2019);
        localDateTime2 = localDateTime2.with(ChronoField.MONTH_OF_YEAR,3);
        System.out.println("localDateTime2:"+localDateTime2);

        //比较两个时间
        Duration duration = Duration.between(localDateTime,localDateTime2);
        System.out.println("比较日："+duration.toDays());
        System.out.println("比较时："+duration.toHours());
        System.out.println("比较分："+duration.toMinutes());

        Period period = Period.between(localDateTime.toLocalDate(),localDateTime2.toLocalDate());
        System.out.println("比较年："+period.getYears());
        System.out.println("比较月："+period.getMonths());
        System.out.println("比较日："+period.getDays());

        //互相转化
        Date date = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());

        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        //定义新的时间
        LocalDateTime newTime = LocalDateTime.now();
        //定义多种格式化类型
        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                // 直接使用常量创建DateTimeFormatter格式器
                //第1种
                DateTimeFormatter.ISO_LOCAL_DATE,
                //第2种
                DateTimeFormatter.ISO_LOCAL_TIME,
                //第3种
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                //第4种
                // 使用本地化的不同风格来创建DateTimeFormatter格式器
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM),
                //第5种
                DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG),
                //第6种
                // 根据模式字符串来创建DateTimeFormatter格式器
                DateTimeFormatter.ofPattern("Gyyyy%%MMM%%dd HH:mm:ss")
        };
        // 依次使用不同的格式器对LocalDateTime进行格式化
        System.out.println("开始使用DateTimeFormatter进行格式化");
        for(int i = 0 ; i < formatters.length ; i++){
            // 下面两行代码的作用相同
            System.out.println(newTime.format(formatters[i]));
            System.out.println(formatters[i].format(newTime));
        }
    }
}
