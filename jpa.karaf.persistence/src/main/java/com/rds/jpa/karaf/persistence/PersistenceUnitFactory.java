package com.rds.jpa.karaf.persistence;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gadei
 */
@Component(
        name = "com.rds.jpa.karaf.persistence.unit.factory",
        immediate = true
)
public class PersistenceUnitFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceUnitFactory.class);

    @Reference(
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.STATIC,
            target = "(osgi.unit.name=com.rds.jpa.karaf.pu)"
    )
    private EntityManagerFactoryBuilder emfb;

    private EntityManagerFactory emf;

    private final Map<String, Object> config;

    public PersistenceUnitFactory() {
        this.config = new HashMap<>();

        // default Derby connection config
        config.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        config.put("javax.persistence.jdbc.url", "jdbc:derby:memory:test;create=true");
        config.put("javax.persistence.jdbc.user", "");
        config.put("javax.persistence.jdbc.password", "");        
        config.put("eclipselink.target-database", "Derby");
    }

    @Activate
    public void activate(Map<String, Object> properties) {
        LOGGER.info("Activate: [{}]", emfb);

        // we can use the injected properties 
        
        // we need to test the EMFB was injected and our config properties are correct
        // we should be able to create and open a new EMF
        if (emfb != null) {
            try {
                emf = emfb.createEntityManagerFactory(config);
                if (emf.isOpen()) {
                    LOGGER.info("EMF: [{}]", emf);
                    EntityManager em = emf.createEntityManager();
                    LOGGER.info("EM: [{}]", em);
                    emf.close();
                }else{
                    LOGGER.warn("EMF must be opened!!!");
                }
            } catch (Exception ex) {
                    LOGGER.error("Exception", ex);
            }
        } else {
            LOGGER.error("EMFB must be injected!!!");
        }
    }

}
