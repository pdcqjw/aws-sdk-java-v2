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

package software.amazon.awssdk.interceptor;

/**
 * An attribute attached to a particular execution, stored in {@link ExecutionAttributes}.
 *
 * This is typically used as a static final field in an {@link ExecutionInterceptor}:
 * <code>
 * class MyExecutionInterceptor implements ExecutionInterceptor {
 *     private static final ExecutionAttribute&lt;String&gt; DATA = new ExecutionAttribute&lt;&gt;();
 *
 *     @Override
 *     public void beforeExecution(BeforeExecutionContext execution, ExecutionAttributes executionAttributes) {
 *         executionAttributes.put(DATA, "Request: " + execution.request());
 *     }
 *
 *     @Override
 *     public void afterExecution(AfterExecutionContext execution, ExecutionAttributes executionAttributes) {
 *         executionAttributes.get(DATA); // Retrieve the value saved in beforeExecution.
 *     }
 * }
 * </code>
 * @param <T> The type of data associated with this attribute.
 */
public class ExecutionAttribute<T> {

}
