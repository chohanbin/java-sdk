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
package com.ibm.watson.developer_cloud.natural_language_understanding.v1;

import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.http.RequestBuilder;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.DeleteModelOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.ListModelsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.ListModelsResults;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.util.GsonSingleton;
import com.ibm.watson.developer_cloud.util.ResponseConverterUtils;
import com.ibm.watson.developer_cloud.util.Validator;

/**
 * Analyze various features of text content at scale. Provide text, raw HTML, or a public URL, and IBM Watson Natural
 * Language Understanding will give you results for the features you request. The service cleans HTML content before
 * analysis by default, so the results can ignore most advertisements and other unwanted content.
 *
 * You can create <a target="_blank"
 * href="https://www.ibm.com/watson/developercloud/doc/natural-language-understanding/customizing.html">custom
 * models</a> with Watson Knowledge Studio that can be used to detect custom entities and relations in Natural Language
 * Understanding.
 *
 * @version v1
 * @see <a href="http://www.ibm.com/watson/developercloud/natural-language-understanding.html">Natural Language
 *      Understanding</a>
 */
public class NaturalLanguageUnderstanding extends WatsonService {

  private static final String SERVICE_NAME = "natural_language_understanding";
  private static final String URL = "https://gateway.watsonplatform.net/natural-language-understanding/api";

  private String versionDate;

  /**
   * Instantiates a new `NaturalLanguageUnderstanding`.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   */
  public NaturalLanguageUnderstanding(String versionDate) {
    super(SERVICE_NAME);
    if ((getEndPoint() == null) || getEndPoint().isEmpty()) {
      setEndPoint(URL);
    }

    Validator.isTrue((versionDate != null) && !versionDate.isEmpty(), "version cannot be null.");

    this.versionDate = versionDate;
  }

  /**
   * Instantiates a new `NaturalLanguageUnderstanding` with username and password.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   * @param username the username
   * @param password the password
   */
  public NaturalLanguageUnderstanding(String versionDate, String username, String password) {
    this(versionDate);
    setUsernameAndPassword(username, password);
  }

  /**
   * Instantiates a new `NaturalLanguageUnderstanding` with IAM. Note that if the access token is specified in the
   * iamOptions, you accept responsibility for managing the access token yourself. You must set a new access token
   * before this one expires. Failing to do so will result in authentication errors after this token expires.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   * @param iamOptions the options for authenticating through IAM
   */
  public NaturalLanguageUnderstanding(String versionDate, IamOptions iamOptions) {
    this(versionDate);
    setIamCredentials(iamOptions);
  }

  /**
   * Analyze text, HTML, or a public webpage.
   *
   * Analyzes text, HTML, or a public webpage with one or more text analysis features.
   *
   * @param analyzeOptions the {@link AnalyzeOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link AnalysisResults}
   */
  public ServiceCall<AnalysisResults> analyze(AnalyzeOptions analyzeOptions) {
    Validator.notNull(analyzeOptions, "analyzeOptions cannot be null");
    String[] pathSegments = { "v1/analyze" };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    final JsonObject contentJson = new JsonObject();
    if (analyzeOptions.text() != null) {
      contentJson.addProperty("text", analyzeOptions.text());
    }
    if (analyzeOptions.html() != null) {
      contentJson.addProperty("html", analyzeOptions.html());
    }
    if (analyzeOptions.url() != null) {
      contentJson.addProperty("url", analyzeOptions.url());
    }
    contentJson.add("features", GsonSingleton.getGson().toJsonTree(analyzeOptions.features()));
    if (analyzeOptions.clean() != null) {
      contentJson.addProperty("clean", analyzeOptions.clean());
    }
    if (analyzeOptions.xpath() != null) {
      contentJson.addProperty("xpath", analyzeOptions.xpath());
    }
    if (analyzeOptions.fallbackToRaw() != null) {
      contentJson.addProperty("fallback_to_raw", analyzeOptions.fallbackToRaw());
    }
    if (analyzeOptions.returnAnalyzedText() != null) {
      contentJson.addProperty("return_analyzed_text", analyzeOptions.returnAnalyzedText());
    }
    if (analyzeOptions.language() != null) {
      contentJson.addProperty("language", analyzeOptions.language());
    }
    if (analyzeOptions.limitTextCharacters() != null) {
      contentJson.addProperty("limit_text_characters", analyzeOptions.limitTextCharacters());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(AnalysisResults.class));
  }

  /**
   * Delete model.
   *
   * Deletes a custom model.
   *
   * @param deleteModelOptions the {@link DeleteModelOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteModel(DeleteModelOptions deleteModelOptions) {
    Validator.notNull(deleteModelOptions, "deleteModelOptions cannot be null");
    String[] pathSegments = { "v1/models" };
    String[] pathParameters = { deleteModelOptions.modelId() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * List models.
   *
   * Lists available models for Relations and Entities features, including Watson Knowledge Studio custom models that
   * you have created and linked to your Natural Language Understanding service.
   *
   * @param listModelsOptions the {@link ListModelsOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link ListModelsResults}
   */
  public ServiceCall<ListModelsResults> listModels(ListModelsOptions listModelsOptions) {
    String[] pathSegments = { "v1/models" };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    if (listModelsOptions != null) {
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(ListModelsResults.class));
  }

  /**
   * List models.
   *
   * Lists available models for Relations and Entities features, including Watson Knowledge Studio custom models that
   * you have created and linked to your Natural Language Understanding service.
   *
   * @return a {@link ServiceCall} with a response type of {@link ListModelsResults}
   */
  public ServiceCall<ListModelsResults> listModels() {
    return listModels(null);
  }

}
