package com.deloitte.pdfutility.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class ZipFilesUtility {
	
	private static final String baseDir="C:" + File.separator + "pdfFiles";
	

	public static void createAndZipFiles() throws IOException, CsvValidationException {
		String filePath = "C:/META_24_06_2022.csv";
		readCSVFile(filePath);
		// zip all the files
		zipFile(baseDir);
	}

	private static void readCSVFile(String filePath) {
		String pdfName="";
		try {
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine;
			boolean firstLineRead = false;
			while ((nextLine = reader.readNext()) != null) {
				// skip the header of excel file
				if (!firstLineRead) {
					firstLineRead = true;
					continue;
				}
				
				//get names of file from csv
				pdfName = nextLine[1];
				
				// create pdf file and write content to it
				createPdfFile(pdfName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void createPdfFile(String pdfName) {
		String path = baseDir + File.separator + pdfName;
		File file = new File(path);
		file.getParentFile().mkdirs();

		try {
			writeContentToFile(path);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("PDF created");

	}

	private static void zipFile(String path) {
		List<File> srcFiles = new ArrayList<File>();
		File filePath = new File(path);
		for (final File fileEntry : filePath.listFiles()) {
			srcFiles.add(fileEntry);
		}

		try {
			FileOutputStream fos = new FileOutputStream(
					baseDir + File.separator + "compressedfiles.zip");
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			for (File srcFile : srcFiles) {
				System.out.println(srcFile.getAbsolutePath());
				File fileToZip = new File(srcFile.getAbsolutePath());
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());

				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			System.out.println("Files zipped to "+baseDir + File.separator + "compressedfiles.zip");
			zipOut.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeContentToFile(String path) {
	System.out.println("Copying file contents from C:/SampleFile.pdf");
		File f = new File("C:/SampleFile.pdf");

		OutputStream oos;
		try {
			oos = new FileOutputStream(path);
			byte[] buf = new byte[8192];

			InputStream is = new FileInputStream(f);

			int c = 0;

			while ((c = is.read(buf, 0, buf.length)) > 0) {
				oos.write(buf, 0, c);
				oos.flush();
			}
			oos.close();
			System.out.println("stop");
			is.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

