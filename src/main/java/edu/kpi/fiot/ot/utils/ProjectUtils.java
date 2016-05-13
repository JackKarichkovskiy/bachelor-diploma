package edu.kpi.fiot.ot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectUtils {
	
	/**
     * Method checks the object by null equality.
     *
     * @param <T> - type of object
     * @param obj - object that needs to be checked
     * @return the origin value
     * @throws IllegalArgumentException if (object == null)
     */
    public static final <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }

        return obj;
    }
    
    /**
     * Method loads and constructs Properties object from some config file.
     *
     * @param confPath - location of config file
     * @return Properties object that represents config file
     * @throws IOException - if IO problems
     */
    public static final Properties loadProperties(String confPath) throws IOException {
        checkNotNull(confPath);

        Properties resultProp = new Properties();

        resultProp.load(new FileInputStream(new File(confPath)));

        return resultProp;
    }
}
