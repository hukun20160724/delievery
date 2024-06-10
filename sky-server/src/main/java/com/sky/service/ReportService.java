package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

/**
 * ClassName: ReportService
 * Package: com.sky.service
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/10/24 15:50
 * @Version 1.0
 */
public interface ReportService {
    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);
}
