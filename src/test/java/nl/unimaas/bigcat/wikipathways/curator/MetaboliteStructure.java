/* Copyright (C) 2013  Egon Willighagen <egon.willighagen@gmail.com>
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

import nl.unimaas.bigcat.wikipathways.curator.SPARQLHelper;
import nl.unimaas.bigcat.wikipathways.curator.StringMatrix;

import org.junit.Assert;
import org.junit.Test;

public class MetaboliteStructure {

	private static String WP_SPARQL_END_POINT = "http://sparql.wikipathways.org/";

	@Test
	public void nullDataSources() throws Exception {
		String sparql = ResourceHelper.resourceAsString("structure/metaboliteClass.rq");
		StringMatrix table = SPARQLHelper.sparql(WP_SPARQL_END_POINT, sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Metabolite DataNode count failed:\n" + table, 1, table.getRowCount());
		Assert.assertTrue("Unexpectedly low metabolite count:\n" + table.getColumn("count").get(0),
			Integer.valueOf(table.getColumn("count").get(0)) > 5000
		);
	}

	@Test
	public void isPartOfAPathway() throws Exception {
		String sparql = ResourceHelper.resourceAsString("structure/isPartOfAPathway.rq");
		StringMatrix table = SPARQLHelper.sparql(WP_SPARQL_END_POINT, sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Found metabolites that are not part of a pathway:\n" + table, 0, table.getRowCount());
	}

}
