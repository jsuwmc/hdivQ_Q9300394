/**
 * Copyright 2005-2013 hdiv.org
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

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.hdiv.AbstractHDIVTestCase;
import org.hdiv.util.HDIVErrorCodes;
import org.hdiv.util.HDIVUtil;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

public class ValidatorErrorHandlerTest extends AbstractHDIVTestCase {

	private ValidatorErrorHandler validatorErrorHandler;

	protected void onSetUp() throws Exception {
		this.validatorErrorHandler = this.getApplicationContext().getBean(ValidatorErrorHandler.class);
	}

	public void testPageIncorrect() {

		HttpServletRequest request = HDIVUtil.getHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		this.validatorErrorHandler.handleValidatorError(request, response, HDIVErrorCodes.PAGE_ID_INCORRECT);

		String redirectUrl = response.getRedirectedUrl();

		assertEquals(getConfig().getSessionExpiredLoginPage(), redirectUrl);
	}

	public void testHandleValidatorError() {

		HttpServletRequest request = HDIVUtil.getHttpServletRequest();
		MockHttpSession session = (MockHttpSession) request.getSession();
		session.setNew(false); // mark as not new sesssion
		MockHttpServletResponse response = new MockHttpServletResponse();

		this.validatorErrorHandler.handleValidatorError(request, response, HDIVErrorCodes.REQUIRED_PARAMETERS);

		String redirectUrl = response.getRedirectedUrl();

		assertEquals(getConfig().getErrorPage(), redirectUrl);
	}

	public void testDefaultErrorPage() {

		// Remove default errorPage
		getConfig().setErrorPage(null);

		HttpServletRequest request = HDIVUtil.getHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		this.validatorErrorHandler.handleValidatorError(request, response, HDIVErrorCodes.REQUIRED_PARAMETERS);

		// Default Error page is generated, so no redirect URL exist
		assertNull(response.getRedirectedUrl());

		assertTrue(response.getBufferSize() > 0);

		String responseContent = null;
		try {
			responseContent = response.getContentAsString();
		} catch (UnsupportedEncodingException e) {
			responseContent = null;
		}
		assertNotNull(responseContent);
		assertTrue(responseContent.contains("Unauthorized access"));

	}

}
