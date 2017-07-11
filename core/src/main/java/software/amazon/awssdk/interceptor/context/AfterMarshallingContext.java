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

import software.amazon.awssdk.annotation.SdkPublicApi;
import software.amazon.awssdk.annotation.ThreadSafe;
import software.amazon.awssdk.http.SdkHttpFullRequest;
import software.amazon.awssdk.interceptor.ExecutionInterceptor;

/**
 * The state of the execution when the {@link ExecutionInterceptor#afterMarshalling} method is invoked.
 */
@ThreadSafe
@SdkPublicApi
public interface AfterMarshallingContext extends BeforeMarshallingContext {
    /**
     * The {@link SdkHttpFullRequest} that was created as a result of marshalling the {@link #request()}. This is the HTTP request
     * that will be sent to the downstream service.
     */
    SdkHttpFullRequest httpRequest();
}
