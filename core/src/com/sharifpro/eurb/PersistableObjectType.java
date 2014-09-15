package com.sharifpro.eurb;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import com.sharifpro.eurb.dashboard.model.Dashboard;
import com.sharifpro.eurb.dashboard.model.DashboardCol;
import com.sharifpro.eurb.dashboard.model.DashboardItem;
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
		OBJECT_TYPE_MAP.put(PersistableObject.class.getCanonicalName(), 50);
		
		OBJECT_TYPE_MAP.put(User.class.getCanonicalName(), 100);
		OBJECT_TYPE_MAP.put(Authorities.class.getCanonicalName(), 150);
		OBJECT_TYPE_MAP.put(Groups.class.getCanonicalName(), 200);
		OBJECT_TYPE_MAP.put(GroupAuthorities.class.getCanonicalName(), 250);
		OBJECT_TYPE_MAP.put(GroupMembers.class.getCanonicalName(), 300);
		OBJECT_TYPE_MAP.put(UserMessage.class.getCanonicalName(), 310);

		OBJECT_TYPE_MAP.put(DbConfig.class.getCanonicalName(), 350);
		OBJECT_TYPE_MAP.put(TableMapping.class.getCanonicalName(), 400);
		OBJECT_TYPE_MAP.put(ColumnMapping.class.getCanonicalName(), 450);

		OBJECT_TYPE_MAP.put(ReportDesign.class.getCanonicalName(), 500);
		OBJECT_TYPE_MAP.put(ReportCategory.class.getCanonicalName(), 550);
		OBJECT_TYPE_MAP.put(ReportDataset.class.getCanonicalName(), 600);
		OBJECT_TYPE_MAP.put(ReportColumn.class.getCanonicalName(), 650);
		OBJECT_TYPE_MAP.put(GroupAggregation.class.getCanonicalName(), 700);
		OBJECT_TYPE_MAP.put(ReportFilter.class.getCanonicalName(), 750);
		OBJECT_TYPE_MAP.put(ReportFormat.class.getCanonicalName(), 800);
		OBJECT_TYPE_MAP.put(ReportExecutionHistory.class.getCanonicalName(), 850);
		OBJECT_TYPE_MAP.put(ReportChart.class.getCanonicalName(), 950);
		OBJECT_TYPE_MAP.put(ReportChartAxis.class.getCanonicalName(), 1000);

		OBJECT_TYPE_MAP.put(Dashboard.class.getCanonicalName(), 510);
		OBJECT_TYPE_MAP.put(DashboardCol.class.getCanonicalName(), 515);
		OBJECT_TYPE_MAP.put(DashboardItem.class.getCanonicalName(), 520);
		
		
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
		return getObjectTypeFor(clazz.getCanonicalName());
	}

	public static String getClassForObjectType(Integer objectType) {
		for(Entry<String, Integer> entry : OBJECT_TYPE_MAP.entrySet()){
			if(entry.getValue().equals(objectType)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
