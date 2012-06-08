package com.sharifpro.eurb;

import java.util.HashMap;
import java.util.Map;

import com.sharifpro.eurb.builder.model.GroupAggregation;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartAxis;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportExecutionHistory;
import com.sharifpro.eurb.builder.model.ReportFilter;
import com.sharifpro.eurb.builder.model.ReportFormat;
import com.sharifpro.eurb.builder.model.UserMessage;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.eurb.management.security.model.Authorities;
import com.sharifpro.eurb.management.security.model.GroupAuthorities;
import com.sharifpro.eurb.management.security.model.GroupMembers;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.User;

public class PersistableObjectType {

	public static Map<String, Integer> OBJECT_TYPE_MAP = new HashMap<String, Integer>();
	static {
		OBJECT_TYPE_MAP.put(PersistableObject.class.getName(), 50);
		
		OBJECT_TYPE_MAP.put(User.class.getName(), 100);
		OBJECT_TYPE_MAP.put(Authorities.class.getName(), 150);
		OBJECT_TYPE_MAP.put(Groups.class.getName(), 200);
		OBJECT_TYPE_MAP.put(GroupAuthorities.class.getName(), 250);
		OBJECT_TYPE_MAP.put(GroupMembers.class.getName(), 300);
		OBJECT_TYPE_MAP.put(UserMessage.class.getName(), 310);

		OBJECT_TYPE_MAP.put(DbConfig.class.getName(), 350);
		OBJECT_TYPE_MAP.put(TableMapping.class.getName(), 400);
		OBJECT_TYPE_MAP.put(ColumnMapping.class.getName(), 450);

		OBJECT_TYPE_MAP.put(ReportDesign.class.getName(), 500);
		OBJECT_TYPE_MAP.put(ReportCategory.class.getName(), 550);
		OBJECT_TYPE_MAP.put(ReportDataset.class.getName(), 600);
		OBJECT_TYPE_MAP.put(ReportColumn.class.getName(), 650);
		OBJECT_TYPE_MAP.put(GroupAggregation.class.getName(), 700);
		OBJECT_TYPE_MAP.put(ReportFilter.class.getName(), 750);
		OBJECT_TYPE_MAP.put(ReportFormat.class.getName(), 800);
		OBJECT_TYPE_MAP.put(ReportExecutionHistory.class.getName(), 850);
		OBJECT_TYPE_MAP.put(ReportChart.class.getName(), 950);
		OBJECT_TYPE_MAP.put(ReportChartAxis.class.getName(), 1000);
		
		
		OBJECT_TYPE_MAP.put("Version", 900);
	}
	
	public static Integer getObjectTypeFor(String className) {
		Integer result = OBJECT_TYPE_MAP.get(className);
		if(result == null) {
			return -1;
		}
		return result;
	}

	public static Integer getObjectTypeFor(Class<? extends PersistableObject> clazz) {
		return getObjectTypeFor(clazz.getName());
	}
}
