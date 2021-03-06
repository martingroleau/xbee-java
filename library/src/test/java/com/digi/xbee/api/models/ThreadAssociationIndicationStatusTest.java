/**
 * Copyright 2017, Digi International Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES 
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR 
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES 
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN 
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF 
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package com.digi.xbee.api.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.digi.xbee.api.utils.HexUtils;

public class ThreadAssociationIndicationStatusTest {

	// Variables.
	private ThreadAssociationIndicationStatus[] associationIndicationStatusValues;
	
	
	@Before
	public void setup() {
		// Retrieve the list of enum. values.
		associationIndicationStatusValues = ThreadAssociationIndicationStatus.values();
	}
	
	/**
	 * Test method for {@link com.digi.xbee.api.models.ThreadAssociationIndicationStatus#getValue()}.
	 * 
	 * <p>Verify that the ID of each {@code ThreadAssociationIndicationStatus} 
	 * entry is valid.</p>
	 */
	@Test
	public void testAssociationIndicationStatusValues() {
		for (ThreadAssociationIndicationStatus associationIndicationStatus:associationIndicationStatusValues)
			assertTrue(associationIndicationStatus.getValue() >= 0);
	}
	
	/**
	 * Test method for {@link com.digi.xbee.api.models.ThreadAssociationIndicationStatus#name()}.
	 * 
	 * <p>Verify that the name of each {@code ThreadAssociationIndicationStatus} 
	 * entry is valid.</p>
	 */
	@Test
	public void testAssociationIndicationStatusNames() {
		for (ThreadAssociationIndicationStatus associationIndicationStatus:associationIndicationStatusValues) {
			assertNotNull(associationIndicationStatus.name());
			assertTrue(associationIndicationStatus.name().length() > 0);
		}
	}
	
	/**
	 * Test method for {@link com.digi.xbee.api.models.ThreadAssociationIndicationStatus#getDescription()}.
	 * 
	 * <p>Verify that the description of each {@code ThreadAssociationIndicationStatus} 
	 * entry is valid.</p>
	 */
	@Test
	public void testAssociationIndicationStatusDescriptions() {
		for (ThreadAssociationIndicationStatus associationIndicationStatus:associationIndicationStatusValues) {
			assertNotNull(associationIndicationStatus.getDescription());
			assertTrue(associationIndicationStatus.getDescription().length() > 0);
		}
	}
	
	/**
	 * Test method for {@link com.digi.xbee.api.models.ThreadAssociationIndicationStatus#get(int)}.
	 * 
	 * <p>Verify that each {@code ThreadAssociationIndicationStatus} entry can be 
	 * retrieved statically using its value.</p>
	 */
	@Test
	public void testAssociationIndicationStatusStaticAccess() {
		for (ThreadAssociationIndicationStatus associationIndicationStatus:associationIndicationStatusValues)
			assertEquals(associationIndicationStatus, ThreadAssociationIndicationStatus.get(associationIndicationStatus.getValue()));
	}
	
	/**
	 * Test method for {@link com.digi.xbee.api.models.ThreadAssociationIndicationStatus#get(int)}.
	 * 
	 * <p>Verify that when trying to get an invalid {@code ThreadAssociationIndicationStatus} 
	 * entry, a {@code null} entry is retrieved.</p>
	 */
	@Test
	public void testAssociationIndicationStatusStaticInvalidAccess() {
		assertNull(ThreadAssociationIndicationStatus.get(0xAA));
	}
	
	/**
	 * Test method for {@link com.digi.xbee.api.models.ThreadAssociationIndicationStatus#toString()}.
	 * 
	 * <p>Verify that the {@code toString()} method of an {@code ThreadAssociationIndicationStatus} 
	 * entry returns its description correctly.</p>
	 */
	@Test
	public void testAssociationIndicationStatusToString() {
		for (ThreadAssociationIndicationStatus associationIndicationStatus:associationIndicationStatusValues)
			assertEquals(HexUtils.byteToHexString((byte)associationIndicationStatus.getValue()) + ": " + associationIndicationStatus.getDescription(), associationIndicationStatus.toString());
	}
}