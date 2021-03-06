/*
 * Copyright 2020 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"; you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.parser.tree;

import com.google.errorprone.annotations.Immutable;
import io.confluent.ksql.parser.NodeLocation;
import java.util.Objects;
import java.util.Optional;

@Immutable
public class AssertValues extends AssertStatement {

  private final InsertValues statement;

  public AssertValues(
      final Optional<NodeLocation> location,
      final InsertValues statement
  ) {
    super(location);
    this.statement = Objects.requireNonNull(statement, "statement");
  }

  public InsertValues getStatement() {
    return statement;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStatement());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final AssertValues that = (AssertValues) o;
    return Objects.equals(getStatement(), that.getStatement());
  }

  @Override
  public String toString() {
    return "AssertValues{"
        + "statement=" + getStatement()
        + '}';
  }

  @Override
  protected <R, C> R accept(final AstVisitor<R, C> visitor, final C context) {
    return visitor.visitAssertValues(this, context);
  }
}
