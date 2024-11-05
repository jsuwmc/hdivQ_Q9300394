# RELEASE NOTES

## HDIV CHANGELOG
https://github.com/hdiv

###3.5.1

 * [NEW] Remove spring-hateaos dependency

###3.5.0

 * [NEW] Improvements in the hdiv-services module for a better technology support

###3.4.12

 * [FIX] Execute filter in ERROR DispatchType requests

###3.4.11

 * [FIX] Recover deprecated methods

###3.4.10

 * [FIX] Urls with _HDIV_STATE_ parameter

###3.4.9

 * [FIX] Make ValidatorError serializable

###3.4.8

 * [FIX] UICommand clicked detection minor fix

###3.4.7

 * [NEW] Minor changes in hdiv-jsf

###3.4.6

 * [NEW] Minor changes in hdiv-jsf

###3.4.5

 * [NEW] Improvements in client-side generated cookies validation
 * [FIX] Fix same parameter in action and field
 * [FIX] Fix required parameter configured as start parameter

###3.4.4

 * [NEW] Tag attribute holder in RequestContext

###3.4.3

 * [BUG] Fix loop

###3.4.1

 * [NEW] Initialize ‘formStateId’ attribute with ModifyHdivState state value

###3.4.0

 * [NEW] New 'editableFieldsRequiredByDefault' configuration option.

###3.3.17

 * [BUG] Fix loop

###3.3.16

 * [NEW] Initialize ‘formStateId’ attribute with ModifyHdivState state value

###3.3.15

 * [NEW] Configuration option to disable multipart integration
 * [NEW] Prevent exclusion overriding

###3.3.13

 * [NEW] Include cookie original value in attack detail.
 * [NEW] Include parameter value in new parameter attack detail.
 
###3.3.11

 * [BUG] Spring MVC, support no RequestContext in request.

###3.3.10

 * Fix encoding error in Websphere.

###3.3.9

 * Struts1: Don’t process url if requestContext is null.

###3.3.8

 * @Since and @DeprecatedSince annotations to enable Page and State versioning.
 * Protect access to invalidated session attributes. 

###3.3.7

 * Protect internal request attributes deletion or modification. 

###3.3.6

 * System property to disable request clean in async requests 

###3.3.5

 * Protect Struts Tags extension from null IDataComposer

###3.3.4

 * Replace commons-logging by slf4j
 * [BUG] If an uncontrolled exception occurs during validate() the request should progress

###3.3.3

 * Fix missing 'hdivFormStateId' attribute in request scope
 * Fix for issue 94: https://github.com/hdiv/hdiv/issues/94

###3.3.2

 * Reduce dependencies
 * Protect Matchers from StackOverflowError.
 * Reinitialize HttpSession attributes if they are missing.

###3.3.1

 * Allow Hdiv to run in a auto-contained context (without WebApplicationContext)
 * Weak reference RequestContext
 * Allow UUID as Page Ids
 * Fix for Glassfish queryString

###3.3.0

 * Fix issue with parameters without values in WebSphere servers: http://www-01.ibm.com/support/docview.wss?uid=swg1PM35450
 * Fix Spring security issue that may cause the links not to be processed by Hdiv
 * Performance improvements.
 * Ensure error message content is escaped.
 * Error page design update.
 * JSF 1.x support removed. Now JSF 2.0 is the minimum supported version.
 * All request scoped data moved inside RequestContextHolder object.

###3.2.0

 * Add new methods to ISession to get/set/remove session attributes.
 * Refactor state id generation and parse.

###3.1.1

 * Fix spring-security 4.0.x support bug

###3.1.0

 * Performance improvements.

###3.0.0

 * Remove Cipher and Hash strategies.
 * Servlet API >= 3.0
 * Support asynchronous requests.
 * Add 'hdiv-page-' prefix to pages stored in user session.
 * Refactor validation error message creation. It is not created in ValidatorHelperRequest but ValidatorFilter.

###2.1.14

 * LinkUrlProcessor: remove input _HDIV_STATE_ parameter if it exists.
 * Bug fixed in page refresh and non sequential page ids.

###2.1.13

 * Multiple iframe support in the same page
 * Fix issue with IE 6 when redirects to a URL with a fragment identifier.
 * Extension of Struts 1 Tiles InsertTag class for actions with parameters (queryString) support.
 * Fix issue in HDIV Core library. It was caused when repeated values were received in last positions of array named "values".
 * Multipart request handler modified to return appropriate RequestWrapper object, if available, unwrapping the given request as far as necessary.

###2.1.12

 * Fix issue in Struts1: HDIVTilesRequestProcessor class
 * Fix issue in Spring version check.

