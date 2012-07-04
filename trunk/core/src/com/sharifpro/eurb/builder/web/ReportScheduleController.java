package com.sharifpro.eurb.builder.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportAlertDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.dao.ReportScheduleDao;
import com.sharifpro.eurb.builder.delivery.FileSystemDeliveryMethod;
import com.sharifpro.eurb.builder.model.ReportAlert;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportSchedule;
import com.sharifpro.eurb.builder.model.ReportUserAlert;
import com.sharifpro.eurb.builder.util.ReportConstants.DeliveryMethod;
import com.sharifpro.eurb.info.AuthorityType;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.util.DateProvider;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SecurityUtil;
import com.sharifpro.util.SessionManager;
import com.sharifpro.util.json.JsonUtil;

@Controller
public class ReportScheduleController {

	private ReportScheduleDao reportScheduleDao;
	private FileSystemDeliveryMethod fileSystemDeliveryMethod;
	private UserDao userDao;
	private ReportDesignDao reportDesignDao;
	private ReportAlertDao reportAlertDao;
	private JsonUtil jsonUtil;

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_RPG_REPORT_SCHEDULER_VIEW_LIST)")
	@RequestMapping(value="/builder/schedule/schedule.spy")
	public ModelAndView executeDbConfigSpy() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/builder/schedule/schedule");
		return mv;
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_RPG_REPORT_SCHEDULER_VIEW_LIST)")
	@RequestMapping(value = "/builder/schedule/findUserScheduled.spy")
	public @ResponseBody
	Map<String, ? extends Object> findUserScheduled() {
		try {
			User reportUser = SessionManager.getCurrentUser();

			return JsonUtil.getSuccessfulMap(reportScheduleDao.getScheduledReports(reportUser));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	/*@RequestMapping(value = "/builder/schedule/findUserFileDelivered.spy")
	public @ResponseBody
	Map<String, ? extends Object> findUserFileDelivered() {
		try {
			User reportUser = SessionManager.getCurrentUser();

			return JsonUtil.getSuccessfulMap(fileSystemDeliveryMethod.getDeliveredReports(reportUser));
		} catch (NullPointerException npe) {
			return JsonUtil.getModelMapError(PropertyProvider.get("error.settings.directories"));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value = "/builder/schedule/findAllUsersScheduled.spy")
	public @ResponseBody Map<String, ? extends Object> findAllUsersScheduled() {
		try {
			List<ReportSchedule> scheduledReports = new ArrayList<ReportSchedule>();

			List<User> users = userDao.findAllActive();

			Iterator<User> iterator = users.iterator();
			while (iterator.hasNext()) {
				User user = iterator.next();

				List<ReportSchedule> schedules = reportScheduleDao.getScheduledReports(user);

				scheduledReports.addAll(schedules);
			}
			return JsonUtil.getSuccessfulMap(scheduledReports);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value = "/builder/schedule/findSchedule.spy")
	public @ResponseBody
	Map<String, ? extends Object> findSchedule(
			@RequestParam(required = false) Long userId,
			@RequestParam(required = true) String scheduleName) {
		try {
			Map<String, Object> response = new HashMap<String, Object>();
			User user = null;

			if (userId >= 0) {
				user = userDao.findByPrimaryKey(userId);
			} else {
				user = SessionManager.getCurrentUser();
			}
			response.put("user", user);
			if (StringUtils.isEmpty(user.getEmail())) {
				return JsonUtil.getModelMapError(PropertyProvider.get("eurb.app.builder.schedule.userMustHaveValidEmail"));
			}
			if (scheduleName != null && scheduleName.length() > 0) {
				ReportSchedule reportSchedule = reportScheduleDao.getScheduledReport(user, scheduleName);

				response.put("report", reportSchedule.getReport());
				response.put("scheduleType", reportSchedule.getScheduleType());
				response.put("startDate", DateProvider.formatDate(reportSchedule.getStartDate()));
				response.put("startHour", reportSchedule.getStartHour());
				response.put("startMinute", reportSchedule.getStartMinute());
				response.put("startAmPm", reportSchedule.getStartAmPm());
				response.put("recipients", reportSchedule.getRecipients());
				response.put("description", reportSchedule.getScheduleDescription());
				response.put("hours", reportSchedule.getHours());
				response.put("cron", reportSchedule.getCronExpression());
				response.put("runToFile", reportSchedule.isDeliveryMethodSelected(DeliveryMethod.FILE.getName()));

				if (reportSchedule.getAlert() != null) {
					ReportUserAlert userAlert = reportSchedule.getAlert();

					response.put("alertId", userAlert.getAlert().getId().intValue());
					response.put("alertLimit", userAlert.getLimit());
					response.put("alertOperator", userAlert.getOperator());
				}
			}
			return response;
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}*/

	@PreAuthorize("hasAnyRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_RPG_REPORT_SCHEDULER_EDIT, T(com.sharifpro.eurb.info.AuthorityType).ROLE_RPG_REPORT_SCHEDULER_CREATE)")
	@RequestMapping(value = "/builder/schedule/scheduleStore.spy")
	public @ResponseBody Map<String, ? extends Object> store(
			@RequestParam(required = false) Long userId,
			@RequestParam(required = false) String recipients,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String startHour,
			@RequestParam(required = false) String startMinute,
			@RequestParam(required = false) String startAmPm,
			@RequestParam(required = false) String scheduleName,
			@RequestParam(required = false) Long reportId,
			@RequestParam(required = false, defaultValue= "1") String exportType,
			@RequestParam(required = false) Integer scheduleType,
			@RequestParam(required = false, defaultValue= "0") Integer hours,
			@RequestParam(required = false) String cronExpression,
			@RequestParam(required = false) String scheduleDescription,
			@RequestParam(required = false, defaultValue = "true") Boolean runToFile,
			@RequestParam(required = false, defaultValue = "-1") Integer alertId,
			@RequestParam(required = false) Integer alertLimit,
			@RequestParam(required = false) String alertOperator)
			throws PersistableObjectDaoException {
		User user = null;

		if (userId != null) {
			user = userDao.findByPrimaryKey(userId);
		} else {
			user = SessionManager.getCurrentUser();
		}

		if (StringUtils.isEmpty(user.getEmail())) {
			return JsonUtil.getModelMapError(PropertyProvider
					.get("eurb.app.builder.schedule.userMustHaveValidEmail"));
		}

		if (StringUtils.isEmpty(recipients)) {
			recipients = user.getEmail();
		}

		if (startDate == null || startDate.length() < 1 || startHour == null
				|| startHour.length() < 1 || startMinute == null
				|| startMinute.length() < 1 || startAmPm == null
				|| startAmPm.length() < 1) {
			return JsonUtil
					.getModelMapError(PropertyProvider.ERROR_DATEANDTIME_REQUIRED);
		}

		int hour = Integer.parseInt(startHour);
		int minute = Integer.parseInt(startMinute);
		if (hour > 12 || hour < 1 || minute < 0 || minute > 59) {
			return JsonUtil
					.getModelMapError(PropertyProvider.ERROR_DATEANDTIME_INVALID);
		}

		ReportSchedule reportSchedule;

		try {
			if (scheduleName != null && scheduleName.length() > 0) {
				if(SecurityUtil.hasRole(AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_EDIT)) {
					reportSchedule = reportScheduleDao.getScheduledReport(user, scheduleName);
				} else {
					throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_EDIT);
				}
			} else {
				if(SecurityUtil.hasRole(AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_CREATE)) {
					ReportDesign report = reportDesignDao.findByPrimaryKey(reportId);

					int exportTypeMode = Integer.parseInt(exportType);

					reportSchedule = new ReportSchedule();
					reportSchedule.setReport(report);
					reportSchedule.setUser(user);
					reportSchedule.setReportParameters(getReportParameters());
					reportSchedule.setExportType(exportTypeMode);
					reportSchedule.setScheduleName(report.getId() + "|" + new Date().getTime());
				} else {
					throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_CREATE);
				}
			}

			reportSchedule.setScheduleType(scheduleType);
			reportSchedule.setStartDate(DateProvider.parseDate(startDate));
			reportSchedule.setStartHour(startHour);
			reportSchedule.setStartMinute(startMinute);
			reportSchedule.setStartAmPm(startAmPm);
			reportSchedule.setRecipients(recipients);
			reportSchedule.setScheduleDescription(scheduleDescription);
			reportSchedule.setHours(hours == null ? 0 : hours);
			reportSchedule.setCronExpression(cronExpression);

			if (runToFile) {
				reportSchedule.setDeliveryMethods(new String[] { DeliveryMethod.FILE.getName() });
			} else {
				reportSchedule.setDeliveryMethods(new String[] { DeliveryMethod.EMAIL.getName() });
			}

			if (alertId != -1) {
				ReportAlert alert = reportAlertDao.getReportAlert(new Integer(alertId));

				ReportUserAlert userAlert = new ReportUserAlert();
				userAlert.setAlert(alert);
				userAlert.setUser(user);
				userAlert.setLimit(alertLimit);
				userAlert.setOperator(alertOperator);

				reportSchedule.setAlert(userAlert);
			} else {
				reportSchedule.setAlert(null);
			}

			if (scheduleName != null && scheduleName.length() > 0) {
				// in order to update a schedule report, original reportSchedule
				// is deleted and new a one is created.
				reportScheduleDao.deleteScheduledReport(user, scheduleName);
			}

			reportScheduleDao.scheduleReport(reportSchedule);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}

		return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(reportSchedule.getScheduleName()));
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_RPG_REPORT_SCHEDULER_DELETE)")
	@RequestMapping(value = "/builder/schedule/scheduleRemove.spy")
	public @ResponseBody Map<String, ? extends Object>  scheduleRemove(@RequestParam Object data) {
		try {
			User reportUser = null;

			//if (userId != null) {
			//	reportUser = userDao.findByPrimaryKey(userId);
			//} else {
				reportUser = SessionManager.getCurrentUser();;
			//}
			List<String> deleteScheduleNames = jsonUtil.getListFromRequest(data, String.class);
			for(String scheduleName : deleteScheduleNames) { 
				ReportSchedule reportSchedule = reportScheduleDao.getScheduledReport(reportUser, scheduleName);
				reportScheduleDao.deleteScheduledReport(reportUser, reportSchedule.getScheduleName());
			}
			return  JsonUtil.getSuccessfulMapAfterStore(deleteScheduleNames);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	private Map<String, Object> getReportParameters() {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Autowired
	public void setReportScheduleDao(ReportScheduleDao schedulerProvider) {
		this.reportScheduleDao = schedulerProvider;
	}

	@Autowired
	public void setFileSystemDeliveryMethod(
			FileSystemDeliveryMethod fileSystemDeliveryMethod) {
		this.fileSystemDeliveryMethod = fileSystemDeliveryMethod;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setReportDesignDao(ReportDesignDao reportDesignDao) {
		this.reportDesignDao = reportDesignDao;
	}

	@Autowired
	public void setReportAlertDao(ReportAlertDao reportAlertDao) {
		this.reportAlertDao = reportAlertDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}