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
package testing.custom.transformer;

import java.util.List;
import software.amazon.awssdk.Request;
import software.amazon.awssdk.metrics.internal.cloudwatch.spi.RequestMetricTransformer;
import software.amazon.awssdk.metrics.spi.MetricType;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;

/**
 * For testing purposes only.
 */
public class DynamoDbRequestMetricTransformer implements RequestMetricTransformer {
    @Override
    public List<MetricDatum> toMetricData(MetricType metricType, Request<?> request, Object response) {
        return null;
    }
}
