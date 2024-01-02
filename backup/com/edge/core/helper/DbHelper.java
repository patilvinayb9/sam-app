package com.edge.core.helper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface DbHelper {

    default String getValue(String str, String delimiter) {
        if (delimiter == null) {
            return str;
        } else {
            return str.split(delimiter)[0].trim();
        }
    }

    default String addINClause(String[] array, String delimiter) {
        String val = "";
        for (String str : array) {
            val += ",'" + getValue(str, delimiter) + "'";
        }
        if (val.length() > 1) {
            val = val.substring(1);
        }
        return val;
    }

    default String addINClause(Collection<String> array, String delimiter) {
        String val = "";
        for (String str : array) {
            val += ",'" + getValue(str, delimiter) + "'";
        }
        if (val.length() > 1) {
            val = val.substring(1);
        }
        return val;
    }

    ;


    default String addINClause(String colName, String[] array) {
        return addINClause(colName, array, null);
    }

    String addINClause(String colName, String[] array, String delimiter);

    String addBETWEENClause(String colName, Integer from, Integer to);

    String addBETWEENClause(String colName, BigDecimal from, BigDecimal to);

    String addBETWEENClause(String colName, Date from, Date to);

}