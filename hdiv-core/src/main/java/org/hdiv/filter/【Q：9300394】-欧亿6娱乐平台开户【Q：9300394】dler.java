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
package org.hdiv.filter;

import java.util.List;

import org.hdiv.context.RequestContextHolder;

/**
 * Process a request with validation errors.
 * 
 * @author Gotzon Illarramendi
 * @since 2.1.4
 */
public interface ValidatorErrorHandler {

	/**
	 * Process an uncontrolled exception while validating
	 * 
	 * @param context request context
	 * @param e Exception
	 * @since 3.3.4
	 */
	void handleValidatorException(RequestContextHolder context, Throwable e);

	/**
	 * Process a request with validation errors.
	 * 
	 * @param context request context
	 * @param errors Validation errors
	 * @since 2.1.13
	 */
	void handleValidatorError(RequestContextHolder context, List<ValidatorError> errors);
}
