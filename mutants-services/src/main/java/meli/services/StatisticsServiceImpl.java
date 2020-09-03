package meli.services;

import meli.interfaces.StatisticsDao;
import meli.interfaces.StatisticsService;
import meli.models.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public Statistics generateStatistics() {
        long mutants = statisticsDao.countByIfMutants(true);
        long nonMutants = statisticsDao.countByIfMutants(false);
        double ratio;
        if (mutants != 0 || nonMutants != 0)
            ratio = ((double)mutants)/(mutants+nonMutants);
        else
            ratio = 0d;
        return new Statistics(mutants, nonMutants, ratio);
    }
}
