/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.tg.jenkins.util

import org.wso2.tg.jenkins.Logger
import org.wso2.tg.jenkins.Properties

/**
 * Increases TG runtime memory.
 *
 * @param min minimum memory
 * @param max maximum memory
 */
def increaseTestGridRuntimeMemory(min, max) {
    def props = Properties.instance
    sh """
          echo ${props.TESTGRID_NAME}
          cd ${props.TESTGRID_DIST_LOCATION}
          cd ${props.TESTGRID_NAME}
          sed -i 's/-Xms256m -Xmx1024m/-Xmx${min} -Xms${max}/g' testgrid
        """
}

/**
 * Unstashing the stashed testplans if not available.
 * @param testplanDirectory directory check whether testplans are available
 */
def unstashTestPlansIfNotAvailable(def testplanDirectory) {
    def props = Properties.instance
    def log = new Logger()
    if (!fileExists(testplanDirectory)) {
        log.info("test-plans directory not found, unstashing the testplans to ${props.WORKSPACE}")
        dir("${props.WORKSPACE}") {
            unstash name: "test-plans"
        }
    }
}
