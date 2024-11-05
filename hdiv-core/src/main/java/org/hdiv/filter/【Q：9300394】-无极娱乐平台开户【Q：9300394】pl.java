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

import java.io.UnsupportedEncodingException;

import org.hdiv.context.RequestContextHolder;
import org.hdiv.exception.HDIVException;
import org.hdiv.state.IState;
import org.hdiv.util.Constants;
import org.hdiv.util.HDIVErrorCodes;
import org.hdiv.util.HDIVUtil;
import org.hdiv.util.Method;

public class ValidationContextImpl implements ValidationContext {

	private final boolean obfuscation;

	private final StateRestorer restorer;

	private final StringBuilder sb = new StringBuilder(128);

	protected String target;

	protected String redirect;

	private final Method method;

	protected final RequestContextHolder context;

	public ValidationContextImpl(final RequestContextHolder context, final StateRestorer restorer, final boolean obfuscation) {
		this.context = context;
		this.obfuscation = obfuscation;
		this.restorer = restorer;
		method = Method.secureValueOf(context.getMethod());
	}

	public String getTarget() {
		if (target == null) {
			String target = getRequestedTarget();
			if (obfuscation && HDIVUtil.isObfuscatedTarget(target)) {

				// Restore state from request or memory
				IState state = restoreState();
				if (state != null) {
					this.target = state.getAction();
					redirect = this.target;
					context.setRedirectAction(redirect);
				}
				if (redirect == null) {
					throw new HDIVException(HDIVErrorCodes.INVALID_HDIV_PARAMETER_VALUE);
				}
			}
			if (this.target == null) {
				this.target = target;
			}
		}
		return target;
	}

	protected IState restoreState() {
		ValidatorHelperResult result = restorer.restoreState(this);
		if (result.isValid()) {
			return result.getValue();
		}
		return null;
	}

	public StringBuilder getBuffer() {
		return sb;
	}

	public String getRedirect() {
		return redirect;
	}

	protected StateRestorer getRestorer() {
		return restorer;
	}

	private final String getDecodedTarget(final StringBuilder sb, final RequestContextHolder request) {
		/**
		 * Remove contest path and session info first
		 */
		return decodeUrl(sb, HDIVUtil.stripSession(request.getUrlWithoutContextPath()));
	}

	/**
	 * It decodes the url to replace the character represented by percentage with its equivalent.
	 *
	 * @param url url to decode
	 * @return decoder url
	 */
	private String decodeUrl(final StringBuilder sb, final String url) {
		try {
			return HDIVUtil.decodeValue(sb, url, Constants.ENCODING_UTF_8);
		}
		catch (final UnsupportedEncodingException e) {
			throw new HDIVException("Error decoding url", e);
		}
		catch (final IllegalArgumentException e) {
			throw new HDIVException("Error decoding url", e);
		}
	}

	public Method getMethod() {
		return method;
	}

	public String getRequestedTarget() {
		return getDecodedTarget(sb, context);
	}

	public RequestContextHolder getRequestContext() {
		return context;
	}

}
