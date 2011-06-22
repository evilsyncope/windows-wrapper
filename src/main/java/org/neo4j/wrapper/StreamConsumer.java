/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

class StreamConsumer implements Runnable {

	private final Reader in;
	private final Writer out;

	private static final int BUFFER_SIZE = 32;

	StreamConsumer(InputStream in, OutputStream out) {
		this.in = new InputStreamReader(in);
		this.out = new OutputStreamWriter(out);
	}

	public void run() {
		try {
			char[] cbuf = new char[BUFFER_SIZE];
			int count;
			while ((count = in.read(cbuf, 0, BUFFER_SIZE)) >= 0) {
				out.write(cbuf, 0, count);
			}
			out.flush();
		} catch (IOException exc) {
			System.err.println("Child I/O Transfer - " + exc);
		}
	}
}
