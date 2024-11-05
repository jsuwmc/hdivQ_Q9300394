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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Editable data (text/textarea) validation definition.
 *
 * @author Gorka Vicente
 * @since HDIV 1.1
 */
public class Validation implements IValidation {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(Validation.class);

	/**
	 * Name of the editable validation.
	 */
	protected String name;

	/**
	 * Regular expression that values received in the parameter must fit.
	 */
	protected Pattern acceptedPattern;

	/**
	 * Regular expression that values received in the parameter can't fit.
	 */
	protected Pattern rejectedPattern;

	/**
	 * Component type to which apply the validation <code>this</code>.
	 */
	protected String componentType;

	/**
	 * True if the validation is part of the default validations.
	 */
	protected boolean defaultValidation = false;

	/**
	 * Checks if a component type has been defined to which apply the validation <code>this</code>.
	 *
	 * @return True if the component type to which apply de validation has been defined. False otherwise.
	 */
	protected boolean existComponentType() {

		return componentType != null;
	}

	/**
	 * Checks if the type <code>parameterType</code> is the same as the one defined in the validation <code>this</code>.
	 *
	 * @param parameterType Component type
	 * @return True if the validation <code>this</code> is the same as <code>parameterType</code>.
	 */
	protected boolean isTheSameComponentType(final String parameterType) {

		if ("password".equals(parameterType)) {
			return "text".equals(componentType);
		}
		return componentType.equalsIgnoreCase(parameterType);
	}

	/**
	 * <p>
	 * Checks if the values <code>values</code> are valid for the editable parameter <code>parameter</code>.
	 * </p>
	 * There are two types of validations:
	 * <ul>
	 * <li>accepted: the value is valid only if it passes the validation</li>
	 * <li>rejected: the value is rejected if doesn't pass the validation</li>
	 * </ul>
	 *
	 * @param parameter parameter name
	 * @param values parameter's values
	 * @param dataType editable data type
	 * @return True if the values <code>values</code> are valid for the parameter <code>parameter</code>.
	 * @since HDIV 1.1.1
	 */
	public boolean validate(final String parameter, final String[] values, final String dataType) {

		// we check if the component type we apply the validation to is
		// the same as the parameter's component type.
		if (dataType != null && existComponentType() && !isTheSameComponentType(dataType)) {
			return true;
		}

		// we validate all the values for the parameter
		for (String value : values) {

			if (value == null) {
				continue;
			}

			if (acceptedPattern != null) {
				try {
					Matcher m = acceptedPattern.matcher(value);
					if (!m.matches()) {
						return false;
					}
				}
				catch (Throwable e) {
					log.error("Error matching pattern " + acceptedPattern.toString() + " with value " + value, e);
				}
			}
			if (rejectedPattern != null) {
				try {
					Matcher m = rejectedPattern.matcher(value);
					if (m.matches()) {
						return false;
					}
				}
				catch (Throwable e) {
					log.error("Error matching pattern " + rejectedPattern.toString() + " with value " + value, e);
				}
			}
		}
		return true;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return componentType
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * @param componentType The comkponent type to set.
	 */
	public void setComponentType(final String componentType) {
		this.componentType = componentType;
	}

	/**
	 * @param acceptedPattern The accepted pattern to set.
	 */
	public void setAcceptedPattern(final String acceptedPattern) {
		try {
			this.acceptedPattern = Pattern.compile(acceptedPattern);
		}
		catch (PatternSyntaxException ex) {
			log.error("Error compiling pattern:" + acceptedPattern + ". It will be ignored.");
			System.err.println("Error compiling pattern:" + acceptedPattern + ". It will be ignored.");
		}
	}

	/**
	 * @return acceptedPattern
	 */
	public String getAcceptedPattern() {
		return acceptedPattern == null ? null : acceptedPattern.pattern();
	}

	/**
	 * @param rejectedPattern The rejected pattern to set.
	 */
	public void setRejectedPattern(final String rejectedPattern) {
		try {
			this.rejectedPattern = Pattern.compile(rejectedPattern);
		}
		catch (PatternSyntaxException ex) {
			log.error("Error compiling pattern:" + rejectedPattern + ". It will be ignored.");
			System.err.println("Error compiling pattern:" + rejectedPattern + ". It will be ignored.");
		}
	}

	/**
	 * @return rejectedPattern
	 */
	public String getRejectedPattern() {
		return rejectedPattern == null ? null : rejectedPattern.pattern();
	}

	/**
	 * @return defaultValidation
	 */
	public boolean isDefaultValidation() {
		return defaultValidation;
	}

	public void setDefaultValidation(final boolean defaultValidation) {
		this.defaultValidation = defaultValidation;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder().append("");
		result = result.append(" name=").append(getName());
		result = result.append(" componentType=").append(getComponentType());
		result = result.append(" acceptedPattern=").append(acceptedPattern == null ? "" : acceptedPattern.toString());
		result = result.append(" rejectedPattern=").append(rejectedPattern == null ? "" : rejectedPattern.toString());
		result = result.append(" defaultValidation=").append(defaultValidation);
		return result.toString();

	}

}
