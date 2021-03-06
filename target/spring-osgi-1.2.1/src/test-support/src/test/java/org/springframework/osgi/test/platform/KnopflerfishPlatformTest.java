/*
 * Copyright 2006-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.osgi.test.platform;

/**
 * @author Costin Leau
 * 
 */
public class KnopflerfishPlatformTest extends CommonPlatformTest {

	protected void setUp() throws Exception {
		System.setProperty("org.knopflerfish.osgi.registerserviceurlhandler", "false");
		super.setUp();
	}

	protected void tearDown() throws Exception {
		System.getProperties().remove("org.knopflerfish.osgi.registerserviceurlhandler");
		super.tearDown();
	}

	AbstractOsgiPlatform createPlatform() {
		return new KnopflerfishPlatform();
	}
}
