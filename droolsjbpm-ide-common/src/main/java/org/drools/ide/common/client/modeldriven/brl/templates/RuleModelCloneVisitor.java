/*
 * Copyright 2011 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.drools.ide.common.client.modeldriven.brl.templates;

import java.util.HashMap;
import java.util.Map;

import org.drools.ide.common.client.modeldriven.brl.ActionFieldValue;
import org.drools.ide.common.client.modeldriven.brl.ActionInsertFact;
import org.drools.ide.common.client.modeldriven.brl.ActionRetractFact;
import org.drools.ide.common.client.modeldriven.brl.ActionSetField;
import org.drools.ide.common.client.modeldriven.brl.ActionUpdateField;
import org.drools.ide.common.client.modeldriven.brl.CEPWindow;
import org.drools.ide.common.client.modeldriven.brl.CompositeFactPattern;
import org.drools.ide.common.client.modeldriven.brl.CompositeFieldConstraint;
import org.drools.ide.common.client.modeldriven.brl.ConnectiveConstraint;
import org.drools.ide.common.client.modeldriven.brl.DSLSentence;
import org.drools.ide.common.client.modeldriven.brl.DSLVariableValue;
import org.drools.ide.common.client.modeldriven.brl.ExpressionFormLine;
import org.drools.ide.common.client.modeldriven.brl.FactPattern;
import org.drools.ide.common.client.modeldriven.brl.FieldConstraint;
import org.drools.ide.common.client.modeldriven.brl.FreeFormLine;
import org.drools.ide.common.client.modeldriven.brl.FromAccumulateCompositeFactPattern;
import org.drools.ide.common.client.modeldriven.brl.FromCollectCompositeFactPattern;
import org.drools.ide.common.client.modeldriven.brl.FromCompositeFactPattern;
import org.drools.ide.common.client.modeldriven.brl.IAction;
import org.drools.ide.common.client.modeldriven.brl.IFactPattern;
import org.drools.ide.common.client.modeldriven.brl.IPattern;
import org.drools.ide.common.client.modeldriven.brl.RuleAttribute;
import org.drools.ide.common.client.modeldriven.brl.RuleMetadata;
import org.drools.ide.common.client.modeldriven.brl.RuleModel;
import org.drools.ide.common.client.modeldriven.brl.SingleFieldConstraint;
import org.drools.ide.common.client.modeldriven.brl.SingleFieldConstraintEBLeftSide;

/**
 * A Rule Model Visitor to create a clone TODO Clone LHS of model...
 */
public class RuleModelCloneVisitor {

    private Object visit(Object o) {
        if ( o == null ) {
            return null;
        }
        if ( o instanceof RuleModel ) {
            return visitRuleModel( (RuleModel) o );
        } else if ( o instanceof RuleAttribute ) {
            return visitRuleAttribute( (RuleAttribute) o );
        } else if ( o instanceof RuleMetadata ) {
            return visitRuleMetadata( (RuleMetadata) o );
        } else if ( o instanceof FactPattern ) {
            return visitFactPattern( (FactPattern) o );
        } else if ( o instanceof CompositeFieldConstraint ) {
            return visitCompositeFieldConstraint( (CompositeFieldConstraint) o );
        } else if ( o instanceof SingleFieldConstraintEBLeftSide ) {
            return visitSingleFieldConstraint( (SingleFieldConstraintEBLeftSide) o );
        } else if ( o instanceof SingleFieldConstraint ) {
            return visitSingleFieldConstraint( (SingleFieldConstraint) o );
        } else if ( o instanceof ExpressionFormLine ) {
            return visitExpressionFormLine( (ExpressionFormLine) o );
        } else if ( o instanceof ConnectiveConstraint ) {
            return visitConnectiveConstraint( (ConnectiveConstraint) o );
        } else if ( o instanceof CompositeFactPattern ) {
            return visitCompositeFactPattern( (CompositeFactPattern) o );
        } else if ( o instanceof FreeFormLine ) {
            return visitFreeFormLine( (FreeFormLine) o );
        } else if ( o instanceof FromAccumulateCompositeFactPattern ) {
            return visitFromAccumulateCompositeFactPattern( (FromAccumulateCompositeFactPattern) o );
        } else if ( o instanceof FromCollectCompositeFactPattern ) {
            return visitFromCollectCompositeFactPattern( (FromCollectCompositeFactPattern) o );
        } else if ( o instanceof FromCompositeFactPattern ) {
            return visitFromCompositeFactPattern( (FromCompositeFactPattern) o );
        } else if ( o instanceof DSLSentence ) {
            return visitDSLSentence( (DSLSentence) o );
        } else if ( o instanceof DSLVariableValue ) {
            return visitDSLVariableValue( (DSLVariableValue) o );
        } else if ( o instanceof ActionInsertFact ) {
            return visitActionFieldList( (ActionInsertFact) o );
        } else if ( o instanceof ActionUpdateField ) {
            return visitActionFieldList( (ActionUpdateField) o );
        } else if ( o instanceof ActionSetField ) {
            return visitActionFieldList( (ActionSetField) o );
        } else if ( o instanceof ActionRetractFact ) {
            return visitActionRetractFact( (ActionRetractFact) o );
        }
        throw new IllegalArgumentException( "Class " + o.getClass().getName() + " is not recognised" );
    }

