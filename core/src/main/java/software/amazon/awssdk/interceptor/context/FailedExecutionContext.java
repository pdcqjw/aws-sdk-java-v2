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

package software.amazon.awssdk.interceptor.context;

import java.util.Optional;
import software.amazon.awssdk.SdkRequest;
import software.amazon.awssdk.SdkResponse;
import software.amazon.awssdk.annotation.SdkPublicApi;
import software.amazon.awssdk.annotation.ThreadSafe;
import software.amazon.awssdk.http.SdkHttpFullRequest;
import software.amazon.awssdk.http.SdkHttpFullResponse;
import software.amazon.awssdk.interceptor.ExecutionInterceptor;

/**
 * All information that is known about a particular execution that has failed. This is given to
 * {@link ExecutionInterceptor#onExecutionFailure} if an entire execution fails for any reason. This includes all information
 * that is known about the request, like the {@link #request()} and the {@link #exception()} that caused the failure.
 */
@ThreadSafe
@SdkPublicApi
public interface FailedExecutionContext {
    /**
     * The exception associated with the failed execution. This is the reason the execution has failed, and is the exception that
     * will be returned or thrown from the client method call. This will never return null.
     */
    Throwable exception();

    /**
     * The latest version of the {@link SdkRequest} available when the execution failed. This will never return null.
     */
    SdkRequest request();

    /**
     * The latest version of the {@link SdkHttpFullRequest} available when the execution failed. If the execution failed before
     * or during request marshalling, this will return {@link Optional#empty()}.
     */
    Optional<SdkHttpFullRequest> httpRequest();

    /**
     * The latest version of the {@link SdkHttpFullResponse} available when the execution failed. If the execution failed before
     * or during transmission, this will return {@link Optional#empty()}.
     */
    Optional<SdkHttpFullResponse> httpResponse();

    /**
     * The latest version of the {@link SdkResponse} available when the execution failed. If the execution failed before or
     * during response unmarshalling, this will return {@link Optional#empty()}.
     */
    Optional<SdkResponse> response();
}
