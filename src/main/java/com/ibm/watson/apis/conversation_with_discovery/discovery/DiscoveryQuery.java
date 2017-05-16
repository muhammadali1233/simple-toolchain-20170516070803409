/*
 * Copyright 2015 IBM Corp. All Rights Reserved.
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
package com.ibm.watson.apis.conversation_with_discovery.discovery;

import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ibm.watson.apis.conversation_with_discovery.utils.Constants;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryRequest;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryResponse;

/**
 * The Class DiscoveryQuery.
 */
public class DiscoveryQuery {

  private String collectionId;

  private Discovery discovery;

  private String environmentId;

  private String password;

  private String userName;

  private String queryFields = "none";

  /**
   * Instantiates a new discovery query.
   */
  public DiscoveryQuery() {
    userName = "14de0531-d268-40e3-a755-9b31e2d5309e";
    password = "VopkW7caWE5h";
    collectionId = "50dc885a-80f9-4f31-9997-79fe04b5acb5";
    environmentId = "bbcdd1c5-01d8-40ec-9a42-3bb643f86ce2";
    queryFields = System.getenv("DISCOVERY_QUERY_FIELDS");

    discovery = new Discovery(Constants.DISCOVERY_VERSION);
    discovery.setEndPoint(Constants.DISCOVERY_URL);
    discovery.setUsernameAndPassword(userName, password);
  }

  /**
   * Use the Watson Developer Cloud SDK to send the user's query to the discovery service.
   *
   * @param userQuery The user's query to be sent to the discovery service
   * @return The query responses obtained from the discovery service
   * @throws Exception the exception
   */
  public QueryResponse query(String userQuery) throws Exception {
    QueryRequest.Builder queryBuilder = new QueryRequest.Builder(environmentId, collectionId);
    
    StringBuilder sb = new StringBuilder();
    
    if(queryFields == null || queryFields.length() == 0 || queryFields.equalsIgnoreCase("none")) {
      sb.append(userQuery);
    } else {
      StringTokenizer st = new StringTokenizer(queryFields, ",");
      while (st.hasMoreTokens()) {
        sb.append(st.nextToken().trim());
        sb.append(":");
        sb.append(userQuery);
        if (st.hasMoreTokens()) {
          sb.append(",");
        }
      }
    }

    queryBuilder.query(sb.toString());
    QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute();

    return queryResponse;
  }
}