    private RuleAttribute visitRuleAttribute(RuleAttribute attr) {
        RuleAttribute clone = new RuleAttribute();
        clone.attributeName = attr.attributeName;
        clone.value = attr.value;
        return clone;
    }

    private RuleMetadata visitRuleMetadata(RuleMetadata md) {
        RuleMetadata clone = new RuleMetadata();
        clone.attributeName = md.attributeName;
        clone.value = md.value;
        return clone;
    }

    //ActionInsertFact, ActionSetField, ActionpdateField
    private ActionInsertFact visitActionFieldList(ActionInsertFact afl) {
        ActionInsertFact clone = new ActionInsertFact();
        clone.factType = afl.factType;
        clone.setBoundName( afl.getBoundName() );
        for ( ActionFieldValue afv : afl.fieldValues ) {
            ActionFieldValue afvClone = new ActionFieldValue();
            afvClone.setField( afv.getField() );
            afvClone.setNature( afv.getNature() );
            afvClone.setType( afv.getType() );
            afvClone.setValue( afv.getValue() );
            clone.addFieldValue( afvClone );
        }
        return clone;
    }

    private ActionSetField visitActionFieldList(ActionSetField afl) {
        ActionSetField clone = new ActionSetField();
        clone.variable = afl.variable;
        for ( ActionFieldValue afv : afl.fieldValues ) {
            ActionFieldValue afvClone = new ActionFieldValue();
            afvClone.setField( afv.getField() );
            afvClone.setNature( afv.getNature() );
            afvClone.setType( afv.getType() );
            afvClone.setValue( afv.getValue() );
            clone.addFieldValue( afvClone );
        }
        return clone;
    }

    private ActionUpdateField visitActionFieldList(ActionUpdateField afl) {
        ActionUpdateField clone = new ActionUpdateField();
        clone.variable = afl.variable;
        for ( ActionFieldValue afv : afl.fieldValues ) {
            ActionFieldValue afvClone = new ActionFieldValue();
            afvClone.setField( afv.getField() );
            afvClone.setNature( afv.getNature() );
            afvClone.setType( afv.getType() );
            afvClone.setValue( afv.getValue() );
            clone.addFieldValue( afvClone );
        }
        return clone;
    }

    private ActionRetractFact visitActionRetractFact(ActionRetractFact arf) {
        ActionRetractFact clone = new ActionRetractFact();
        clone.variableName = arf.variableName;
        return clone;
    }

    private CompositeFactPattern visitCompositeFactPattern(CompositeFactPattern pattern) {
        CompositeFactPattern clone = new CompositeFactPattern();
        clone.type = pattern.type;
        if ( pattern.getPatterns() != null ) {
            for ( IFactPattern fp : pattern.getPatterns() ) {
                clone.addFactPattern( (IFactPattern) visit( fp ) );
            }
        }
        return clone;
    }

    private CompositeFieldConstraint visitCompositeFieldConstraint(CompositeFieldConstraint cfc) {
        CompositeFieldConstraint clone = new CompositeFieldConstraint();
        clone.compositeJunctionType = cfc.compositeJunctionType;
        if ( cfc.constraints != null ) {
            clone.constraints = new FieldConstraint[cfc.constraints.length];
            for ( int i = 0; i < cfc.constraints.length; i++ ) {
                FieldConstraint fc = cfc.constraints[i];
                clone.constraints[i] = (FieldConstraint) visit( fc );
            }
        }
        return clone;
    }

