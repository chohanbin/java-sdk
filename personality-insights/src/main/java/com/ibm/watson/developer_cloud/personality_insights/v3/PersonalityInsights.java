/*
 * Copyright 2018 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.ibm.watson.developer_cloud.personality_insights.v3;

import com.ibm.watson.developer_cloud.http.HttpHeaders;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.http.RequestBuilder;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.util.GsonSingleton;
import com.ibm.watson.developer_cloud.util.ResponseConverterUtils;
import com.ibm.watson.developer_cloud.util.Validator;

/**
 * The IBM Watson Personality Insights service enables applications to derive insights from social media, enterprise
 * data, or other digital communications. The service uses linguistic analytics to infer individuals' intrinsic
 * personality characteristics, including Big Five, Needs, and Values, from digital communications such as email, text
 * messages, tweets, and forum posts.
 *
 * The service can automatically infer, from potentially noisy social media, portraits of individuals that reflect their
 * personality characteristics. The service can infer consumption preferences based on the results of its analysis and,
 * for JSON content that is timestamped, can report temporal behavior.
 *
 * For information about the meaning of the models that the service uses to describe personality characteristics, see
 * [Personality models](https://console.bluemix.net/docs/services/personality-insights/models.html). For information
 * about the meaning of the consumption preferences, see [Consumption
 * preferences](https://console.bluemix.net/docs/services/personality-insights/preferences.html).
 *
 * @version v3
 * @see <a href="http://www.ibm.com/watson/developercloud/personality-insights.html">Personality Insights</a>
 */
public class PersonalityInsights extends WatsonService {

  private static final String SERVICE_NAME = "personality_insights";
  private static final String URL = "https://gateway.watsonplatform.net/personality-insights/api";

  private String versionDate;

  /**
   * Instantiates a new `PersonalityInsights`.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   */
  public PersonalityInsights(String versionDate) {
    super(SERVICE_NAME);
    if ((getEndPoint() == null) || getEndPoint().isEmpty()) {
      setEndPoint(URL);
    }

    Validator.isTrue((versionDate != null) && !versionDate.isEmpty(), "version cannot be null.");

    this.versionDate = versionDate;
  }

  /**
   * Instantiates a new `PersonalityInsights` with username and password.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   * @param username the username
   * @param password the password
   */
  public PersonalityInsights(String versionDate, String username, String password) {
    this(versionDate);
    setUsernameAndPassword(username, password);
  }

  /**
   * Instantiates a new `PersonalityInsights` with IAM. Note that if the access token is specified in the iamOptions,
   * you accept responsibility for managing the access token yourself. You must set a new access token before this one
   * expires. Failing to do so will result in authentication errors after this token expires.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   * @param iamOptions the options for authenticating through IAM
   */
  public PersonalityInsights(String versionDate, IamOptions iamOptions) {
    this(versionDate);
    setIamCredentials(iamOptions);
  }

  /**
   * Generates a personality profile based on input text.
   *
   * Generates a personality profile for the author of the input text. The service accepts a maximum of 20 MB of input
   * content, but it requires much less text to produce an accurate profile; for more information, see [Providing
   * sufficient input](https://console.bluemix.net/docs/services/personality-insights/input.html#sufficient).
   *
   * @param profileOptions the {@link ProfileOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Profile}
   */
  public ServiceCall<Profile> profile(ProfileOptions profileOptions) {
    Validator.notNull(profileOptions, "profileOptions cannot be null");
    String[] pathSegments = { "v3/profile" };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    builder.header("Content-Type", profileOptions.contentType());
    if (profileOptions.contentLanguage() != null) {
      builder.header("Content-Language", profileOptions.contentLanguage());
    }
    if (profileOptions.acceptLanguage() != null) {
      builder.header("Accept-Language", profileOptions.acceptLanguage());
    }
    if (profileOptions.rawScores() != null) {
      builder.query("raw_scores", String.valueOf(profileOptions.rawScores()));
    }
    if (profileOptions.consumptionPreferences() != null) {
      builder.query("consumption_preferences", String.valueOf(profileOptions.consumptionPreferences()));
    }
    if (profileOptions.contentType().equalsIgnoreCase(ProfileOptions.ContentType.APPLICATION_JSON)) {
      builder.bodyJson(GsonSingleton.getGson().toJsonTree(profileOptions.content()).getAsJsonObject());
    } else {
      builder.bodyContent(profileOptions.body(), profileOptions.contentType());
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Profile.class));
  }

  /**
   * Get profile. as csv
   *
   * Generates a personality profile for the author of the input text. The service accepts a maximum of 20 MB of input
   * content, but it requires much less text to produce an accurate profile; for more information, see [Providing
   * sufficient input](https://console.bluemix.net/docs/services/personality-insights/input.html#sufficient). The
   * service analyzes text in Arabic, English, Japanese, Korean, or Spanish and returns its results in a variety of
   * languages. You can provide plain text, HTML, or JSON input by specifying the **Content-Type** parameter; the
   * default is `text/plain`. Request a JSON or comma-separated values (CSV) response by specifying the **Accept**
   * parameter; CSV output includes a fixed number of columns and optional headers. Per the JSON specification, the
   * default character encoding for JSON content is effectively always UTF-8; per the HTTP specification, the default
   * encoding for plain text and HTML is ISO-8859-1 (effectively, the ASCII character set). When specifying a content
   * type of plain text or HTML, include the `charset` parameter to indicate the character encoding of the input text;
   * for example: `Content-Type: text/plain;charset=utf-8`. For detailed information about calling the service and the
   * responses it can generate, see (Requesting a
   * profile)[https://console.bluemix.net/docs/services/personality-insights/input.html], (Understanding a JSON
   * profile)[https://console.bluemix.net/docs/services/personality-insights/output.html], and (Understanding a CSV
   * profile)[https://console.bluemix.net/docs/services/personality-insights/output-csv.html].
   *
   * @param profileOptions the {@link ProfileOptions} containing the options for the call
   * @param includeHeaders option to have the CSV headers returned with the response
   * @return a {@link ServiceCall} with a response type of {@link Profile}
   */
  public ServiceCall<String> getProfileAsCSV(ProfileOptions profileOptions, boolean includeHeaders) {
    Validator.notNull(profileOptions, "profileOptions cannot be null");
    String[] pathSegments = { "v3/profile" };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    builder.header("Content-Type", profileOptions.contentType());
    if (profileOptions.contentLanguage() != null) {
      builder.header("Content-Language", profileOptions.contentLanguage());
    }
    if (profileOptions.acceptLanguage() != null) {
      builder.header("Accept-Language", profileOptions.acceptLanguage());
    }
    if (profileOptions.rawScores() != null) {
      builder.query("raw_scores", String.valueOf(profileOptions.rawScores()));
    }
    if (profileOptions.consumptionPreferences() != null) {
      builder.query("consumption_preferences", String.valueOf(profileOptions.consumptionPreferences()));
    }
    if (profileOptions.contentType().equalsIgnoreCase(ProfileOptions.ContentType.APPLICATION_JSON)) {
      builder.bodyJson(GsonSingleton.getGson().toJsonTree(profileOptions.content()).getAsJsonObject());
    } else {
      builder.bodyContent(profileOptions.body(), profileOptions.contentType());
    }

    builder.header(HttpHeaders.ACCEPT, HttpMediaType.TEXT_CSV);
    builder.query("csv_headers", includeHeaders);

    return createServiceCall(builder.build(), ResponseConverterUtils.getString());
  }

}
