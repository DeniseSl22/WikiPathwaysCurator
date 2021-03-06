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
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.jena.rdf.model.Model;

public class General {

	@BeforeClass
	public static void loadData() throws InterruptedException {
		Model data = OPSWPRDFFiles.loadData();
		Assert.assertTrue(data.size() > 5000);
	}
	
	@Test
	public void nullDataSources() throws Exception {
		String sparql = ResourceHelper.resourceAsString("general/nullDataSource.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Data nodes with a 'null' data source:\n" + table, 0, table.getRowCount());
	}

	@Ignore("This test was predominantly for the WPRDF generation, but in the new generation RDF this no longer causes problems")
	public void noIdentifierURIs() throws Exception {
		String sparql = ResourceHelper.resourceAsString("general/noIdentifierURIs.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Data nodes with a 'noIdentifier' URI:\n" + table, 0, table.getRowCount());
	}

	@Test
	public void emptyLabelOfNodeWithIdentifier() throws Exception {
		String sparql = ResourceHelper.resourceAsString("general/emptyLabelsWithIdentifiers.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Data nodes with an identifier but empty label:\n" + table, 0, table.getRowCount());
	}

	@Test
	public void dataNodeWithoutGraphId() throws Exception {
		String sparql = ResourceHelper.resourceAsString("structure/dataNodeWithoutGraphId.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Data nodes without @GraphId:\n" + table, 0, table.getRowCount());
	}

	@Test
	public void hasPoints() throws Exception {
		String sparql = ResourceHelper.resourceAsString("structure/pointClass.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertNotSame("Expected things of type gpml:Point.", 0, table.getRowCount());
	}

	@Test
	public void groupsHaveDetail() throws Exception {
		String sparql = ResourceHelper.resourceAsString("structure/groupDetails.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Expected details for things of type gpml:Group: " + table, 0, table.getRowCount());
	}

	@Test
	public void nodesHaveTypedParents() throws Exception {
		String sparql = ResourceHelper.resourceAsString("structure/nodesHaveTypedParents.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Parents of DataNodes should be typed: " + table, 0, table.getRowCount());
	}

	@Ignore("Apparently, groups without a type are a common use case; examples are WP2940 and WP2543.")
	public void nodesPointingToUnspecifiedGroups() throws Exception {
		String sparql = ResourceHelper.resourceAsString("structure/nodesInEmptyGroups.rq");
		StringMatrix table = SPARQLHelper.sparql(OPSWPRDFFiles.loadData(), sparql);
		Assert.assertNotNull(table);
		Assert.assertEquals("Nodes should not be part of unspecified groups: " + table, 0, table.getRowCount());
	}

}
