/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.validation.impl;

import ru.runa.wfe.validation.FieldValidator;

/**
 * Base class for range based validators.
 */
public abstract class AbstractRangeValidator<T extends Object> extends FieldValidator {

    protected Comparable getComparableValue() {
        return (Comparable<T>) getFieldValue();
    }

    protected T getMinComparatorValue() {
        return (T) getParameterNotNull(Object.class, "min");
    }

    protected T getMaxComparatorValue() {
        return (T) getParameterNotNull(Object.class, "max");
    }

    @Override
    public void validate() {
        Comparable<T> value = getComparableValue();
        // if there is no value - don't do comparison
        // if a value is required, a required validator should be added to the
        // field
        if (value == null) {
            return;
        }

        boolean inclusive = getParameter(boolean.class, "inclusive", true);
        T minValue = getMinComparatorValue();
        if (minValue != null) {
            if (inclusive) {
                if (value.compareTo(minValue) < 0) {
                    addError();
                }
            } else {
                if (value.compareTo(minValue) <= 0) {
                    addError();
                }
            }
        }

        T maxValue = getMaxComparatorValue();
        if (maxValue != null) {
            if (inclusive) {
                if (value.compareTo(maxValue) > 0) {
                    addError();
                }
            } else {
                if (value.compareTo(maxValue) >= 0) {
                    addError();
                }
            }
        }
    }

}
