package com.edge.core.helper;

import com.edge.core.utils.CoreDateUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component("mongoDbHelper")
public class MongoDbHelperImpl implements DbHelper {

    public String addINClause(String colName, String[] array) {
        return addINClause(colName, array, null);
    }

    @Override
    public String addINClause(String colName, String[] array, String delimiter) {
        String hql = "";
        if (array != null) {
            if (array.length == 1) {
                if (!array[0].equals("Any")) {
                    hql = " , " + colName + " : '" + getValue(array[0], delimiter) + "' ";
                }
            } else if (array.length > 1) {
                if (!array[0].equals("Any")) {
                    hql = " , " + colName + " :{ $in: [ " + addINClause(array, delimiter) + " ] } ";
                }
            }
        }
        return hql;
    }

    @Override
    public String addBETWEENClause(String colName, Integer from, Integer to) {
        String hql = "";

        if (from != null && to == null) {
            hql += " , " + colName + " : { $gte: " + from + " } ";
        } else if (to != null && from == null) {
            hql += " , " + colName + " : { $lte: " + to + " } ";
        } else if (to != null && from != null) {
            hql += " , " + colName + " : { $gte: " + from + " , $lte: " + to + " } ";
        }
        return hql;
    }

    @Override
    public String addBETWEENClause(String colName, BigDecimal from, BigDecimal to) {
        String hql = "";
        if (from != null) {
            hql += " ,  $expr: { $gte: [ { $toDouble:\"$" + colName + "\"}, " + from + " ] } ";
        }
        if (to != null) {
            hql += " ,  $expr: { $lte: [ { $toDouble:\"$" + colName + "\"}, " + to + " ] } ";
        }

        return hql;
    }

    @Override
    public String addBETWEENClause(String colName, Date from, Date to) {
        String hql = "";
        if (from != null && to == null) {
            hql += " , " + colName + " : { $gte: ISODate('" + CoreDateUtils.dateToMongoDbClause(from) + "') } ";
        } else if (to != null && from == null) {
            Date newTo = DateUtils.addDays(to, 1);
            newTo = DateUtils.addSeconds(newTo, -1);
            hql += " , " + colName + " : { $lte: ISODate('" + CoreDateUtils.dateToMongoDbClause(newTo) + "') } ";
        } else if (to != null && from != null) {
            Date newTo = DateUtils.addDays(to, 1);
            newTo = DateUtils.addSeconds(newTo, -1);
            hql += " , " + colName + " : { $gte: ISODate('" + CoreDateUtils.dateToMongoDbClause(from) + "'), $lte: ISODate('" + CoreDateUtils.dateToMongoDbClause(newTo) + "') } ";
        }
        return hql;
    }

}
