package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportCategory extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -6872414545571485755L;

	public static final long ACL_CLASS_IDENTIFIER = 1;

	/** 
	 * This attribute maps to the column name in the report_category table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column description in the report_category table.
	 */
	protected String description;
	
	/** 
	 * This attribute maps to the column parent_category_id in the report_category table.
	 */
	protected Long parentCategory;
	protected Long _parent;
	protected Boolean _is_leaf = false;
	
	private boolean accessPreventDel = true;
	private boolean accessPreventEdit = true;
	private boolean accessPreventExecute = true;
	private boolean accessPreventSharing = true;

	/**
	 * Method 'ReportCategory'
	 * 
	 */
	public ReportCategory()
	{
		super();
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	
	/**
	 * Method 'getParentCategory'
	 * 
	 * @return String
	 */
	public Long getParentCategory() {
		return parentCategory;
	}
	
	public Long get_parent() {
		return parentCategory;
	}


	public Boolean get_is_leaf() {
		return false;
	}

	/**
	 * Method 'setParentCategory'
	 * 
	 * @param parentCategory
	 */
	public void setParentCategory(Long parentCategory) {
		this.parentCategory = parentCategory;
		this._parent = parentCategory;
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
		
		if (!(_other instanceof ReportCategory)) {
			return false;
		}
		
		/*final ReportCategory _cast = (ReportCategory) _other;
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
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
		
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		/*if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportCategoryPk
	 */
	public ReportCategoryPk createPk()
	{
		return new ReportCategoryPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportCategory: " );
		ret.append( super.toString() );
		ret.append( ", name=" + name );
		ret.append( ", description=" + description );
		return ret.toString();
	}
	
	/*public static class ReportCategoryAndDesignSortOrderComparator implements Comparator<Object> {

		@Override
		public int compare(Object thiz, Object that) {
			if(that.getClass().equals(ReportCategory.class)){
				ReportCategory thatC = (ReportCategory) that;
				if(thiz.getClass().equals(ReportCategory.class)){
					ReportCategory thizC = (ReportCategory) thiz;
					if(thizC.getParentCategory() == thatC.getId()){
						return 1;
					}
					else if(thizC.getId() == thatC.getParentCategory()){
						return -1;
					}
				}
				else if (that.getClass().equals(ReportDesign.class)){
					ReportDesign thizD = (ReportDesign) thiz;
					if(thizD.getCategoryId() == thatC.getId()){
						return 1;
					}
				}
			}
			return 0;
		}
		
	}
*/
	
	public boolean isAccessPreventDel() {
		return accessPreventDel;
	}

	public void setAccessPreventDel(boolean accessPreventDel) {
		this.accessPreventDel = accessPreventDel;
	}

	public boolean isAccessPreventEdit() {
		return accessPreventEdit;
	}

	public void setAccessPreventEdit(boolean accessPreventEdit) {
		this.accessPreventEdit = accessPreventEdit;
	}

	public boolean isAccessPreventExecute() {
		return accessPreventExecute;
	}

	public void setAccessPreventExecute(boolean accessPreventExecute) {
		this.accessPreventExecute = accessPreventExecute;
	}

	public boolean isAccessPreventSharing() {
		return accessPreventSharing;
	}

	public void setAccessPreventSharing(boolean accessPreventSharing) {
		this.accessPreventSharing = accessPreventSharing;
	}
}
