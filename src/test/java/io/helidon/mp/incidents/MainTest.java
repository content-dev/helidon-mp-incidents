/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.mp.incidents;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


import io.helidon.microprofile.server.Server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MainTest {
	private static Server server;

	@BeforeAll
	public static void startTheServer() throws Exception {
		server = Main.startServer();
	}

	@Test
	void testIncidents() {

		Client client = ClientBuilder.newClient();

		JsonArray jsonArray = client.target(getConnectionString("/incidents")).request().get(JsonArray.class);

		Assertions.assertNotNull(jsonArray);

	}

	@Test
	void testIncident() {

		Client client = ClientBuilder.newClient();

		JsonObject jsonObject = client.target(getConnectionString("/incidents/11129")).request().get(JsonObject.class);

		Assertions.assertEquals("11129", jsonObject.getString("id"), "ID message");

	}

	@AfterAll
	static void destroyClass() {
		CDI<Object> current = CDI.current();
		((SeContainer) current).close();
	}

	private String getConnectionString(String path) {
		return "http://localhost:" + server.port() + path;
	}
}
