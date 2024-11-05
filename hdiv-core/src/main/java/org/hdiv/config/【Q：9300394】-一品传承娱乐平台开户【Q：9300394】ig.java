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
package org.hdiv.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hdiv.context.RequestContextHolder;
import org.hdiv.regex.PatternMatcher;
import org.hdiv.regex.PatternMatcherFactory;
import org.hdiv.state.IPage;
import org.hdiv.state.scope.StateScopeType;
import org.hdiv.util.Method;
import org.hdiv.validator.EditableDataValidationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class containing HDIV configuration initialized from Spring Factory.
 * 
 * @author Roberto Velasco
 * @author Gorka Vicente
 * @author Gotzon Illarramendi
 */
public class HDIVConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(HDIVConfig.class);

	public static final String DEFAULT_STATE_PARAMETER_NAME = "_HDIV_STATE_";

	public static final String DEFAULT_MODIFY_STATE_PARAMETER_NAME = "_MODIFY_HDIV_STATE_";

	/**
	 * Regular expression executor factory.
	 * 
	 * @since 2.1.6
	 */
	protected transient PatternMatcherFactory patternMatcherFactory;

	/**
	 * List with the pages that will not be Treated by the HDIV filter. The init pages are initialized by the Spring factory.
	 */
	protected StartPage[] startPages = new StartPage[0];

	/**
	 * List with the parameters that will not be validated by the HDIV filter. The init parameters are initialized by the Spring factory.
	 */
	protected List<PatternMatcher> startParameters = new ArrayList<PatternMatcher>();

	/**
	 * Url of the error page to which HDIV will redirect the request if it doesn't pass the HDIV validation.
	 */
	protected String errorPage;

	/**
	 * Url of the error page to which HDIV will redirect the request if it doesn't pass the HDIV validation caused by session expiration and
	 * the user is not logged in the application.
	 */
	protected String sessionExpiredLoginPage;

	/**
	 * Url of the error page to which HDIV will redirect the request if it doesn't pass the HDIV validation caused by session expiration and
	 * the user is logged in the application.
	 */
	protected String sessionExpiredHomePage;

	/**
	 * Confidentiality indicator to know if information is accessible only for those who are authorized.
	 */
	protected boolean confidentiality = true;

	/**
	 * Pentesting active
	 */
	private boolean pentestingActive = false;

	/**
	 * Parameters which HDIV validation will not be applied to.
	 */
	protected Map<PatternMatcher, List<PatternMatcher>> paramsWithoutValidation;

	/**
	 * Validation provider for editable fields (text/textarea).
	 */
	protected EditableDataValidationProvider editableDataValidationProvider;

	/**
	 * If <code>avoidCookiesIntegrity</code> is true, cookie integrity will not be applied.
	 */
	protected boolean avoidCookiesIntegrity = false;

	/**
	 * If <code>avoidCookiesConfidentiality</code> is true, cookie confidentiality will not be applied.
	 */
	protected boolean avoidCookiesConfidentiality = false;

	/**
	 * if <code>avoidValidationInUrlsWithoutParams</code> is true, HDIV validation will not be applied in urls without parameters.
	 * 
	 * @since HDIV 2.1.0
	 */
	protected boolean avoidValidationInUrlsWithoutParams = false;

	/**
	 * Extensions that we have to protect with HDIV's state.
	 * 
	 * @since HDIV 2.0
	 */
	protected List<PatternMatcher> protectedURLPatterns;

	/**
	 * Extensions that we have not to protect with HDIV's state.
	 * 
	 * @since HDIV 2.1.0
	 */
	protected List<String> excludedURLExtensions;

	/**
	 * Show error page on request with editable validation errors.
	 * 
	 * @since 2.1.4
	 */
	protected boolean showErrorPageOnEditableValidation = false;

	/**
	 * Reuse previous {@link IPage} when an AJAX request is received and don't create a new one.
	 * 
	 * @since 2.1.7
	 */
	protected boolean reuseExistingPageInAjaxRequest = false;

	/**
	 * Pages whose link and forms never expire.
	 * 
	 * @since 2.1.7
	 */
	protected Map<PatternMatcher, String> longLivingPages = new HashMap<PatternMatcher, String>();

	/**
	 * True if URLs should be obfuscated
	 */
	private boolean urlObfuscation = false;

	private boolean integrityValidation = true;

	private boolean editableValidation = true;

	/**
	 * Are editable form fields required?
	 */
	protected boolean editableFieldsRequiredByDefault = false;

	/**
	 * Enable or disable multipart request processing and protection in Hdiv.
	 * @since 3.3.15
	 */
	private boolean multipartIntegration = true;

	@Deprecated
	public void setStrategy(final Strategy strategy) {
	}

	/**
	 * Checks if <code>parameter</code> is an init parameter, in which case it will not be treated by HDIV.
	 * 
	 * @param parameter Parameter name
	 * @return True if <code>parameter</code> is an init parameter. False otherwise.
	 */
	public boolean isStartParameter(final String parameter) {

		for (PatternMatcher matcher : startParameters) {
			if (matcher.matches(parameter)) {
				return true;
			}
		}
		return false;
	}

	private void addStartPage(final StartPage startPage) {

		if (log.isDebugEnabled()) {
			log.debug("Added a StartPage: " + startPage);
		}

		List<StartPage> pages = new ArrayList<StartPage>(Arrays.asList(startPages));
		pages.add(startPage);
		startPages = pages.toArray(new StartPage[pages.size()]);
	}

	/**
	 * Checks if <code>target</code> is an init action, in which case it will not be treated by HDIV.
	 * 
	 * @param target target name
	 * @param method request method (get,post...)
	 * @return True if <code>target</code> is an init action. False otherwise.
	 */
	public boolean isStartPage(final String target, final Method method) {
		return getStartPage(target, method) != null;
	}

	public StartPage getStartPage(final String target, final Method method) {
		for (int i = 0; i < startPages.length; i++) {
			StartPage startPage = startPages[i];
			PatternMatcher m = startPage.compiledPattern;

			if (m.matches(target) && (startPage.isAnyMethod() || startPage.method == method)) {
				return startPage;
			}
		}
		return null;
	}

	public boolean hasExtensionToExclude(String path) {

		if (excludedURLExtensions == null) {
			return false;
		}
		int pos = path.indexOf('?');
		if (pos > 0) {
			path = path.substring(0, pos);
		}

		if (path.length() == 0) {
			return false;
		}

		if (path.charAt(path.length() - 1) == '/') {
			return false;
		}

		int pound = path.indexOf('#');
		if (pound >= 0) {
			path = path.substring(0, pound);
		}

		int size = excludedURLExtensions.size();
		for (int i = 0; i < size; i++) {
			if (path.endsWith(excludedURLExtensions.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the parameter needs confidentiality.
	 * 
	 * @param context request context
	 * @param paramName parameter name to check
	 * @return boolean
	 */
	public boolean isParameterWithoutConfidentiality(final RequestContextHolder context, final String paramName) {

		String modifyHdivStateParameterName = context.getHdivModifyParameterName();
		if (modifyHdivStateParameterName != null && modifyHdivStateParameterName.equals(paramName)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the parameter <code>parameter</code> is defined by the user as a no required validation parameter for the action
	 * <code>action</code>.
	 * 
	 * @param action action name
	 * @param parameter parameter name
	 * @return True if it is parameter that needs no validation. False otherwise.
	 */
	public boolean isParameterWithoutValidation(final String action, final String parameter) {

		if (action == null) {
			return false;
		}

		if (paramsWithoutValidation == null) {
			return false;
		}

		for (Entry<PatternMatcher, List<PatternMatcher>> entry : paramsWithoutValidation.entrySet()) {

			if (entry.getKey().matches(action)) {

				for (PatternMatcher paramMatcher : entry.getValue()) {

					if (paramMatcher.matches(parameter)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Calculates if the provided url path is configured as a long-living pages.
	 * 
	 * @param url url path
	 * @return Scope name or null if it is not a long-living page
	 */
	public StateScopeType isLongLivingPages(final String url) {

		for (Map.Entry<PatternMatcher, String> page : longLivingPages.entrySet()) {

			PatternMatcher m = page.getKey();

			if (m.matches(url)) {
				return StateScopeType.byName(page.getValue());
			}
		}
		return null;
	}

	/**
	 * Checks if the HDIV validation must be applied to the parameter <code>parameter</code>
	 * 
	 * @param parameter Parameter name
	 * @param hdivParameter Name of the parameter that HDIV will include in the requests or/and forms which contains the state identifier
	 * parameter
	 * @return True if <code>parameter</code> doesn't need HDIV validation.
	 */
	public boolean needValidation(final String parameter, final String hdivParameter) {

		if (parameter.equals(hdivParameter) || isStartParameter(parameter)) {
			return false;
		}
		return true;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {

		if (errorPage != null && !errorPage.startsWith("/")) {
			errorPage = "/" + errorPage;
		}
		this.errorPage = errorPage;
		if (errorPage != null) {
			PatternMatcher matcher = patternMatcherFactory.getPatternMatcher(errorPage);
			addStartPage(new StartPage((Method) null, matcher));
		}
	}

	public String getSessionExpiredLoginPage() {
		return sessionExpiredLoginPage;
	}

	public void setSessionExpiredLoginPage(String sessionExpiredLoginPage) {
		if (sessionExpiredLoginPage != null && !sessionExpiredLoginPage.startsWith("/")) {
			sessionExpiredLoginPage = "/" + sessionExpiredLoginPage;
		}
		this.sessionExpiredLoginPage = sessionExpiredLoginPage;
		if (sessionExpiredLoginPage != null) {
			PatternMatcher matcher = patternMatcherFactory.getPatternMatcher(sessionExpiredLoginPage);
			addStartPage(new StartPage((Method) null, matcher));
		}
	}

	public String getSessionExpiredHomePage() {
		return sessionExpiredHomePage;
	}

	public void setSessionExpiredHomePage(String sessionExpiredHomePage) {
		if (sessionExpiredHomePage != null && !sessionExpiredHomePage.startsWith("/")) {
			sessionExpiredHomePage = "/" + sessionExpiredHomePage;
		}
		this.sessionExpiredHomePage = sessionExpiredHomePage;
		if (sessionExpiredHomePage != null) {
			PatternMatcher matcher = patternMatcherFactory.getPatternMatcher(sessionExpiredHomePage);
			addStartPage(new StartPage((Method) null, matcher));
		}
	}

	public boolean getConfidentiality() {
		return confidentiality;
	}

	public void setConfidentiality(final boolean confidentiality) {
		this.confidentiality = confidentiality;
	}

	public void setParamsWithoutValidation(final Map<String, List<String>> paramsWithoutValidation) {
		this.paramsWithoutValidation = new HashMap<PatternMatcher, List<PatternMatcher>>();
		for (Entry<String, List<String>> entry : paramsWithoutValidation.entrySet()) {

			PatternMatcher matcher = patternMatcherFactory.getPatternMatcher(entry.getKey());
			List<PatternMatcher> paramMatchers = this.paramsWithoutValidation.get(matcher);
			if (paramMatchers == null) {
				paramMatchers = new ArrayList<PatternMatcher>();
				this.paramsWithoutValidation.put(matcher, paramMatchers);
			}
			for (String param : entry.getValue()) {
				PatternMatcher paramMatcher = patternMatcherFactory.getPatternMatcher(param);
				paramMatchers.add(paramMatcher);
			}
		}
	}

	/**
	 * It creates a map from the list of start pages defined by the user.
	 * 
	 * @param userStartPages list of start pages defined by the user
	 */
	public void setUserStartPages(final List<StartPage> userStartPages) {

		for (StartPage startPage : userStartPages) {

			PatternMatcher matcher = patternMatcherFactory.getPatternMatcher(startPage.getPattern());
			startPage.setCompiledPattern(matcher);
			addStartPage(startPage);

		}
	}

	/**
	 * It creates a map from the list of init parameters defined by the user.
	 * 
	 * @param userStartParameters list of init parameters defined by the user
	 */
	public void setUserStartParameters(final List<String> userStartParameters) {

		for (String useStartParameter : userStartParameters) {
			startParameters.add(patternMatcherFactory.getPatternMatcher(useStartParameter));
		}
	}

	/**
	 * @return Returns true if cookies' confidentiality is activated.
	 */
	public boolean isCookiesConfidentialityActivated() {
		return !avoidCookiesConfidentiality;
	}

	/**
	 * @param avoidCookiesConfidentiality the avoidCookiesConfidentiality to set
	 */
	public void setAvoidCookiesConfidentiality(final boolean avoidCookiesConfidentiality) {
		this.avoidCookiesConfidentiality = avoidCookiesConfidentiality;
	}

	/**
	 * @return Returns true if cookies' integrity is activated.
	 */
	public boolean isCookiesIntegrityActivated() {
		return !avoidCookiesIntegrity;
	}

	/**
	 * @param avoidCookiesIntegrity the avoidCookiesIntegrity to set
	 */
	public void setAvoidCookiesIntegrity(final boolean avoidCookiesIntegrity) {
		this.avoidCookiesIntegrity = avoidCookiesIntegrity;
	}

	/**
	 * @return Returns true if validation in urls without parameters is activated.
	 */
	public boolean isValidationInUrlsWithoutParamsActivated() {
		return !avoidValidationInUrlsWithoutParams;
	}

	/**
	 * @param avoidValidationInUrlsWithoutParams The avoidValidationInUrlsWithoutParams to set.
	 */
	public void setAvoidValidationInUrlsWithoutParams(final Boolean avoidValidationInUrlsWithoutParams) {
		this.avoidValidationInUrlsWithoutParams = avoidValidationInUrlsWithoutParams.booleanValue();
	}

	/**
	 * @param protectedExtensions The protected extensions to set.
	 * @since HDIV 2.0
	 */
	public void setProtectedExtensions(final List<String> protectedExtensions) {

		protectedURLPatterns = new ArrayList<PatternMatcher>();

		for (String protectedExtension : protectedExtensions) {
			protectedURLPatterns.add(patternMatcherFactory.getPatternMatcher(protectedExtension));
		}
	}

	public void setExcludedExtensions(final List<String> excludedExtensions) {
		if (excludedURLExtensions == null) {
			excludedURLExtensions = new ArrayList<String>();
		}
		excludedURLExtensions.addAll(excludedExtensions);
	}

	/**
	 * @return Returns the protected extensions.
	 * @since HDIV 2.0
	 */
	public List<PatternMatcher> getProtectedURLPatterns() {
		return protectedURLPatterns;
	}

	/**
	 * @return Returns the excluded extensions.
	 * @since HDIV 2.1.0
	 */
	public List<String> getExcludedURLExtensions() {
		return excludedURLExtensions;
	}

	/**
	 * @return the randomName
	 */
	public boolean isRandomName() {
		return false;
	}

	/**
	 * @param randomName the randomName to set
	 */
	@Deprecated
	public void setRandomName(final boolean randomName) {
		if (randomName) {
			noLongerSupportedDisclaimer("RandomName");
		}
	}

	@Deprecated
	public Strategy getStrategy() {
		return Strategy.MEMORY;
	}

	/**
	 * @return the debugMode
	 */
	@Deprecated
	public boolean isDebugMode() {
		return !isIntegrityValidation();
	}

	/**
	 * @param debugMode the debugMode to set
	 */
	public void setDebugMode(final boolean debugMode) {
		setEditableValidation(!debugMode);
		setIntegrityValidation(!debugMode);
	}

	/**
	 * @return the showErrorPageOnEditableValidation
	 */
	public boolean isShowErrorPageOnEditableValidation() {
		return showErrorPageOnEditableValidation;
	}

	/**
	 * @param showErrorPageOnEditableValidation the showErrorPageOnEditableValidation to set
	 */
	public void setShowErrorPageOnEditableValidation(final boolean showErrorPageOnEditableValidation) {
		this.showErrorPageOnEditableValidation = showErrorPageOnEditableValidation;
	}

	/**
	 * @return the reuseExistingPageInAjaxRequest
	 */
	public boolean isReuseExistingPageInAjaxRequest() {
		return reuseExistingPageInAjaxRequest;
	}

	/**
	 * @param reuseExistingPageInAjaxRequest the reuseExistingPageInAjaxRequest to set
	 */
	public void setReuseExistingPageInAjaxRequest(final boolean reuseExistingPageInAjaxRequest) {
		this.reuseExistingPageInAjaxRequest = reuseExistingPageInAjaxRequest;
	}

	/**
	 * @param patternMatcherFactory the patternMatcherFactory to set
	 */
	public void setPatternMatcherFactory(final PatternMatcherFactory patternMatcherFactory) {
		this.patternMatcherFactory = patternMatcherFactory;
	}

	/**
	 * @return the stateParameterName
	 */
	public String getStateParameterName() {
		return DEFAULT_STATE_PARAMETER_NAME;
	}

	/**
	 * @param stateParameterName the stateParameterName to set
	 */
	@Deprecated
	public void setStateParameterName(final String stateParameterName) {
		if (!stateParameterName.equals(DEFAULT_STATE_PARAMETER_NAME)) {
			noLongerSupportedDisclaimer("StateParameterName");
		}
	}

	/**
	 * @return the modifyStateParameterName
	 */
	public String getModifyStateParameterName() {
		return DEFAULT_MODIFY_STATE_PARAMETER_NAME;
	}

	/**
	 * @param modifyStateParameterName the modifyStateParameterName to set
	 */
	@Deprecated
	public void setModifyStateParameterName(final String modifyStateParameterName) {
		if (!modifyStateParameterName.equals(DEFAULT_MODIFY_STATE_PARAMETER_NAME)) {
			noLongerSupportedDisclaimer("ModifyStateParameterName");
		}
	}

	/**
	 * @param longLivingPages the longLivingPages to set
	 */
	public void setLongLivingPages(final Map<String, String> longLivingPages) {

		for (Map.Entry<String, String> page : longLivingPages.entrySet()) {
			PatternMatcher pattern = patternMatcherFactory.getPatternMatcher(page.getKey());
			String scope = page.getValue();
			this.longLivingPages.put(pattern, scope);
		}
	}

	/**
	 * @param editableDataValidationProvider the editableDataValidationProvider to set
	 */
	public void setEditableDataValidationProvider(final EditableDataValidationProvider editableDataValidationProvider) {
		this.editableDataValidationProvider = editableDataValidationProvider;
	}

	/**
	 * @return the editableDataValidationProvider
	 */
	public EditableDataValidationProvider getEditableDataValidationProvider() {
		return editableDataValidationProvider;
	}

	public boolean isUrlObfuscation() {
		return urlObfuscation;
	}

	public void setUrlObfuscation(final boolean urlObfuscation) {
		this.urlObfuscation = urlObfuscation;
	}

	public boolean getEditableFieldsRequiredByDefault() {
		return editableFieldsRequiredByDefault;
	}

	public void setEditableFieldsRequiredByDefault(final boolean editableFieldsRequiredByDefault) {
		this.editableFieldsRequiredByDefault = editableFieldsRequiredByDefault;
	}

	public boolean isMultipartIntegration() {
		return multipartIntegration;
	}

	public void setMultipartIntegration(final boolean multipartIntegration) {
		this.multipartIntegration = multipartIntegration;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder().append("");
		result = result.append(" Confidentiality=").append(getConfidentiality());
		result.append(" avoidCookiesIntegrity=").append(avoidCookiesIntegrity);
		result.append(" avoidCookiesConfidentiality=").append(avoidCookiesConfidentiality);
		result.append(" avoidValidationInUrlsWithoutParams=").append(avoidValidationInUrlsWithoutParams);
		result.append(" strategy=").append(getStrategy());
		result.append(" randomName=").append(isRandomName());
		result.append(" errorPage=").append(getErrorPage());
		result.append(" sessionExpiredLoginPage=").append(sessionExpiredLoginPage);
		result.append(" sessionExpiredHomePage=").append(sessionExpiredHomePage);
		result.append(" excludedExtensions=").append(excludedURLExtensions);
		result.append(" protectedExtensions=").append(getProtectedURLPatterns());
		result.append(" startPages=").append(startPages);
		result.append(" startParameters=").append(startParameters);
		result.append(" paramsWithoutValidation=").append(paramsWithoutValidation);
		result.append(" longLivingPages=").append(longLivingPages);
		result.append(" debugMode=").append(isDebugMode());
		result.append(" showErrorPageOnEditableValidation=").append(showErrorPageOnEditableValidation);
		result.append(" multipartIntegration=").append(multipartIntegration);

		return result.toString();
	}

	private void noLongerSupportedDisclaimer(final String feature) {
		System.err.println("**************************************************************");
		System.err.println("*                                                            *");
		System.err.println("*       This feature is no longer supported under Hdiv CE    *");
		System.err.println("*                                                            *");
		System.err.println("**************************************************************");
		System.err.println("Feature:" + feature);
	}

	public boolean isIntegrityValidation() {
		return integrityValidation;
	}

	public boolean isEditableValidation() {
		return editableValidation;
	}

	public void setIntegrityValidation(final boolean integrityValidation) {
		this.integrityValidation = integrityValidation;
	}

	public void setEditableValidation(final boolean editableValidation) {
		this.editableValidation = editableValidation;
	}

	public boolean isPentestingActive() {
		return pentestingActive;
	}

	public void setPentestingActive(final boolean pentestingActive) {
		this.pentestingActive = pentestingActive;
	}
}
