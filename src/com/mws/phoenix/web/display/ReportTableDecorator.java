package com.mws.phoenix.web.display;

import java.util.Map;

import org.displaytag.decorator.TableDecorator;

public class ReportTableDecorator extends TableDecorator {

    public String startRow() {
        Map map = (Map)this.getCurrentRowObject();
        if (map.containsKey("TOTAL")) {
            return "<tr><th rowspan=\"100\"><hr /></th></tr>";
        } else {
            return super.startRow();
        }
    }

    public String finishRow() {
        return startRow();
    }
    
}
