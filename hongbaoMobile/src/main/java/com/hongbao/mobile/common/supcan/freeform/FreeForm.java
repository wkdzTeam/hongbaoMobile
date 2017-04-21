/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.common.supcan.freeform;

import com.hongbao.mobile.common.supcan.common.Common;
import com.hongbao.mobile.common.supcan.common.properties.Properties;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 硕正FreeForm
 */
@XStreamAlias("FreeForm")
public class FreeForm extends Common {

	public FreeForm() {
		super();
	}
	
	public FreeForm(Properties properties) {
		this();
		this.properties = properties;
	}
	
}
