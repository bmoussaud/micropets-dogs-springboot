package org.moussaud.micropets.pets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.observation.annotation.Observed;

@Observed(name = "DogGenerator")
@Service
public class DogGenerator {

        static Logger logger = LoggerFactory.getLogger(DogGenerator.class);

        @Autowired
        DogSummary data;

        public DogSummary generate() {

                logger.debug("generate data");
                data.clear();

                data.addDog("Medor", "Bulldog", 18,
                                "https://www.petmd.com/sites/default/files/10New_Bulldog_0.jpeg");
                data.addDog("Bill", "Bull Terrier", 12,
                                "https://www.petmd.com/sites/default/files/07New_Collie.jpeg");
                data.addDog("Rantanplan", "Labrador Retriever", 3,
                                "https://www.petmd.com/sites/default/files/01New_GoldenRetriever.jpeg");
                data.addDog("Lassie", "Golden Retriever", 1,
                                "https://www.petmd.com/sites/default/files/11New_MixedBreed.jpeg");
                data.addDog("Beethoven", "Great St Bernard", 30,
                                "https://upload.wikimedia.org/wikipedia/commons/6/64/Hummel_Vedor_vd_Robandahoeve.jpg");

                return data;

        }
}
