package com.effective.chapter08;

import java.util.Date;

/**
 * 表示一段不可变的时间周期
 */
public final class Period {
    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        // 这里要先进行拷贝后检查参数，防止检查参数过程中其他线程修改数据
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException("开始时间不能大于结束时间");
        }
    }

    /**
     * 返回一个新复制的对象，防止被修改
     */
    public Date getStart() {
        return new Date(this.start.getTime());
    }

    public Date getEnd() {
        return new Date(this.end.getTime());
    }
}