    private DSLSentence visitDSLSentence(final DSLSentence sentence) {
        DSLSentence clone = new DSLSentence();
        clone.setDefinition( sentence.getDefinition() );
        for ( DSLVariableValue value : sentence.getValues() ) {
            clone.getValues().add( (DSLVariableValue) visit( value ) );
        }
        return clone;
    }

    private DSLVariableValue visitDSLVariableValue(DSLVariableValue value) {
        DSLVariableValue clone = new DSLVariableValue();
        clone.setValue( value.getValue() );
        return clone;
    }

    private FactPattern visitFactPattern(FactPattern pattern) {
        FactPattern clone = new FactPattern();
        clone.setBoundName( pattern.getBoundName() );
        clone.setFactType( pattern.getFactType() );
        clone.setNegated( pattern.isNegated() );

        CEPWindow cloneCEPWindow = new CEPWindow();
        cloneCEPWindow.setOperator( pattern.getWindow().getOperator() );
        cloneCEPWindow.setParameters( cloneCEPWindowParameters( pattern.getWindow() ) );
        clone.setWindow( cloneCEPWindow );

        for ( FieldConstraint fc : pattern.getFieldConstraints() ) {
            clone.addConstraint( (FieldConstraint) visit( fc ) );
        }
        return clone;
    }

    private Map<String, String> cloneCEPWindowParameters(CEPWindow window) {
        Map<String, String> clone = new HashMap<String, String>();
        for ( Map.Entry<String, String> entry : window.getParameters().entrySet() ) {
            clone.put( entry.getKey(),
                       entry.getValue() );
        }
        return clone;
    }

    private FreeFormLine visitFreeFormLine(FreeFormLine ffl) {
        FreeFormLine clone = new FreeFormLine();
        clone.text = ffl.text;
        return clone;
    }

    private FromAccumulateCompositeFactPattern visitFromAccumulateCompositeFactPattern(FromAccumulateCompositeFactPattern pattern) {
        FromAccumulateCompositeFactPattern clone = new FromAccumulateCompositeFactPattern();
        clone.setActionCode( pattern.getActionCode() );
        clone.setExpression( (ExpressionFormLine) visit( pattern.getExpression() ) );
        clone.setFactPattern( (FactPattern) visit( pattern.getFactPattern() ) );
        clone.setFunction( pattern.getFunction() );
        clone.setInitCode( pattern.getInitCode() );
        clone.setResultCode( pattern.getResultCode() );
        clone.setReverseCode( pattern.getReverseCode() );
        clone.setSourcePattern( (IPattern) visit( pattern.getSourcePattern() ) );
        return clone;
    }

    private FromCollectCompositeFactPattern visitFromCollectCompositeFactPattern(FromCollectCompositeFactPattern pattern) {
        FromCollectCompositeFactPattern clone = new FromCollectCompositeFactPattern();
        clone.setExpression( (ExpressionFormLine) visit( pattern.getExpression() ) );
        clone.setFactPattern( (FactPattern) visit( pattern.getFactPattern() ) );
        clone.setRightPattern( (IPattern) visit( pattern.getRightPattern() ) );
        return clone;
    }

    private FromCompositeFactPattern visitFromCompositeFactPattern(FromCompositeFactPattern pattern) {
        FromCompositeFactPattern clone = new FromCompositeFactPattern();
        clone.setExpression( (ExpressionFormLine) visit( pattern.getExpression() ) );
        clone.setFactPattern( (FactPattern) visit( pattern.getFactPattern() ) );
        return clone;
    }