###2.1.11

 * Fix issue in longLiving request validation.
 * Fix issue in concurrent Ajax requests with 'reuseExistingPageInAjaxRequest' option enabled.
 * New 'X-HDIV-EXCLUDE-PAGE-REUSE' header to avoid page reusing in Ajax calls.

###2.1.10

 * Take into account <hdiv:validationRule> tag order looking for a matching url.
 * New 'parameters' attribute in <hdiv:validationRule> tag. Contains a list or unique regular expression that defines the parameters for which editable validation is applied (https://github.com/hdiv/hdiv/issues/66).
 * F5 key support. Reloading the same page never expire session states (https://github.com/hdiv/hdiv/issues/67).
 * Fix @Validation and grouping issue (https://github.com/hdiv/hdiv/issues/70).
 * In case of detecting an attack in editable data validation, add validation name to the log.
 * New 'ServletContextInitializer' and 'SessionInitializer' interfaces for ServletContext and HttpSession initialization.

###2.1.9

 * Minor error fixes.

###2.1.8

 * Fix multipart issue in Struts1 (https://github.com/hdiv/hdiv/issues/59).
 * Fix multipart issue in Spring MVC (https://github.com/hdiv/hdiv/issues/62).
 * Support Servlet3 standard multipart processing in Spring MVC.

###2.1.7

 * Memory and performance optimizations.
 * New 'hdiv-thymeleaf' module for Thymeleaf template engine support.
 * Create default error page content if no errorPage is configured.
 * @Configuration support for JavaConfig as an alternative to XML.
 * Spring >= 4.0.0 is required to use JavaConfig and @EnableHdivWebSecurity feature
 * Ability to include an inner RequestDataValueProcessor to HdivRequestDataValueProcessor.
 * Spring Security CSRF token integration. Both, HDIV and Spring Security tokens are created.
 * New 'reuseExistingPageInAjaxRequest' configuration option. If it is activated, Ajax requests don't create a new page and reuse the existing one.
 * Move CipherTag to hdiv-core.
 * New 'longLivingPages' feature to configure pages which link and forms never expire.
 * Create a custom token for form elements.

###2.1.6

 * Fix form multipart issue (https://github.com/hdiv/hdiv/issues/44).
 * New 'org.hdiv.regex' package for Java regular expression execution abstraction.
 * New 'hdiv-docs' module with official documentation.
 * Include original parameter value in attack logs

###2.1.5

 * Java >= 1.5 is required.
 * Fix form field confidentiality error on RESTfull configuration (https://github.com/hdiv/hdiv/issues/31).
 * Hash and Cipher strategy support for Ajax requests (https://github.com/hdiv/hdiv/issues/36).
 * Support Spring 4.0.0 (4.0.0.BUILD-SNAPSHOT) and changes to org.springframework.web.servlet.support.RequestDataValueProcessor

###2.1.4

 * Use request method to define StartPages (https://github.com/hdiv/hdiv/issues/5).
 * Editable Validation and JSR303 validations in Spring MVC (https://github.com/hdiv/hdiv/issues/10).
 * Editable Validation support in Grails.
 * New 'showErrorPageOnEditableValidation' attribute in <hdiv:config> to show error page on Editable validation error.
 * Struts1 module for Hdiv. (Struts 1.3.8 support).
 * Fix Multipart request handing in Spring MVC (https://github.com/hdiv/hdiv/issues/14) and (https://github.com/hdiv/hdiv/issues/18).
 * JSF module for Hdiv. (JSF 1.1, 1.2, 2.0 and 2.1 support). Tested with Apache MyFaces and Sun RI.
 * Fix error with multivalue url params.
 * New 'sessionExpired' attribute in <hdiv:config> to define error pages when session has expired.
 * New 'org.hdiv.filter.ValidatorErrorHandler' interface to handle request validation errors. 'DefaultValidatorErrorHandler' class contains default implementation.
 * IDataComposer initialization dropped from InitListener and moved to ValidatorFilter to avoid problems with premature request initialization. Fix (https://github.com/hdiv/hdiv/issues/21)
 * JSF requires ValidatorFilter for GET request validation and IDataComposer initialization
 * Fix Thread safe error on Cipher strategy (https://github.com/hdiv/hdiv/issues/23).
 * Spring beans with generated names (https://github.com/hdiv/hdiv/issues/27).
 * Spring 3.0 or greater is required for hdiv-core.

###2.1.3

 * avoidValidationInUrlsWithoutParams only affects to link urls.
 * Absolute URLs are returned as absolute URLs in LinkUrlProcessor.
 * RequestDataValueProcessor for Grails applications.

###2.1.2

 * Confidential values are visible in the log.
 * Expose form state id to request. Simpler Ajax support.
 * Fixed (https://github.com/hdiv/hdiv/issues/3) error parsing parameters without value
 * Fixed (https://github.com/hdiv/hdiv/issues/4) bug with nested tags

###2.1.1

 * New 'debugMode' configuration mode to log the attacks but don`t stop it.
 * Register and use a group of editable validations by default (Sql injection, exec command and XSS attacks).
 * Add "registerDefaults" attribute to <hdiv:editableValidations> element to enable/disable default editable validations.
 * Add "enableDefaults" attribute to <hdiv:validationRule> element to enable/disable default editable validations per rule.
 * Make possible to update a existing state. Designed for ajax requests that update part of the page.
 * New 'maxPagesPerSession' configuration attribute to control the number of states in session.

###2.1.0

 * JavaDoc and License correction.

###2.1.0.RC3

 * Final Spring Framework 3.1.0 version support.
 * Configurable IUserData instance in the scheme.
 * Better support for anchored urls.
 * Refactor of the url splitting and processing classes.

###2.1.0.RC2

 * Hdiv schema for simpler configuration.

###2.1.0.RC1

------- New Features -------

 * Support for JSF 1.2
 * Support for JSF 2.0

------- Improvements --------

 * WebFlow deletes session pages when conversation ends.
   Consecuently, parameter (flowExecutionKey/execution) is no longer added automatically.
 * URLs with anchor (#) are now correctly managed.

------- NEW CLASSES AND MODIFICATIONS -------

 * added hdiv-jsf module
 * added hdiv-jsf-2 module
 * added hdiv-web-jsf-bookstore module

###2.1.0.M2


------- New Features -------

 * Support for Spring MVC 3.0.4
 * Support for Spring Webflow 2.1.1

------- Improvements --------

 * Better error handling support.
 * The bean 'multipartConfig' is not required, interesting for applications without multipart requests.
 * The 'paramWithoutValidation' and 'excludedURLExtensions' properties are not required in HdivConfig.

------- NEW CLASSES AND MODIFICATIONS -------

 * added hdiv-spring-mvc-3.0.4 module
 * added hdiv-web-spring-mvc-3.0.4 module
 * added hdiv-webflow-2.1.1 module
 * added hdiv-web-webflow-2.1.1 module

###2.1.0.M1

------- New Features -------

 * Support for Spring MVC 3.0.2
 * Support for JSTL 1.2

------- Improvements --------

 * It has done a great job in order to improve performance related to CPU, memory and session usage.
 * A new option has been added to avoid creating a state for links wihtout any parameter.
 * A new option called excludedURLExtensions allows excluding links depending on their extension.

------- NEW CLASSES AND MODIFICATIONS -------

 * New interface PageIdGenerator allows changing page id generation strategy.
 * Package org.hdiv.idGenerator has been created for uidGenerator and PageIdGenerator interfaces and their implementations.
 * New DataComposerFactory and DataValidatorFactory factories improve performance on generating IDataComposer and IDataValidator objects.

###2.0.4

------- New Features -------

 * Support for Struts 2.0.11
 * improvement to prevent Cross-site Request Forgery (CSRF) attacks: a random token is included in all requests.

------- BUGS FIXED -------

 * \#007   protectedExtensions for Struts 1.x: definition of this property is obligatory for Struts 1.x applications
 * \#008   "rewrite" tag: this tag is included in HDIV to add HDIV state in url (Struts 1.x)
 * \#009   logout in Spring MVC: RedirectViewHDIV don't add HDIV state if session doesn't exist

------- NEW CLASSES AND MODIFICATIONS -------

 * added hdiv-struts-2.0.11 module
 * added hdiv-web-struts-2.0.11 module

 * Package org.hdiv.taglib.util
  - modified "getURLWithoutRelativePaths" method  
  - added method "hasActionOrServletExtension" to RequestUtilsHDIV class

 * Package org.hdiv.taglib.html
  - added rewrite tag implementation

 * Package org.hdiv.web.servlet.view
  - modified method "sendRedirect"

------- Configuration Files -------

 * modified hdiv-html.tld file with "rewrite" tag
 * modified hdiv-html-el.tld file with "rewrite" tag


###2.0.3

------- New Features -------

 * Spring MVC & Webflow (SWF)
 * Support for Spring 2.5

------- BUGS FIXED -------

 * \#006   JSTL url tag: it was mandatory to define absolute urls. This has been fixed and now it is possible to define relative urls.

------- NEW CLASSES AND MODIFICATIONS -------

 * added hdiv-spring-mvc-2.5 module
 * added hdiv-web-spring-mvc-2.5 module
 * added hdiv-webflow-1.0.5 module
 * added hdiv-web-webflow-1.0.5 module
 * renamed hdiv-spring-mvc module to hdiv-spring-mvc-2.0

 * Package org.hdiv.datacomposer
  - added method "addFlowId" to IDataComposerMemory class
  - added method "addFlowId" to DataComposerMemory class

 * Package org.hdiv.util
  - added method "getDataComposer" to HDIVUtil class
  - modified "addHDIVParameterIfNecessary" (StandardRequestUtilsHDIV)
  - modified "getContextRelativePath" (StandardRequestUtilsHDIV)
  - added method "addExtraParameters" (StandardRequestUtilsHDIV)
  - added method "addFlowExecutionParameter" (StandardRequestUtilsHDIV)

 * Package org.hdiv.session
  - added method "removeEndedPages" to ISession class
  - added method "removeEndedPages" to SessionHDIV class

 * Package org.hdiv.state
  - added property "flowId" to Page class
  - added getter and setter methods for "flowId" property (IPage and Page)

 * Package org.hdiv.config.multipart
  - modified "handleMultipartRequest" method (SpringMVCMultipartConfig)
  - modified "addTextParameter" method (SpringMVCMultipartConfig)

 * Package org.hdiv.config
  - renamed "getProtectedExtensions" method to "getProtectedUTLPatterns" (HDIVConfig)
  - modified "setProtectedExtensions" method (HDIVConfig)

 * Package org.hdiv.taglibs.standard.tag.common.core
  - modified "doEndTag" method (RedirectSupportHDIV)
  - modified "doEndTag" method (URLSupportHDIV)

 * Package org.hdiv.web.util
  - added "TagUtils" class

 * Package org.hdiv.web.servlet.tags.form
  - modified method "writeTagContent" (OptionsTagHDIV)
  - modified method "renderOption" (OptionTagHDIV)


###2.0.1

------- New Features -------

 * Spring MVC "redirect:" prefix support

------- BUGS FIXED -------

 * \#005   File upload error messages integration

------- NEW CLASSES AND MODIFICATIONS -------

 * Package org.hdiv.config
  - added property "FILEUPLOAD_EXCEPTION" to IMultipartConfig class
  - modified handleMultipartRequest method declaration (IMultipartConfig,
    StrutsMultipartConfig, Struts2Multipartconfig and SpringMVCMultipartConfig)

 * Package org.hdiv.filter
  - modified doFilterInternal method to check upload errors

 * Package org.hdiv.util
  - added "SpringRequestUtilsHDIV" class

 * Package org.hdiv.web.multipart
  - modified resolveMultipart method (HDIVMultipartResolver)

 * Package org.hdiv.web.servlet.view
  - added "InternalResourceViewResolverHDIV" class to support
    Spring MVC "redirect:" prefix
  - added "RedirectViewHDIV" class to support Spring MVC
    "redirect:" prefix

 * Package org.hdiv.action
  - added method "processForwardConfig" to "HDIVRequestProcessor" class

 * Package org.hdiv.taglib.util
  - added method "addHDIVParameterIfNecessary" to RequestUtilsHDIV

 * Package org.hdiv.upload
  - modified method "handleRequest" (HDIVMultipartRequestHandler)

 * Package org.hdiv.dispatcher.multipart
  - modified method "parse" (HDIVJakartaMultiPartRequest)


###2.0

------- New Features -------

 * Added support for Spring MVC
 * Added support for Jakarta Taglibs 1.1.0
 * Added support for Jakarta Taglibs 1.1.2
 * Added support for Struts 2.0.9
 * Added support for Struts 1.3.8

------- BUGS FIXED -------

 * \#004   Logout does not work in Struts 2.0.6: resolved

------- NEW CLASSES AND MODIFICATIONS -------

 * added hdiv-spring-mvc module
 * added hdiv-web-spring-mvc module
 * added hdiv-jstl-taglibs-1.1.0 module
 * added hdiv-web-jstl-taglibs-1.1.0 module
 * added hdiv-jstl-taglibs-1.1.2 module
 * added hdiv-web-jstl-taglibs-1.1.2 module
 * added hdiv-struts-2.0.9 module
 * added hdiv-web-struts-2.0.9 module
 * added hdiv-struts-1.3.8 module
 * added hdiv-web-struts-1.3.8 module

 * Package org.hdiv.filter
   - added AbstractValidatorHelper class

------- Configuration Files -------

 * applicationContext.xml
  - removed property "logger" from "stateUtil" bean
  - renamed "helper" bean property


###1.3

------- New Features -------

 * Added support for Struts 2.0.6

------- BUGS FIXED -------

 * \#003   RequestWrapper: modified getParameterMap() method

------- NEW CLASSES AND MODIFICATIONS -------

 * added hdiv-struts-2.0.6 module
 * added hdiv-web-struts-2.0.6 module

 * Package org.hdiv.filter
   - modified ValidatorHelper class to extend from OncePerRequestFilter

 * Package org.hdiv.state
   - property logger and all references have been removed

 * Package org.hdiv.config
   - modified "checkValue()" method

 * Package org.hdiv.validator
  - modified acceptedPattern property
  - modified rejectedPattern property

 * Package org.hdiv.util
  - modified HDIVUtil's getActionMappingName method

------- Configuration Files -------

 * applicationContext.xml
  - removed property "logger" from "stateUtil" bean
  - renamed "session" bean to "sessionHDIV"
  - modified "multipartConfig"'s bean class


###1.1.2

------- New Features -------

 * Added support for Struts-EL subproject.
 * Added support for Tiles.
 * New configuration for start pages and error page (we must define them without context path in hdiv-config.xml file)

------- BUGS FIXED -------

 * \#002   support for Weblogic web server

------- NEW CLASSES AND MODIFICATIONS -------

 * Package org.hdiv.strutsel

 * Package org.hdiv.tiles

 * Package org.hdiv.filter
   - added "validateStartPageParameters()" method to ValidatorHelper
   - modified "doFilter" method to support weblogic

------- Configuration Files -------

 * Added hdiv-html-el.tld file to Struts-el
 * Added hdiv-logic-el.tld file to Struts-el


###1.1.1

------- NEW CLASSES AND MODIFICATIONS -------

 * Added org.hdiv.validation.IValidation class to editable data validations.

 * Added org.hdiv.util.Constants class.

 * Package org.hdiv.config
   - added "matchedPages" property to HDIVConfig
   - added "matchedParameters" property to HDIVConfig
   - added "checkValue()" method to HDIVConfig, to check if value is an init action or parameter

 * Package org.hdiv.filter
   - added "getHeader" method to RequestWrapper
   - added "getHeaders" method to RequestWrapper
   - added "replaceCookieString" method to RequestWrapper
   - added "SavedCookie" class

------- Configuration Files -------

 * hdiv-config.xml:
   - added new beans: avoidCookiesIntegrity, avoidCookiesConfidentiality
   - added "avoidCookiesIntegrity" and "avoidCookiesConfidentiality" beans to "config" bean
   - modified "randomName" bean type


###1.1

------- New Features -------

 * Added support for editable data validations.
 * Added integrity of cookies.

------- BUGS FIXED -------

 * \#001   support for HTML Tag Library inside logic:iterate tag


------- NEW CLASSES AND MODIFICATIONS -------

 * Added org.hdiv.validation package.

 * Added org.hdiv.config.HDIVValidations class to editable data validations.

 * Added org.hdiv.filter.IResponseWrapper class to cookie's validation.

 * Added org.hdiv.filter.ResponseWrapper class to cookie's validation.

 * Added org.hdiv.action.HDIVRequestProcessor to visualize the errors
   produced in the editable fields detected by HDIV.

 * Added org.hdiv.taglib.util.ResponseUtilsHDIV class that contains general purpose
   utility methods related to generating a servlet response in the Struts controller
   framework.

 * Package org.hdiv.config
   - added "setValidations()" method to HDIVConfig
   - added "existValidations()" method to HDIVConfig, to check if there are validations defined for    
     editable fields
   - added "areEditableParameterValuesValid()" method to HDIVConfig, to check if the values are valid
     for the editable parameter

 * Package org.hdiv.dataComposer
   - redefined compose methods to accept editableName parameters

 * Package org.hdiv.filter
   - added "validateEditableParameter()" method to validate editable parameters

 * Package org.hdiv.listener
   - modified "sessionCreated()" method to store suffix in session in the memory version in order to add to HDIV      parameter

 * Package org.hdiv.session
   - added "generateInitialPageId()" method to SessionHDIV

 * Package org.hdiv.util
   - added two new error codes to HDIVErrorCodes

------- Configuration Files -------

 * Added hdiv-validations.xml file to editable data validations.

 * Renamed hdivConfig.xml to hdiv-config-xml and added property "validations" to bean "config"

 * applicationContext.xml: bean "page" and all references have been removed
