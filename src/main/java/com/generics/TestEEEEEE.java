package com.generics;

import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.internal.utils.configuration.StandaloneConfiguration;
import org.openqa.grid.web.Hub;

public class TestEEEEEE {
	public static void main(String[] args) {
		GridHubConfiguration obj = new GridHubConfiguration();
		GridHubConfiguration.loadFromJSON(StandaloneConfiguration.DEFAULT_STANDALONE_CONFIG_FILE);
		System.out.println(StandaloneConfiguration.DEFAULT_STANDALONE_CONFIG_FILE);
		Hub objHub = new Hub(obj);
		try {
			objHub.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(objHub.getNewSessionRequestCount());
	}
}
