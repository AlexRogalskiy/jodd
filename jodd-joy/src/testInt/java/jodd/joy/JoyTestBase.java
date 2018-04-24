// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.joy;

import jodd.exception.UncheckedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class JoyTestBase {

	public static boolean isRunning;

	/**
	 * Starts Tomcat after the suite.
	 */
	@BeforeAll
	static void beforeClass() {
		isRunning = true;
		startTomcat();
		startJetty();
	}

	/**
	 * Stop Tomcat after the suite.
	 */
	@AfterAll
	static void afterSuite() {
		isRunning = false;
		stopTomcat();
		stopJetty();
	}

	// ---------------------------------------------------------------- tomcat

	private static JoyTomcatTestServer server1;
	private static JoyJettyTestServer server2;

	/**
	 * Starts Tomcat.
	 */
	public static void startTomcat() {
		if (server1 != null) {
			return;
		}
		server1 = new JoyTomcatTestServer();
		try {
			server1.start();
			System.out.println("Tomcat test server started");
		} catch (Exception e) {
			throw new UncheckedException(e);
		}
	}
	public static void startJetty() {
		if (server2 != null) {
			return;
		}
		server2 = new JoyJettyTestServer();
		try {
			server2.start();
			System.out.println("Tomcat test server started");
		} catch (Exception e) {
			throw new UncheckedException(e);
		}
	}

	/**
	 * Stops Tomcat if not in the suite.
	 */
	public static void stopTomcat() {
		if (server1 == null) {
			return;
		}
		if (isRunning) {	// dont stop tomcat if it we are still running in the suite!
			return;
		}
		try {
			server1.stop();
		} catch (Exception ignore) {
		} finally {
			System.out.println("Tomcat test server stopped");
			server1 = null;
		}
	}

	public static void stopJetty() {
		if (server2 == null) {
			return;
		}
		if (isRunning) {	// dont stop tomcat if it we are still running in the suite!
			return;
		}
		try {
			server2.stop();
		} catch (Exception ignore) {
		} finally {
			System.out.println("Jetty test server stopped");
			server2 = null;
		}
	}

}