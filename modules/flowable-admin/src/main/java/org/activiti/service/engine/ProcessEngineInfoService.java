/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.service.engine;

import org.activiti.domain.EndpointType;
import org.activiti.domain.ServerConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessEngineInfoService {

    public static final String PROCESS_ENGINE_INFO_URL = "management/engine";
    public static final String DMN_ENGINE_INFO_URL = "dmn-management/engine";
    public static final String FORM_ENGINE_INFO_URL = "form-management/engine";

    @Autowired
    protected ActivitiClientService clientUtil;

    @Autowired
    protected ObjectMapper objectMapper;

    public JsonNode getEngineInfo(ServerConfig serverConfig) {

        EndpointType endpointType = EndpointType.valueOf(serverConfig.getEndpointType());

        URIBuilder builder = null;

        switch (endpointType) {

            case PROCESS:
                builder = clientUtil.createUriBuilder(PROCESS_ENGINE_INFO_URL);
                break;

            case DMN:
                builder = clientUtil.createUriBuilder(DMN_ENGINE_INFO_URL);
                break;

            case FORM:
                builder = clientUtil.createUriBuilder(FORM_ENGINE_INFO_URL);
                break;
        }

        HttpGet get = new HttpGet(clientUtil.getServerUrl(serverConfig, builder));
        return clientUtil.executeRequest(get, serverConfig);
    }
}
