package com.mcaffee.ocdsim.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mcaffee.ocdsim.OCDSim;


public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(800, 800);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new OCDSim();
        }
}