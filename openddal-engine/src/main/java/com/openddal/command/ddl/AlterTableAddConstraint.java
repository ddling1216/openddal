/*
 * Copyright 2014-2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.openddal.command.ddl;

import java.util.ArrayList;

import com.openddal.command.expression.Expression;
import com.openddal.dbobject.index.Index;
import com.openddal.dbobject.schema.Schema;
import com.openddal.dbobject.table.IndexColumn;
import com.openddal.engine.Session;
import com.openddal.excutor.effects.AlterTableAddConstraintExecutor;
import com.openddal.util.New;

/**
 * This class represents the statement ALTER TABLE ADD CONSTRAINT
 *
 * @author <a href="mailto:jorgie.mail@gmail.com">jorgie li</a>
 */
public class AlterTableAddConstraint extends SchemaCommand {

    /**
     * The constraint type name for check constraints.
     */
    public static final String CHECK = "CHECK";

    /**
     * The constraint type name for referential constraints.
     */
    public static final String REFERENTIAL = "REFERENTIAL";

    /**
     * The constraint type name for unique constraints.
     */
    public static final String UNIQUE = "UNIQUE";

    /**
     * The constraint type name for primary key constraints.
     */
    public static final String PRIMARY_KEY = "PRIMARY KEY";

    /**
     * referential constraint
     * The action is to restrict the operation.
     */
    public static final int RESTRICT = 0;

    /**
     * referential constraint
     * The action is to cascade the operation.
     */
    public static final int CASCADE = 1;

    /**
     * referential constraint
     * The action is to set the value to the default value.
     */
    public static final int SET_DEFAULT = 2;

    /**
     * referential constraint
     * The action is to set the value to NULL.
     */
    public static final int SET_NULL = 3;

    private final boolean ifNotExists;
    private int type;
    private String constraintName;
    private String tableName;
    private IndexColumn[] indexColumns;
    private int deleteAction;
    private int updateAction;
    private Schema refSchema;
    private String refTableName;
    private IndexColumn[] refIndexColumns;
    private Expression checkExpression;
    private Index index, refIndex;
    private String comment;
    private boolean checkExisting;
    private boolean primaryKeyHash;
    private ArrayList<Index> createdIndexes = New.arrayList();
    private AlterTableAddConstraintExecutor executor;

    public AlterTableAddConstraint(Session session, Schema schema, boolean ifNotExists) {
        super(session, schema);
        this.ifNotExists = ifNotExists;
    }

    @Override
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRefTableName() {
        return refTableName;
    }

    public IndexColumn[] getIndexColumns() {
        return indexColumns;
    }

    public void setIndexColumns(IndexColumn[] indexColumns) {
        this.indexColumns = indexColumns;
    }

    /**
     * @return the refSchema
     */
    public Schema getRefSchema() {
        return refSchema;
    }

    /**
     * Set the referenced table.
     *
     * @param refSchema the schema
     * @param ref       the table name
     */
    public void setRefTableName(Schema refSchema, String ref) {
        this.refSchema = refSchema;
        this.refTableName = ref;
    }

    /**
     * @return the ifNotExists
     */
    public boolean isIfNotExists() {
        return ifNotExists;
    }

    /**
     * @return the constraintName
     */
    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    /**
     * @return the deleteAction
     */
    public int getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(int action) {
        this.deleteAction = action;
    }

    /**
     * @return the updateAction
     */
    public int getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(int action) {
        this.updateAction = action;
    }

    /**
     * @return the refIndexColumns
     */
    public IndexColumn[] getRefIndexColumns() {
        return refIndexColumns;
    }

    public void setRefIndexColumns(IndexColumn[] indexColumns) {
        this.refIndexColumns = indexColumns;
    }

    /**
     * @return the checkExpression
     */
    public Expression getCheckExpression() {
        return checkExpression;
    }

    public void setCheckExpression(Expression expression) {
        this.checkExpression = expression;
    }

    /**
     * @return the index
     */
    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    /**
     * @return the refIndex
     */
    public Index getRefIndex() {
        return refIndex;
    }

    public void setRefIndex(Index refIndex) {
        this.refIndex = refIndex;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the checkExisting
     */
    public boolean isCheckExisting() {
        return checkExisting;
    }

    public void setCheckExisting(boolean b) {
        this.checkExisting = b;
    }

    /**
     * @return the primaryKeyHash
     */
    public boolean isPrimaryKeyHash() {
        return primaryKeyHash;
    }

    public void setPrimaryKeyHash(boolean b) {
        this.primaryKeyHash = b;
    }

    /**
     * @return the createdIndexes
     */
    public ArrayList<Index> getCreatedIndexes() {
        return createdIndexes;
    }

    @Override
    public AlterTableAddConstraintExecutor getExecutor() {
        if(executor == null) {
            executor = new AlterTableAddConstraintExecutor(this);
        }
        return executor;
    }
}
