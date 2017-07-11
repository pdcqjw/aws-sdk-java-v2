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

import software.amazon.awssdk.SdkRequest;
import software.amazon.awssdk.annotation.SdkPublicApi;
import software.amazon.awssdk.annotation.ThreadSafe;
import software.amazon.awssdk.interceptor.ExecutionAttributes;
import software.amazon.awssdk.interceptor.ExecutionInterceptor;

/**
 * The state of the execution when the {@link ExecutionInterceptor#beforeExecution} method is invoked.
 */
@ThreadSafe
@SdkPublicApi
public interface BeforeExecutionContext {
    /**
     * The {@link SdkRequest} to be executed.
     */
    SdkRequest request();
}
