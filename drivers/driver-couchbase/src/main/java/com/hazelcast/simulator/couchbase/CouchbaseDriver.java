/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hazelcast.simulator.couchbase;

import com.couchbase.client.java.CouchbaseCluster;
import com.hazelcast.simulator.agent.workerprocess.WorkerParameters;
import com.hazelcast.simulator.drivers.Driver;

import java.io.IOException;

import static com.hazelcast.simulator.utils.FileUtils.fileAsText;
import static java.lang.String.format;

public class CouchbaseDriver extends Driver<CouchbaseCluster> {

    private CouchbaseCluster cluster;

    @Override
    public WorkerParameters loadWorkerParameters(String workerType, int agentIndex) {
        WorkerParameters params = new WorkerParameters()
                .setAll(properties)
                .set("WORKER_TYPE", workerType)
                .set("file:log4j.xml", loadLog4jConfig());

        if ("javaclient".equals(workerType)) {
            loadClientParameters(params);
        } else {
            throw new IllegalArgumentException(format("Unsupported workerType [%s]", workerType));
        }

        return params;
    }

    private void loadClientParameters(WorkerParameters params) {
        params.set("JVM_OPTIONS", get("CLIENT_ARGS", ""))
                .set("nodes", fileAsText("nodes.txt"))
                .set("file:worker.sh", loadWorkerScript("javaclient"));
    }

    @Override
    public CouchbaseCluster getDriverInstance() {
        return cluster;
    }

    @Override
    public void startDriverInstance() throws Exception {
        String[] nodes = get("nodes").split(",");
        this.cluster = CouchbaseCluster.create(nodes);
    }

    @Override
    public void close() throws IOException {
        if (cluster != null) {
            cluster.disconnect();
        }
    }
}
