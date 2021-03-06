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

package software.amazon.awssdk.services.stepfunctions.builder.states;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.amazon.awssdk.annotation.SdkInternalApi;
import software.amazon.awssdk.services.stepfunctions.builder.internal.PropertyNames;

/**
 * Base class for states that allow transitions to either another state or
 * machine termination.
 */
@SdkInternalApi
abstract class TransitionStateBuilder implements State.Builder {

    public abstract TransitionStateBuilder transition(Transition.Builder builder);

    @JsonProperty(PropertyNames.END)
    private void setEnd(boolean isEnd) {
        if (isEnd) {
            transition(EndTransition.builder());
        }
    }

    @JsonProperty(PropertyNames.NEXT)
    private void setNext(String nextStateName) {
        transition(NextStateTransition.builder().nextStateName(nextStateName));
    }
}
