/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.http.timers.client;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static software.amazon.awssdk.internal.http.timers.ClientExecutionAndRequestTimerTestUtils.createMockGetRequest;
import static software.amazon.awssdk.internal.http.timers.ClientExecutionAndRequestTimerTestUtils.execute;
import static software.amazon.awssdk.internal.http.timers.ClientExecutionAndRequestTimerTestUtils.interruptCurrentThreadAfterDelay;
import static software.amazon.awssdk.internal.http.timers.TimeoutTestConstants.CLIENT_EXECUTION_TIMEOUT;

import java.io.InputStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import software.amazon.awssdk.AbortedException;
import software.amazon.awssdk.AmazonClientException;
import software.amazon.awssdk.LegacyClientConfiguration;
import software.amazon.awssdk.handlers.RequestHandler;
import software.amazon.awssdk.http.AbortableCallable;
import software.amazon.awssdk.http.AmazonHttpClient;
import software.amazon.awssdk.http.ExecutionContext;
import software.amazon.awssdk.http.MockServerTestBase;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.SdkHttpFullResponse;
import software.amazon.awssdk.http.exception.ClientExecutionTimeoutException;
import software.amazon.awssdk.http.server.MockServer;
import software.amazon.awssdk.internal.http.request.RequestHandlerTestUtils;
import software.amazon.awssdk.internal.http.request.SlowRequestHandler;
import software.amazon.awssdk.internal.http.response.DummyResponseHandler;

@RunWith(MockitoJUnitRunner.class)
public class AbortedExceptionClientExecutionTimerIntegrationTest extends MockServerTestBase {

    private AmazonHttpClient httpClient;

    @Mock
    private SdkHttpClient sdkHttpClient;

    @Mock
    private AbortableCallable<SdkHttpFullResponse> abortableCallable;

    @Before
    public void setup() throws Exception {
        when(sdkHttpClient.prepareRequest(any(), any())).thenReturn(abortableCallable);
        httpClient = AmazonHttpClient.builder()
                .clientConfiguration(new LegacyClientConfiguration()
                                             .withClientExecutionTimeout(CLIENT_EXECUTION_TIMEOUT)
                                             .withMaxErrorRetry(0))
                .sdkHttpClient(sdkHttpClient)
                .build();
        when(abortableCallable.call()).thenReturn(SdkHttpFullResponse.builder()
                                                                     .statusCode(200)
                                                                     .build());
    }

    @Override
    protected MockServer buildMockServer() {
        return new MockServer(MockServer.DummyResponseServerBehavior.build(200, "Hi", "Dummy response"));
    }

    @Test(expected = AbortedException.class)
    public void clientExecutionTimeoutEnabled_aborted_exception_occurs_timeout_not_expired() throws Exception {
        when(abortableCallable.call()).thenThrow(new AbortedException());

        execute(httpClient, createMockGetRequest());
    }

    @Test(expected = ClientExecutionTimeoutException.class)
    public void clientExecutionTimeoutEnabled_aborted_exception_occurs_timeout_expired() throws Exception {
        // Simulate a slow HTTP request
        when(abortableCallable.call()).thenAnswer(i -> {
            Thread.sleep(10_000);
            return null;
        });

        execute(httpClient, createMockGetRequest());
    }

    /**
     * Tests that a streaming operation has it's request properly cleaned up if the client is interrupted after the
     * response is received.
     *
     * @see TT0070103230
     */
    @Test
    public void clientInterruptedDuringResponseHandlers_DoesNotLeakConnection() throws Exception {
        InputStream mockContent = mock(InputStream.class);
        when(abortableCallable.call()).thenReturn(SdkHttpFullResponse.builder()
                                                                     .statusCode(200)
                                                                     .content(mockContent)
                                                                     .build());
        interruptCurrentThreadAfterDelay(1000);
        List<RequestHandler> requestHandlers = RequestHandlerTestUtils
                .buildRequestHandlerList(new SlowRequestHandler().withAfterResponseWaitInSeconds(10));
        try {
            requestBuilder()
                    .executionContext(withHandlers(requestHandlers))
                    .execute(new DummyResponseHandler().leaveConnectionOpen());
            fail("Expected exception");
        } catch (AmazonClientException e) {
            assertThat(e.getCause(), instanceOf(InterruptedException.class));
        }

        verify(mockContent).close();
    }

    private AmazonHttpClient.RequestExecutionBuilder requestBuilder() {
        return httpClient.requestExecutionBuilder().request(newGetRequest());
    }

    private ExecutionContext withHandlers(List<RequestHandler> requestHandlers) {
        return ExecutionContext.builder().withRequestHandlers(requestHandlers).build();
    }
}
