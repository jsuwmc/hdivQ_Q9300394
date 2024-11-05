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
package org.hdiv.validator;

import java.io.Serializable;
import java.util.List;

/**
 * Validation rules container.
 * 
 * @since HDIV 2.1.10
 */
public interface ValidationRepository extends Serializable {

	/**
	 * Returns the validation rules for a concrete url and parameter name.
	 * 
	 * @param url url
	 * @param parameter parameter name
	 * @return Selected validations
	 */
	List<IValidation> findValidations(String url, String parameter);

	/**
	 * Returns default validation rules.
	 * 
	 * @return Default validations
	 */
	List<IValidation> findDefaultValidations();
}