    public RuleModel visitRuleModel(RuleModel model) {
        RuleModel clone = new RuleModel();
        clone.modelVersion = model.modelVersion;
        clone.name = model.name;
        clone.parentName = model.parentName;
        clone.setNegated( model.isNegated() );

        if ( model.attributes != null ) {
            clone.attributes = new RuleAttribute[model.attributes.length];
            for ( int i = 0; i < model.attributes.length; i++ ) {
                RuleAttribute attr = model.attributes[i];
                clone.attributes[i] = (RuleAttribute) visit( attr );
            }
        }
        if ( model.metadataList != null ) {
            clone.metadataList = new RuleMetadata[model.metadataList.length];
            for ( int i = 0; i < model.metadataList.length; i++ ) {
                RuleMetadata md = model.metadataList[i];
                clone.metadataList[i] = (RuleMetadata) visit( md );
            }
        }
        if ( model.lhs != null ) {
            clone.lhs = new IPattern[model.lhs.length];
            for ( int i = 0; i < model.lhs.length; i++ ) {
                IPattern pattern = model.lhs[i];
                clone.lhs[i] = (IPattern) visit( pattern );
            }
        }
        if ( model.rhs != null ) {
            clone.rhs = new IAction[model.rhs.length];
            for ( int i = 0; i < model.rhs.length; i++ ) {
                IAction action = model.rhs[i];
                clone.rhs[i] = (IAction) visit( action );
            }
        }
        return clone;
    }

    private SingleFieldConstraint visitSingleFieldConstraint(SingleFieldConstraint sfc) {
        SingleFieldConstraint clone = new SingleFieldConstraint();
        clone.setConstraintValueType( sfc.getConstraintValueType() );
        clone.setExpressionValue( (ExpressionFormLine) visit( sfc.getExpressionValue() ) );
        clone.setFieldBinding( sfc.getFieldBinding() );
        clone.setFactType( sfc.getFactType() );
        clone.setFieldName( sfc.getFieldName() );
        clone.setFieldType( sfc.getFieldType() );
        clone.setOperator( sfc.getOperator() );
        for ( Map.Entry<String, String> entry : sfc.getParameters().entrySet() ) {
            clone.setParameter( entry.getKey(),
                                entry.getValue() );
        }
        clone.setValue( sfc.getValue() );

        if ( sfc.connectives != null ) {
            clone.connectives = new ConnectiveConstraint[sfc.connectives.length];
            for ( int i = 0; i < sfc.connectives.length; i++ ) {
                clone.connectives[i] = (ConnectiveConstraint) visit( sfc.connectives[i] );
            }
        }
        return clone;
    }

    private ExpressionFormLine visitExpressionFormLine(ExpressionFormLine efl) {
        ExpressionFormLine clone = new ExpressionFormLine( efl );
        clone.setBinding( efl.getBinding() );
        return clone;
    }

    private ConnectiveConstraint visitConnectiveConstraint(ConnectiveConstraint cc) {
        ConnectiveConstraint clone = new ConnectiveConstraint();
        clone.setConstraintValueType( cc.getConstraintValueType() );
        clone.setExpressionValue( (ExpressionFormLine) visit( cc.getExpressionValue() ) );
        clone.setFieldName( cc.getFieldName() );
        clone.setFieldType( cc.getFieldType() );
        clone.setOperator( cc.getOperator() );
        for ( Map.Entry<String, String> entry : cc.getParameters().entrySet() ) {
            clone.setParameter( entry.getKey(),
                                entry.getValue() );
        }
        clone.setValue( cc.getValue() );
        return clone;
    }

    private SingleFieldConstraintEBLeftSide visitSingleFieldConstraint(SingleFieldConstraintEBLeftSide sfexp) {
        SingleFieldConstraintEBLeftSide clone = new SingleFieldConstraintEBLeftSide();
        clone.setConstraintValueType( sfexp.getConstraintValueType() );
        clone.setExpressionLeftSide( (ExpressionFormLine) visit( sfexp.getExpressionLeftSide() ) );
        clone.setExpressionValue( (ExpressionFormLine) visit( sfexp.getExpressionValue() ) );
        clone.setFieldBinding( sfexp.getFieldBinding() );
        clone.setFactType( sfexp.getFactType() );
        clone.setFieldName( sfexp.getFieldName() );
        clone.setFieldType( sfexp.getFieldType() );
        clone.setOperator( sfexp.getOperator() );
        for ( Map.Entry<String, String> entry : sfexp.getParameters().entrySet() ) {
            clone.setParameter( entry.getKey(),
                                entry.getValue() );
        }
        clone.setParent( sfexp.getParent() );
        clone.setValue( sfexp.getValue() );

        if ( sfexp.connectives != null ) {
            clone.connectives = new ConnectiveConstraint[sfexp.connectives.length];
            for ( int i = 0; i < sfexp.connectives.length; i++ ) {
                clone.connectives[i] = (ConnectiveConstraint) visit( sfexp.connectives[i] );
            }
        }
        return clone;
    }

}
