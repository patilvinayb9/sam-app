package com.edge.core.helper;

import com.edge.core.utils.CoreDateUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component("sqlDbHelper")
public class SqlDbHelperImpl implements DbHelper {

    public String addINClause(String colName, String[] array) {
        return addINClause(colName, array, null);
    }

    @Override
    public String addINClause(String colName, String[] array, String delimiter) {
        String hql = "";
        if (array != null) {
            if (array.length == 1) {
                if (!array[0].equals("Any")) {
                    hql = " and " + colName + " = '" + getValue(array[0], delimiter) + "' ";
                }
            } else if (array.length > 1) {
                if (!array[0].equals("Any")) {
                    hql = " and " + colName + " in (" + addINClause(array, delimiter) + ")";
                }
            }
        }
        return hql;
    }

    @Override
    public String addBETWEENClause(String colName, Integer from, Integer to) {
        String hql = "";
        if (from != null) {
            hql += " and " + colName + " >= " + from + " ";
        }
        if (to != null) {
            hql += " and " + colName + " <= " + to + " ";
        }
        return hql;
    }

    @Override
    public String addBETWEENClause(String colName, BigDecimal from, BigDecimal to) {
        String hql = "";
        if (from != null) {
            hql += " and " + colName + " >= " + from + " ";
        }
        if (to != null) {
            hql += " and " + colName + " <= " + to + " ";
        }
        return hql;
    }

    @Override
    public String addBETWEENClause(String colName, Date from, Date to) {
        String hql = "";
        if (from != null) {
            hql += " and " + colName + " >= '" + CoreDateUtils.dateToStandardSting(from) + "' ";
        }
        if (to != null) {
            hql += " and " + colName + " <= '" + CoreDateUtils.dateToStandardSting(to) + "' ";
        }
        return hql;
    }

}
