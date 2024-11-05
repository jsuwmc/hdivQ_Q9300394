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
package org.hdiv.taglib.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.RadioTag;
import org.hdiv.dataComposer.IDataComposer;
import org.hdiv.util.HDIVUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Renders an HTML <b>&lt;input&gt;</b> element of type <b>radio</b>, populated from the specified property of the bean associated with our
 * current form. This tag is only valid when nested inside a form tag body.
 * </p>
 * <p>
 * If an iterator is used to render a series of radio tags, the idName attribute may be used to specify the name of the bean exposed by the
 * iterator. In this case, the value attribute is used as the name of a property on the idName bean that returns the value of the radio tag
 * in this iteration.
 * </p>
 * 
 * @author Gorka Vicente
 */
public class RadioTagHDIV extends RadioTag {

	/**
	 * Universal version identifier. Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized
	 * object.
	 */
	private static final long serialVersionUID = -3271977297872192976L;

	private static final Logger log = LoggerFactory.getLogger(RadioTagHDIV.class);

	/**
	 * Renders an HTML &lt;input type="radio"&gt; element.
	 * @param serverValue The data to be used in the tag's <code>value</code> attribute and sent to the server when the form is submitted.
	 * @param checkedValue If the serverValue equals this value the radio button will be checked.
	 * @return A radio input element.
	 * @throws JspException
	 * @see org.hdiv.dataComposer.IDataComposer#composeFormField(String, String, boolean, String)
	 * @since Struts 1.1
	 */
	@Override
	protected String renderRadioElement(final String serverValue, final String checkedValue) throws JspException {

		String cipheredValue = null;

		String preparedName = prepareName();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		IDataComposer dataComposer = HDIVUtil.getRequestContext(request).getDataComposer();
		cipheredValue = dataComposer != null ? dataComposer.composeFormField(preparedName, serverValue, false, null) : serverValue;

		StringBuilder results = new StringBuilder("<input type=\"radio\"");

		renderAttribute(results, "name", preparedName);
		renderAttribute(results, "accesskey", accesskey);
		renderAttribute(results, "tabindex", tabindex);
		renderAttribute(results, "value", cipheredValue);

		if (serverValue.equals(checkedValue)) {
			results.append(" checked=\"checked\"");
		}
		results.append(prepareEventHandlers());
		results.append(prepareStyles());
		results.append(getElementClose());

		return results.toString();
	}

	/**
	 * Prepares an attribute if the value is not null, appending it to the the given StringBuilder.
	 * @param handlers The StringBuilder that output will be appended to.
	 */
	protected void renderAttribute(final StringBuilder handlers, final String name, final Object value) {

		if (value != null) {
			handlers.append(" ");
			handlers.append(name);
			handlers.append("=\"");
			handlers.append(value);
			handlers.append("\"");
		}
	}

}
