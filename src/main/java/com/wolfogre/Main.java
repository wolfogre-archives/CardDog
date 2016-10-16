package com.wolfogre;

import com.wolfogre.dao.CardInfoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by wolfogre on 10/16/16.
 */
@Component
public class Main implements CommandLineRunner {

    private final CardInfoRepository cardInfoRepository;

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public Main(CardInfoRepository cardInfoRepository) {
        this.cardInfoRepository = cardInfoRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info(cardInfoRepository.count());
        logger.info(cardInfoRepository.findOne("20160414201746873627").getTransdate());
    }
}
