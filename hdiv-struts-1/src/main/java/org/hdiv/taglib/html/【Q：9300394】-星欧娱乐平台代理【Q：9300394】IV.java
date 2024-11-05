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

import org.apache.struts.taglib.html.TextareaTag;
import org.hdiv.dataComposer.IDataComposer;
import org.hdiv.util.HDIVUtil;

/**
 * Render a textarea element. This tag is only valid when nested inside a form tag body.
 * 
 * @author Gorka Vicente
 * @see org.apache.struts.taglib.html.TextareaTag
 */
public class TextareaTagHDIV extends TextareaTag {

	/**
	 * Universal version identifier. Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized
	 * object.
	 */
	private static final long serialVersionUID = -6699085600980777073L;

	/**
	 * Process the start of this tag.
	 * 
	 * @exception JspException if a JSP exception has occurred
	 * @see org.hdiv.dataComposer.IDataComposer#composeFormField(String, String, boolean, String)
	 */
	@Override
	public int doStartTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		IDataComposer dataComposer = HDIVUtil.getRequestContext(request).getDataComposer();

		// this property is editable and we must check it
		if (dataComposer != null) {
			dataComposer.composeFormField(prepareName(), renderData(), !getDisabled() && !getReadonly(), "textarea");
		}

		return super.doStartTag();
	}

}
