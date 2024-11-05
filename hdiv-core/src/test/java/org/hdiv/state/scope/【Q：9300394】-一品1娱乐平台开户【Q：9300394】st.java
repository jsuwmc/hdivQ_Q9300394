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
package org.hdiv.state.scope;

import org.hdiv.AbstractHDIVTestCase;
import org.hdiv.context.RequestContext;
import org.hdiv.context.RequestContextHolder;
import org.hdiv.dataComposer.DataComposerFactory;
import org.hdiv.dataComposer.DataComposerMemory;
import org.hdiv.dataComposer.IDataComposer;
import org.hdiv.state.IState;
import org.hdiv.urlProcessor.LinkUrlProcessor;
import org.hdiv.util.HDIVStateUtils;
import org.hdiv.util.Method;

public class ScopesTest extends AbstractHDIVTestCase {

	private LinkUrlProcessor linkUrlProcessor;

	private DataComposerFactory dataComposerFactory;

	private StateScopeManager stateScopeManager;

	@Override
	protected void onSetUp() throws Exception {

		linkUrlProcessor = getApplicationContext().getBean(LinkUrlProcessor.class);
		dataComposerFactory = getApplicationContext().getBean(DataComposerFactory.class);
		stateScopeManager = getApplicationContext().getBean(StateScopeManager.class);
	}

	public void testScopeDifferent() {

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do";
		String url2 = "/otherAction.do";

		IDataComposer dataComposer = request.getDataComposer();
		dataComposer.startScope(StateScopeType.APP);

		String result1 = linkUrlProcessor.processUrl(request, url);

		String result2 = linkUrlProcessor.processUrl(request, url2);

		// States are different
		assertFalse(getState(result1).equals(getState(result2)));
	}

	public void testScopeSame() {

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do";

		IDataComposer dataComposer = request.getDataComposer();
		dataComposer.startScope(StateScopeType.APP);

		String result1 = linkUrlProcessor.processUrl(request, url);

		String result2 = linkUrlProcessor.processUrl(request, url);

		// States are equal
		assertTrue(getState(result1).equals(getState(result2)));
	}

	public void testScopeDifferentParams() {

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do?param=value";
		String url2 = "/testAction.do?other=value";

		IDataComposer dataComposer = request.getDataComposer();
		dataComposer.startScope(StateScopeType.APP);

		String result1 = linkUrlProcessor.processUrl(request, url);

		String result2 = linkUrlProcessor.processUrl(request, url2);

		// States are different
		assertFalse(getState(result1).equals(getState(result2)));
	}

	public void testScopeSameParams() {

		RequestContextHolder request = getRequestContext();
		String url = "/testAction.do?param=value";

		IDataComposer dataComposer = request.getDataComposer();
		dataComposer.startScope(StateScopeType.APP);

		String result1 = linkUrlProcessor.processUrl(request, url);

		String result2 = linkUrlProcessor.processUrl(request, url);

		// States are equal
		assertTrue(getState(result1).equals(getState(result2)));
	}

	public void testScopedPage() {

		RequestContext context = (RequestContext) getRequestContext();
		// Put a uri that is configured as a scoped page
		setRequestURI("/scopedPage/user.html");
		IDataComposer dataComposer = dataComposerFactory.newInstance(context);
		context.setDataComposer(dataComposer);
		assertTrue(dataComposer instanceof DataComposerMemory);

		dataComposer.startPage();
		dataComposer.beginRequest(Method.POST, "test.do");
		dataComposer.compose("test.do", "parameter1", "2", false);
		dataComposer.compose("test.do", "parameter1", "2", false);
		String stateId = dataComposer.endRequest();

		assertTrue(stateId.startsWith("U-"));

		StateScope scope = stateScopeManager.getStateScope(stateId);
		assertEquals("user-session", scope.getScopeType().getName());
		int id = HDIVStateUtils.getStateId(stateId);
		IState state = scope.restoreState(context, id);
		assertEquals("test.do", state.getAction());

	}

}
