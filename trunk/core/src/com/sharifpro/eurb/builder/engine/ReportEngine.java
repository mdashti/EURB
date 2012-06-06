/*
 * Copyright (C) 2006 Erik Swenson - erik@oreports.com
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *  
 */

package com.sharifpro.eurb.builder.engine;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.engine.input.ReportEngineInput;
import com.sharifpro.eurb.builder.engine.output.ReportEngineOutput;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportFilter;
import com.sharifpro.eurb.builder.provider.DirectoryProvider;
import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public abstract class ReportEngine
{	
	protected ReportDesignDao dataSourceProvider;
	protected DirectoryProvider directoryProvider;
	
	protected ApplicationContext applicationContext;
	
	public ReportEngine(ReportDesignDao dataSourceProvider,
			DirectoryProvider directoryProvider)
	{
		this.dataSourceProvider = dataSourceProvider;
		this.directoryProvider = directoryProvider;
	}
	
	public abstract ReportEngineOutput generateReport(ReportEngineInput input)
			throws PersistableObjectDaoException;

	public abstract List<ReportFilter> buildParameterList(ReportDesign report) throws PersistableObjectDaoException;

	public void setApplicationContext(ApplicationContext applicationContext) 
	{
		this.applicationContext = applicationContext;
	}	
}