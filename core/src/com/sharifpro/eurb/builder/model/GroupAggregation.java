package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class GroupAggregation extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -8458707854067698993L;

	/** 
	 * This attribute maps to the column parent_column_id in the group_aggregation table.
	 */
	protected Long parentColumnId;

	
	/** 
	 * This attribute maps to the column aggregated_column_mapping_id in the group_aggregation table.
	 */
	protected Long aggregatedColumnMappingId;
	

	/** 
	 * This attribute maps to the column aggregated_column_dataset_id in the group_aggregation table.
	 */
	protected Long aggregatedColumnDatasetId;

		

	/** 
	 * This attribute maps to the column aggregated_report_column_id in the group_aggregation table.
	 */
	protected Long aggregatedReportColumnId;


	/** 
	 * This attribute maps to the column aggregation_function in the group_aggregation table.
	 */
	protected String aggregationFunction;

	/** 
	 * This attribute maps to the column place in the group_aggregation table.
	 */
	protected Integer place;

	/**
	 * Method 'GroupAggregation'
	 * 
	 */
	public GroupAggregation()
	{
		super();
	}

	/**
	 * Method 'getParentColumnId'
	 * 
	 * @return Long
	 */
	public Long getParentColumnId()
	{
		return parentColumnId;
	}

	/**
	 * Method 'setParentColumnId'
	 * 
	 * @param parentColumnId
	 */
	public void setParentColumnId(Long parentColumnId)
	{
		this.parentColumnId = parentColumnId;
	}

	
	/**
	 * Method 'getAggregatedReportColumnId'
	 * 
	 * @return Long
	 */
	public Long getAggregatedReportColumnId()
	{
		return (aggregatedReportColumnId == null || aggregatedReportColumnId == 0) ? null : aggregatedReportColumnId;
	}

	/**
	 * Method 'setAggregatedReportColumnId'
	 * 
	 * @param aggregatedReportColumnId
	 */
	public void setAggregatedReportColumnId(Long aggregatedReportColumnId)
	{
		this.aggregatedReportColumnId = aggregatedReportColumnId;
	}

	
	/**
	 * Method 'getAggregationFunction'
	 * 
	 * @return String
	 */
	public String getAggregationFunction()
	{
		return aggregationFunction;
	}

	/**
	 * Method 'setAggregationFunction'
	 * 
	 * @param aggregationFunction
	 */
	public void setAggregationFunction(String aggregationFunction)
	{
		this.aggregationFunction = aggregationFunction;
	}
	
	/**
	 * Method 'getAggregatedColumnMappingId'
	 * 
	 * @return Long
	 */
	public Long getAggregatedColumnMappingId() {
		return (aggregatedColumnMappingId == null || aggregatedColumnMappingId == 0) ? null : aggregatedColumnMappingId;
	}

	/**
	 * Method 'setAggregatedColumnMappingId'
	 * 
	 * @param aggregatedColumnMappingId
	 */
	public void setAggregatedColumnMappingId(Long aggregatedColumnMappingId) {
		this.aggregatedColumnMappingId = aggregatedColumnMappingId;
	}

	/**
	 * Method 'getAggregatedColumnDatasetId'
	 * 
	  * @return Long
	 */
	public Long getAggregatedColumnDatasetId() {
		return (aggregatedColumnDatasetId == null || aggregatedColumnDatasetId == 0) ? null : aggregatedColumnDatasetId;
	}

	/**
	 * Method 'setAggregatedColumnDatasetId'
	 * 
	 * @param aggregatedColumnDatasetId
	 */
	public void setAggregatedColumnDatasetId(Long aggregatedColumnDatasetId) {
		this.aggregatedColumnDatasetId = aggregatedColumnDatasetId;
	}


	/**
	 * Method 'getPlace'
	 * 
	 * @return Integer
	 */
	public Integer getPlace()
	{
		return place;
	}

	/**
	 * Method 'setPlace'
	 * 
	 * @param place
	 */
	public void setPlace(Integer place)
	{
		this.place = place;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof GroupAggregation)) {
			return false;
		}
		
		/*final GroupAggregation _cast = (GroupAggregation) _other;
		
		if (parentColumnId == null ? _cast.parentColumnId != parentColumnId : !parentColumnId.equals( _cast.parentColumnId )) {
			return false;
		}
		
		if (parentColumnDatasetId == null ? _cast.parentColumnDatasetId != parentColumnDatasetId : !parentColumnDatasetId.equals( _cast.parentColumnDatasetId )) {
			return false;
		}
		
		if (parentColumnDesignId == null ? _cast.parentColumnDesignId != parentColumnDesignId : !parentColumnDesignId.equals( _cast.parentColumnDesignId )) {
			return false;
		}
		
		if (parentColumnDesignVersionId == null ? _cast.parentColumnDesignVersionId != parentColumnDesignVersionId : !parentColumnDesignVersionId.equals( _cast.parentColumnDesignVersionId )) {
			return false;
		}
		
		if (aggregatedReportColumnId == null ? _cast.aggregatedReportColumnId != aggregatedReportColumnId : !aggregatedReportColumnId.equals( _cast.aggregatedReportColumnId )) {
			return false;
		}
		
		if (aggregatedReportColumnDatasetId == null ? _cast.aggregatedReportColumnDatasetId != aggregatedReportColumnDatasetId : !aggregatedReportColumnDatasetId.equals( _cast.aggregatedReportColumnDatasetId )) {
			return false;
		}
		
		if (aggregatedReportColumnDesignId == null ? _cast.aggregatedReportColumnDesignId != aggregatedReportColumnDesignId : !aggregatedReportColumnDesignId.equals( _cast.aggregatedReportColumnDesignId )) {
			return false;
		}
		
		if (aggregatedReportColumnDesignVersionId == null ? _cast.aggregatedReportColumnDesignVersionId != aggregatedReportColumnDesignVersionId : !aggregatedReportColumnDesignVersionId.equals( _cast.aggregatedReportColumnDesignVersionId )) {
			return false;
		}
		
		if (aggregationFunction == null ? _cast.aggregationFunction != aggregationFunction : !aggregationFunction.equals( _cast.aggregationFunction )) {
			return false;
		}
		
		if (place == null ? _cast.place != place : !place.equals( _cast.place )) {
			return false;
		}*/
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = super.hashCode();
		
		/*if (parentColumnId != null) {
			_hashCode = 29 * _hashCode + parentColumnId.hashCode();
		}
		
		if (parentColumnDatasetId != null) {
			_hashCode = 29 * _hashCode + parentColumnDatasetId.hashCode();
		}
		
		if (parentColumnDesignId != null) {
			_hashCode = 29 * _hashCode + parentColumnDesignId.hashCode();
		}
		
		if (parentColumnDesignVersionId != null) {
			_hashCode = 29 * _hashCode + parentColumnDesignVersionId.hashCode();
		}
		
		if (aggregatedReportColumnId != null) {
			_hashCode = 29 * _hashCode + aggregatedReportColumnId.hashCode();
		}
		
		if (aggregatedReportColumnDatasetId != null) {
			_hashCode = 29 * _hashCode + aggregatedReportColumnDatasetId.hashCode();
		}
		
		if (aggregatedReportColumnDesignId != null) {
			_hashCode = 29 * _hashCode + aggregatedReportColumnDesignId.hashCode();
		}
		
		if (aggregatedReportColumnDesignVersionId != null) {
			_hashCode = 29 * _hashCode + aggregatedReportColumnDesignVersionId.hashCode();
		}
		
		if (aggregationFunction != null) {
			_hashCode = 29 * _hashCode + aggregationFunction.hashCode();
		}
		
		if (place != null) {
			_hashCode = 29 * _hashCode + place.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return GroupAggregationPk
	 */
	public GroupAggregationPk createPk()
	{
		return new GroupAggregationPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.GroupAggregation: " );
		ret.append( super.toString() );
		ret.append( ", parentColumnId=" + parentColumnId );
		ret.append( ", aggregatedColumnMappingId=" + aggregatedColumnMappingId );
		ret.append( ", aggregatedColumnDatasetId=" + aggregatedColumnDatasetId );
		ret.append( ", aggregatedReportColumnId=" + aggregatedReportColumnId );
		ret.append( ", aggregationFunction=" + aggregationFunction );
		ret.append( ", place=" + place );
		return ret.toString();
	}

}
