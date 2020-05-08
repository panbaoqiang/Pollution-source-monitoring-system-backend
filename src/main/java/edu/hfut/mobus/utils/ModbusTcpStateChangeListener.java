package edu.hfut.mobus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 监听属性值的变化
 * @author Administrator
 *
 */
public class ModbusTcpStateChangeListener implements PropertyChangeListener{
	private Logger logger = LoggerFactory.getLogger(ModbusTcpStateChangeListener.class);
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.info(  evt.getPropertyName() + " : " + evt.getNewValue() + " , " + evt.getOldValue());
	}

}
