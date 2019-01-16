package com.swx.ibms.common.web.license;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * @author zsq
 *
 */
public class LicenseManagerHolder {  
	/**
	 * 
	 */
	private static LicenseManager licenseManager;  

	/**
	 * 
	 */
	private LicenseManagerHolder(){}  

	/**
	 * @param param  LicenseManager
	 * @return LicenseManager
	 */
	public static synchronized  LicenseManager getLicenseManager(LicenseParam param){  
		if(licenseManager==null){  
			licenseManager=new LicenseManager(param);  
		}  
		return licenseManager;  
	}  
}  
