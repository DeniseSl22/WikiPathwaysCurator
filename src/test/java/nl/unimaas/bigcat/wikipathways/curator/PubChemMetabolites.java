/* Copyright (C) 2015,2018  Egon Willighagen <egon.willighagen@gmail.com>
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the <organization> nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package nl.unimaas.bigcat.wikipathways.curator;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.unimaas.bigcat.wikipathways.curator.assertions.IAssertion;
import nl.unimaas.bigcat.wikipathways.curator.tests.PubChemMetabolitesTests;

public class PubChemMetabolites extends JUnitTests {

	@BeforeEach
	public void waitForIt() throws InterruptedException { Thread.sleep(OPSWPRDFFiles.SLEEP_TIME); }

	@Test
	public void nonNumericIDs() throws Exception {
		Assertions.assertTimeout(Duration.ofSeconds(50), () -> {
			SPARQLHelper helper = (System.getProperty("SPARQLEP").contains("http:"))
				? new SPARQLHelper(System.getProperty("SPARQLEP"))
		        : new SPARQLHelper(OPSWPRDFFiles.loadData());
			List<IAssertion> assertions = PubChemMetabolitesTests.nonNumericIDs(helper);
			performAssertions(assertions);
		});
	}

	@Test
	public void nonLive2LiveIdentifiers() throws Exception {
		Assertions.assertTimeout(Duration.ofSeconds(20), () -> {
			SPARQLHelper helper = (System.getProperty("SPARQLEP").contains("http:"))
				? new SPARQLHelper(System.getProperty("SPARQLEP"))
		        : new SPARQLHelper(OPSWPRDFFiles.loadData());
			List<IAssertion> assertions = PubChemMetabolitesTests.nonLive2LiveIdentifiers(helper);
			performAssertions(assertions);
		});
	}

	@Test
	public void nonExistingIdentifiers() throws Exception {
		Assertions.assertTimeout(Duration.ofSeconds(20), () -> {
			SPARQLHelper helper = (System.getProperty("SPARQLEP").contains("http:"))
				? new SPARQLHelper(System.getProperty("SPARQLEP"))
		        : new SPARQLHelper(OPSWPRDFFiles.loadData());
			List<IAssertion> assertions = PubChemMetabolitesTests.nonExistingIdentifiers(helper);
			performAssertions(assertions);
		});
	}

}
