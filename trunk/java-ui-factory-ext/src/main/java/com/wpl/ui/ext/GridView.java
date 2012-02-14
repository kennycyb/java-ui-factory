package com.wpl.ui.ext;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.wpl.ui.components.IComponent;
import com.wpl.ui.ext.factory.GridViewFactory;
import com.wpl.ui.factory.annotations.DefaultFactory;

@DefaultFactory(GridViewFactory.class)
public class GridView<T> implements IComponent {

	private class GridViewColumn extends TableColumn {
		private String mFieldName;

		public String getFieldName() {
			return mFieldName;
		}

		public void setFieldName(String fieldName) {
			mFieldName = fieldName;
		}

	}

	private class GridViewColumnModel extends DefaultTableColumnModel {

	}

	private class GridViewDataModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2690213448788265466L;

		@Override
		public int getColumnCount() {
			return mColumnModel.getColumnCount();
		}

		@Override
		public int getRowCount() {
			return mData.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			T object = mData.get(rowIndex);

			return null;
		}
	}

	private final List<T> mData = new ArrayList<T>();
	private JTable mTable;
	private GridViewColumnModel mColumnModel;
	private GridViewDataModel mDataModel;

	public GridView() {

		this.mColumnModel = new GridViewColumnModel();
		this.mDataModel = new GridViewDataModel();
	}

	private List<String> mProperties = new ArrayList<String>();

	public void setProperties(List<String> properties) {
		mProperties = properties;

		for (String property : mProperties) {
			TableColumn column = new TableColumn();
			column.setHeaderValue(property);
			mColumnModel.addColumn(column);
		}
	}

	public void init() {

	}

	@Override
	public Component getComponent() {

		this.mTable = new JTable(this.mDataModel, this.mColumnModel);

		return this.mTable;
	}
}