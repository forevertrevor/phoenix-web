package com.mws.phoenix.web.display;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

public class AreaDecorator implements ColumnDecorator {

    private final NumberFormat num = new DecimalFormat("#,##0 cm<sup>2</sup>");
    
    public String decorate(Object columnValue) throws DecoratorException {
        if (columnValue == null) {
            return num.format(new Double(0));
        }else if (columnValue instanceof Number) {
            return num.format(columnValue);
        } else {
            return columnValue.toString();
        }
    }

}
