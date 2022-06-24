package com.deloitte.pdfutility;

import com.deloitte.pdfutility.utility.ZipFilesUtility;


public class App 
{
    public static void main( String[] args )
    {
       try {
		ZipFilesUtility.createAndZipFiles();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}
