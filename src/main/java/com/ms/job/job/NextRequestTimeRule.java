package com.ms.job.job;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class NextRequestTimeRule {

    public static Date nextTime(Date request, int count) {
        if (count <= 1) {
            // + 2分钟
            return DateUtils.addMinutes(request, 2);
        } else if (count <= 2) {
            // + 10分钟
            return DateUtils.addMinutes(request, 10);
        } else if (count <= 3) {
            // + 10分钟
            return DateUtils.addMinutes(request, 10);
        } else if (count <= 4) {
            // + 1小时
            return DateUtils.addHours(request, 1);
        } else if (count <= 5) {
            // + 2小时
            return DateUtils.addHours(request, 2);
        } else if (count <= 6) {
            // + 6小时
            return DateUtils.addHours(request, 6);
        } else if (count <= 7) {
            // + 12小时
            return DateUtils.addHours(request, 12);
        }
        // + 24小时
        return DateUtils.addHours(request, 24);
    }
}