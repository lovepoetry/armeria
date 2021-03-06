/*
 * Copyright 2017 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.linecorp.armeria.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpResponseWriter;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.common.ResponseHeaders;

class HttpResponseExceptionTest {

    @Test
    void testHttpResponse() throws Exception {
        final HttpResponseWriter response = HttpResponse.streaming();
        final HttpResponseException exception = HttpResponseException.of(response);
        response.write(ResponseHeaders.of(HttpStatus.INTERNAL_SERVER_ERROR,
                                          HttpHeaderNames.CONTENT_TYPE, MediaType.PLAIN_TEXT_UTF_8));
        response.close();

        final AggregatedHttpResponse message = exception.httpResponse().aggregate().join();
        assertThat(message.status()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(message.contentType()).isEqualTo(MediaType.PLAIN_TEXT_UTF_8);
    }
}
