/**
 * Copyright 2005-2016 hdiv.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hdiv.idGenerator;

import java.util.Random;
import java.util.UUID;

/**
 * This implementation uses a sequence number to generate unique page ids.
 *
 * @author Gotzon Illarramendi
 * @since HDIV 2.1.0
 */
public class SequentialPageIdGenerator implements PageIdGenerator {

	private static final long serialVersionUID = 5878935796457886668L;

	/**
	 * Sequence number
	 */
	private int id;

	/**
	 * Constructor that initializes the sequence number in a non-constant value.
	 */
	public SequentialPageIdGenerator() {
		id = generateInitialPageId();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.hdiv.util.PageIdGenerator#getNextPageId()
	 */
	public synchronized UUID getNextPageId() {
		return new UUID(0, ++id);
	}

	/**
	 * Generate the initial number of sequencer, which is based on a random value between 1 and 20.
	 *
	 * @return valor sequencer initial value
	 */
	protected int generateInitialPageId() {
		Random r = new Random();
		return r.nextInt(20);
	}

}
