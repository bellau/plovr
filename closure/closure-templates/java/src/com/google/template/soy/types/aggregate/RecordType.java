/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.template.soy.types.aggregate;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSortedMap;
import com.google.template.soy.base.SoyBackendKind;
import com.google.template.soy.data.SoyRecord;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.types.SoyObjectType;
import com.google.template.soy.types.SoyType;

import java.util.Map;

/**
 * Dict type - classic dictionary type with string keys. Only works with field
 * (dot) access.
 *
 * <p> Important: Do not use outside of Soy code (treat as superpackage-private).
 *
 * @author Talin
 */
public final class RecordType implements SoyObjectType {

  private final ImmutableSortedMap<String, SoyType> members;


  private RecordType(Map<String, SoyType> members) {
    this.members = ImmutableSortedMap.copyOf(members);
  }


  public static RecordType of(Map<String, SoyType> members) {
    return new RecordType(members);
  }


  @Override public Kind getKind() {
    return Kind.RECORD;
  }


  @Override public boolean isAssignableFrom(SoyType srcType) {
    if (srcType.getKind() == Kind.RECORD) {
      RecordType srcRecord = (RecordType) srcType;
      // The source record must have at least all of the members in the dest
      // record.
      for (Map.Entry<String, SoyType> entry : members.entrySet()) {
        SoyType fieldType = srcRecord.members.get(entry.getKey());
        if (fieldType == null || !entry.getValue().isAssignableFrom(fieldType)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }


  @Override public boolean isInstance(SoyValue value) {
    return value instanceof SoyRecord;
  }


  @Override public String getName() {
    return "Record";
  }


  /** Return the members of this record type. */
  public ImmutableSortedMap<String, SoyType> getMembers() {
    return members;
  }


  @Override public String getNameForBackend(SoyBackendKind backend) {
    return "Object";
  }


  @Override public SoyType getFieldType(String fieldName) {
    return members.get(fieldName);
  }


  @Override public String getFieldAccessor(String fieldName, SoyBackendKind backendKind) {
    if (backendKind == SoyBackendKind.JS_SRC) {
      return "." + fieldName;
    } else {
      throw new UnsupportedOperationException();
    }
  }


  @Override public String getFieldImport(String fieldName, SoyBackendKind backend) {
    return null;
  }


  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    boolean first = true;
    for (Map.Entry<String, SoyType> entry : members.entrySet()) {
      if (first) {
        first = false;
      } else {
        sb.append(", ");
      }
      sb.append(entry.getKey());
      sb.append(": ");
      sb.append(entry.getValue().toString());
    }
    sb.append("]");
    return sb.toString();
  }


  @Override public boolean equals(Object other) {
    return other != null
        && other.getClass() == this.getClass()
        && ((RecordType) other).members.equals(members);
  }


  @Override public int hashCode() {
    return Objects.hashCode(this.getClass(), members);
  }
}
