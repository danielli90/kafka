/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package org.apache.kafka.copycat.source;

import org.apache.kafka.common.annotation.InterfaceStability;
import org.apache.kafka.copycat.connector.CopycatRecord;

/**
 * <p>
 * SourceRecords are generated by SourceTasks and passed to Copycat for storage in
 * Kafka. In addition to the standard fields in CopycatRecord which specify where data is stored
 * in Kafka, they also include a sourcePartition and sourceOffset.
 * </p>
 * <p>
 * The sourcePartition represents a single input sourcePartition that the record came from (e.g. a filename, table
 * name, or topic-partition). The sourceOffset represents a position in that sourcePartition which can be used
 * to resume consumption of data.
 * </p>
 * <p>
 * These values can have arbitrary structure and should be represented using
 * org.apache.kafka.copycat.data objects (or primitive values). For example, a database connector
 * might specify the sourcePartition as a record containing { "db": "database_name", "table":
 * "table_name"} and the sourceOffset as a Long containing the timestamp of the row.
 * </p>
 */
@InterfaceStability.Unstable
public class SourceRecord extends CopycatRecord {
    private final Object sourcePartition;
    private final Object sourceOffset;

    public SourceRecord(Object sourcePartition, Object sourceOffset, String topic, Integer partition, Object value) {
        this(sourcePartition, sourceOffset, topic, partition, null, value);
    }

    public SourceRecord(Object sourcePartition, Object sourceOffset, String topic, Object value) {
        this(sourcePartition, sourceOffset, topic, null, null, value);
    }

    public SourceRecord(Object sourcePartition, Object sourceOffset, String topic, Integer partition,
                        Object key, Object value) {
        super(topic, partition, key, value);
        this.sourcePartition = sourcePartition;
        this.sourceOffset = sourceOffset;
    }

    public Object getSourcePartition() {
        return sourcePartition;
    }

    public Object getSourceOffset() {
        return sourceOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        SourceRecord that = (SourceRecord) o;

        if (sourceOffset != null ? !sourceOffset.equals(that.sourceOffset) : that.sourceOffset != null)
            return false;
        if (sourcePartition != null ? !sourcePartition.equals(that.sourcePartition) : that.sourcePartition != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sourcePartition != null ? sourcePartition.hashCode() : 0);
        result = 31 * result + (sourceOffset != null ? sourceOffset.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SourceRecord{" +
                "sourcePartition=" + sourcePartition +
                ", sourceOffset=" + sourceOffset +
                "} " + super.toString();
    }
}
