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
package org.hdiv.urlProcessor;

import org.hdiv.AbstractHDIVTestCase;
import org.hdiv.context.RequestContextHolder;
import org.hdiv.dataComposer.DataComposerFactory;
import org.hdiv.dataComposer.IDataComposer;
import org.springframework.mock.web.MockHttpServletRequest;

public class AvoidValidationInUrlsWithoutParamsTest extends AbstractHDIVTestCase {

	private LinkUrlProcessor linkUrlProcessor;

	private FormUrlProcessor formUrlProcessor;

	private DataComposerFactory dataComposerFactory;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void onSetUp() throws Exception {

		linkUrlProcessor = getApplicationContext().getBean(LinkUrlProcessor.class);
		formUrlProcessor = getApplicationContext().getBean(FormUrlProcessor.class);
		dataComposerFactory = getApplicationContext().getBean(DataComposerFactory.class);
	}

	/*
	 * Link processing
	 */

	public void testProcessAction() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.FALSE);

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do";

		String result = linkUrlProcessor.processUrl(request, url);

		assertTrue(result.contains("_HDIV_STATE_"));
	}

	public void testProcessActionParams() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.FALSE);

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do?param=1";

		String result = linkUrlProcessor.processUrl(request, url);

		assertTrue(result.contains("_HDIV_STATE_"));
	}

	public void testProcessActionAvoid() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.TRUE);

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do";

		String result = linkUrlProcessor.processUrl(request, url);

		assertFalse(result.contains("_HDIV_STATE_"));
	}

	public void testProcessActionAvoidParams() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.TRUE);

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do?param=1";

		String result = linkUrlProcessor.processUrl(request, url);

		assertTrue(result.contains("_HDIV_STATE_"));
	}

	/*
	 * Form processing. AvoidValidationInUrlsWithoutParams is ignored in forms
	 */

	public void testProcessFormAction() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.FALSE);

		RequestContextHolder request = getRequestContext();
		String action = "/testAction.do";

		String result = formUrlProcessor.processUrl(request, action);

		// Post urls are not modified
		assertEquals(action, result);
	}

	public void testProcessFormParamAction() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.FALSE);

		RequestContextHolder request = getRequestContext();
		String action = "/testAction.do?param=1";

		String result = formUrlProcessor.processUrl(request, action);

		// Confidenciality
		assertEquals("/testAction.do?param=0", result);
	}

	public void testProcessActionComplete() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.TRUE);

		RequestContextHolder request = getRequestContext();

		IDataComposer dataComposer = dataComposerFactory.newInstance(request);
		request.setDataComposer(dataComposer);
		dataComposer.startPage();

		String action = "/testAction.do";

		String result = formUrlProcessor.processUrl(request, action);

		// Post urls are not modified
		assertEquals(action, result);

		String val = dataComposer.compose("param", "value", false);
		assertEquals("0", val);

		String requestId = dataComposer.endRequest();

		assertNotNull(requestId);
		assertTrue(requestId.length() > 0);
	}

	public void testAvoidValidationWithAjaxCallWithParameters() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.TRUE);

		MockHttpServletRequest request = getMockRequest();
		request.addHeader("x-requested-with", "XMLHttpRequest");
		clearAjax();

		String url = "/testAction.do?param=1";

		String result = linkUrlProcessor.processUrl(getRequestContext(), url);

		assertTrue(result.contains("_HDIV_STATE_"));

	}

	public void testAvoidValidationWithAjaxCall() {

		getConfig().setAvoidValidationInUrlsWithoutParams(Boolean.TRUE);

		MockHttpServletRequest request = getMockRequest();
		request.addHeader("x-requested-with", "XMLHttpRequest");
		clearAjax();

		String url = "/testAction.do";

		String result = linkUrlProcessor.processUrl(getRequestContext(), url);

		assertTrue(!result.contains("_HDIV_STATE_"));
	}

}
