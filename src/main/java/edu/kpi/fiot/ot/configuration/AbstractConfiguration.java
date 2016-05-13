/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kpi.fiot.ot.configuration;

import java.io.IOException;
import java.util.Properties;

import edu.kpi.fiot.ot.utils.ProjectUtils;

/**
 * Abstract class that loads some configuration file.
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class AbstractConfiguration implements Configuration {

    /**
     * Error detail message.
     */
    private static final String ERROR_CONF_LOAD = "Some problems with loading config file %s";

    /**
     * Property file object.
     */
    protected Properties appProp;

    /**
     * Constructor for loading a configuration from config path.
     *
     * @param confPath - path of config file
     */
    protected AbstractConfiguration(String confPath) {
        try {
        	appProp = ProjectUtils.loadProperties(confPath);
        } catch (IOException ex) {
        	System.out.println(String.format(ERROR_CONF_LOAD, confPath));
        }
    }

    @Override
    public String getProperty(String propName) {
        return appProp.getProperty(propName);
    }
}
