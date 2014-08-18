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

package com.google.template.soy.exprtree;

import com.google.common.base.Preconditions;

/**
 * Base class which represents access to a field or element of an aggregate value,
 * such as an object or collection. Syntactically, data accesses are always
 * suffix operators such as base.fieldName or base[expression].
 *
 * @author Talin
 */
public abstract class DataAccessNode extends AbstractParentExprNode {

  protected final boolean isNullSafe;

  /**
   * @param base The base expression, that is a reference to the object
   *     containing the named field.
   * @param isNullSafe If true, checks during evaluation whether the base expression is null
   *     and returns null instead of causing an invalid dereference.
   */
  public DataAccessNode(ExprNode base, boolean isNullSafe) {
    Preconditions.checkArgument(base != null);
    addChild(base);
    this.isNullSafe = isNullSafe;
  }


  /** Returns the base expression from which we're looking up the named field. */
  public ExprNode getBaseExprChild() {
    return this.getChild(0);
  }


  /** Returns whether this field reference is null-safe. */
  public boolean isNullSafe() {
    return isNullSafe;
  }


  /**
   * Returns the source string for the part of the expression that accesses
   * the item - in other words, not including the base expression. This is
   * intended for use in reporting errors.
   */
  public abstract String getSourceStringSuffix();


  @Override public String toSourceString() {
    return getBaseExprChild().toSourceString() + getSourceStringSuffix();
  }
}
