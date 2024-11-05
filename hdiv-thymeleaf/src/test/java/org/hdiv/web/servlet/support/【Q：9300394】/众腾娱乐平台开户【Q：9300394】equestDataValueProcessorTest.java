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
package org.hdiv.web.servlet.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hdiv.AbstractHDIVTestCase;
import org.hdiv.context.RequestContextHolder;
import org.hdiv.dataComposer.DataComposerFactory;
import org.hdiv.dataComposer.IDataComposer;
import org.hdiv.state.IParameter;
import org.hdiv.state.IState;
import org.hdiv.state.StateUtil;
import org.hdiv.util.HDIVUtil;

public class ThymeleafHdivRequestDataValueProcessorTest extends AbstractHDIVTestCase {

	private ThymeleafHdivRequestDataValueProcessor dataValueProcessor;

	private DataComposerFactory dataComposerFactory;

	private StateUtil stateUtil;

	@Override
	protected void onSetUp() throws Exception {

		dataValueProcessor = getApplicationContext().getBean(ThymeleafHdivRequestDataValueProcessor.class);
		dataComposerFactory = getApplicationContext().getBean(DataComposerFactory.class);
		stateUtil = getApplicationContext().getBean(StateUtil.class);
	}

	public void testProcessUrl() {

		RequestContextHolder context = getRequestContext();
		String url = "/testAction.do";

		String result = dataValueProcessor.processUrl(context, url);
		assertTrue(result.contains("_HDIV_STATE_"));

	}

	public void testProcessUrlAvoid() {

		getConfig().setAvoidValidationInUrlsWithoutParams(true);

		RequestContextHolder context = getRequestContext();
		String url = "/testAction.do";

		String result = dataValueProcessor.processUrl(context, url);
		assertEquals(url, result);

	}

	public void testProcessAction() {

		HttpServletRequest request = getMockRequest();
		RequestContextHolder context = getRequestContext();
		String action = "/testAction.do";

		String result = dataValueProcessor.processAction(request, action);
		// Post urls are not modified
		assertEquals(action, result);

		String val = dataValueProcessor.processFormFieldValue(context, "param", "value", "select");
		assertEquals("0", val);

		Map<String, String> extraParams = dataValueProcessor.getExtraHiddenFields(context);

		assertNotNull(extraParams);
		assertTrue(extraParams.size() > 0);
	}

	public void testProcessActionGetMethod() {

		RequestContextHolder context = getRequestContext();
		String action = "/onlyget.do"; // Is startPage only for get

		String result = dataValueProcessor.processAction(context, action, "GET");
		// Post urls are not modified
		assertEquals(action, result);

		String val = dataValueProcessor.processFormFieldValue(context, "param", "value", "select");
		assertEquals("value", val);

		Map<String, String> extraParams = dataValueProcessor.getExtraHiddenFields(context);

		assertNotNull(extraParams);
		assertTrue(extraParams.size() == 0);
	}

	public void testProcessActionAvoid() {

		getConfig().setAvoidValidationInUrlsWithoutParams(true);

		HttpServletRequest request = getMockRequest();
		RequestContextHolder context = getRequestContext();
		String action = "/testAction.do";

		String result = dataValueProcessor.processAction(request, action);
		// Post urls are not modified
		assertEquals(action, result);

		String val = dataValueProcessor.processFormFieldValue(context, "param", "value", "select");
		assertEquals("0", val);

		Map<String, String> extraParams = dataValueProcessor.getExtraHiddenFields(context);

		assertNotNull(extraParams);
		assertTrue(extraParams.size() > 0);
	}

	public void testProcessFormThymeleafOrder() {

		HttpServletRequest request = getMockRequest();
		RequestContextHolder context = getRequestContext();
		IDataComposer dataComposer = dataComposerFactory.newInstance(context);
		HDIVUtil.setDataComposer(dataComposer, request);

		dataComposer.startPage();

		String action = "/testAction.do";

		// 1. the action url
		String result = dataValueProcessor.processAction(request, action);
		// Post urls are not modified
		assertEquals(action, result);

		// 2. Hidden field
		Map<String, String> extraParams = dataValueProcessor.getExtraHiddenFields(context);

		assertNotNull(extraParams);
		assertTrue(extraParams.size() == 1);
		String hdivStateParam = HDIVUtil.getHdivStateParameterName(request);
		String stateValue = extraParams.get(hdivStateParam);
		assertNotNull(stateValue);

		// 3. form parameters
		String val = dataValueProcessor.processFormFieldValue(context, "param", "value", "select");
		assertEquals("0", val);

		val = dataValueProcessor.processFormFieldValue(context, "param1", "value1", "text");
		assertEquals("value1", val);

		dataComposer.endPage();

		// Restore state
		IState state = stateUtil.restoreState(context, stateValue);
		assertNotNull(state);

		IParameter param = state.getParameter("param");
		List<String> values = param.getValues();
		assertTrue(values.size() == 1);

		String value = values.get(0);
		assertEquals("value", value);

	}
}
