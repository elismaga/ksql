/*
 * Copyright 2019 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.execution.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.confluent.ksql.execution.timestamp.TimestampColumn;
import io.confluent.ksql.schema.ksql.LogicalSchema;
import io.confluent.ksql.serde.WindowInfo;
import java.util.Objects;
import java.util.Optional;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.streams.kstream.Windowed;

public final class WindowedTableSource extends SourceStep<KTableHolder<Windowed<Struct>>> {

  private final WindowInfo windowInfo;

  public WindowedTableSource(
      @JsonProperty(value = "properties", required = true) final ExecutionStepPropertiesV1 props,
      @JsonProperty(value = "topicName", required = true) final String topicName,
      @JsonProperty(value = "formats", required = true) final Formats formats,
      @JsonProperty(value = "windowInfo", required = true) final WindowInfo windowInfo,
      @JsonProperty("timestampColumn") final Optional<TimestampColumn> timestampColumn,
      @JsonProperty(value = "sourceSchema", required = true) final LogicalSchema sourceSchema
  ) {
    super(props, topicName, formats, timestampColumn, sourceSchema);
    this.windowInfo = Objects.requireNonNull(windowInfo, "windowInfo");
  }

  public WindowInfo getWindowInfo() {
    return windowInfo;
  }

  @Override
  public KTableHolder<Windowed<Struct>> build(final PlanBuilder builder) {
    return builder.visitWindowedTableSource(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final WindowedTableSource that = (WindowedTableSource) o;
    return Objects.equals(properties, that.properties)
        && Objects.equals(topicName, that.topicName)
        && Objects.equals(formats, that.formats)
        && Objects.equals(timestampColumn, that.timestampColumn)
        && Objects.equals(sourceSchema, that.sourceSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        properties,
        topicName,
        formats,
        timestampColumn,
        sourceSchema
    );
  }
}
