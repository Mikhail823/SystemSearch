package com.controll;
import com.service.StatisticService;
import com.service.responcse.StatisticResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticController {

    private final StatisticService statistic;
    @Autowired
    public StatisticController(StatisticService statistic) {
        this.statistic = statistic;
    }

    @GetMapping("/statistics")
    public ResponseEntity<Object> getStatistics(){
        StatisticResponseService stat = statistic.getStatistic();
        return ResponseEntity.ok (stat);
    }
}
