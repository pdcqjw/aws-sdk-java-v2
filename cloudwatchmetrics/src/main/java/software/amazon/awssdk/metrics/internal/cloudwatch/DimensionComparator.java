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

package software.amazon.awssdk.metrics.internal.cloudwatch;

import java.util.Comparator;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.util.StringUtils;

/**
 * Used to compare {@link Dimension}.
 *
 */
enum DimensionComparator implements Comparator<Dimension> {
    INSTANCE;
    /** Used to indicate the two objects being compared are both non-nulls. */
    private static final int NON_NULLS = 2;

    @Override
    public int compare(Dimension dim1, Dimension dim2) {
        int result = nullCompare(dim1, dim2);
        if (result == NON_NULLS) {
            result = nullSafeCompare(dim1.name(), dim2.name());
            if (result == 0) {
                return nullSafeCompare(dim1.value(), dim2.value());
            }
        }
        return result;
    }

    /**
     * Compares the two given strings for order, handling null as necessary.
     *
     * @return a negative integer, zero, or a positive integer as the first
     *         object is less than, equal to, or greater than the second object.
     */
    private int nullSafeCompare(String first, String second) {
        int result = nullCompare(first, second);
        return result == NON_NULLS ? StringUtils.compare(first, second) : result;
    }

    /**
     * Partially compares the two given objects for order, handling null as
     * necessary.
     *
     * @return a -1 if the first object is null but not the second, 0 if both
     *         objects are identical, 1 if the second object is null but not the
     *         first. Otherwise, {@link #NON_NULLS} is returned which means both
     *         objects are non-null but not identical.
     */
    private int nullCompare(Object first, Object second) {
        if (first == second) {
            return 0;
        }
        if (first == null) {
            return -1;
        }
        if (second == null) {
            return 1;
        }
        return NON_NULLS;
    }
}
