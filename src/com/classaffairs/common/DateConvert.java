package com.classaffairs.common;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

import com.classaffairs.framework.core.utils.DateUtil;

/**
 * 日期转化器（BeanUtil中使用）
 * @author mamm
 *
 */
public class DateConvert implements Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			return value;
		}

		if (value instanceof Long) {
			Long longValue = (Long) value;
			return new Date(longValue.longValue());
		}
		if (value instanceof String) {
			Date endTime = null;
			try {
				//endTime = DateUtils.parseDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
				endTime = DateUtil.formatDateToDate(value.toString(), "yyyy-MM-dd HH:mm:ss");

			} catch (Exception e) {
				e.printStackTrace();
			}
			return endTime;
		}

		return null;
	}

}
