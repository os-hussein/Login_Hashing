package com.login.files.text;

import java.io.*;

/**
 * 
 * @author Osman Hussein
 * unique salts will be stored in a text file alongside the userid
 *
 */
public class HashTextFile {
	private String fileName;
	
	public HashTextFile(String fileName){
		this.fileName = fileName;
	}
	
	//writes the salt to the text file
	public String storeSalt(byte[] salt, int userid){ 
		File file = new File("C:\\Users\\USER-1\\Desktop\\Java Proj\\login\\src\\main\\resources\\hash storage\\" +getFileName());
		String saltStr = new String(salt);
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileOutputStream(file, true));
			printWriter.println(saltStr +" " +userid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(printWriter!=null){
				printWriter.flush();
				printWriter.close();
			}
		}
		return saltStr +" " +userid;
	}
	
	//gets the salt in bytes
	public byte[] retrieveSalt(int userid){
		byte[] bHash = null;
		BufferedInputStream in = null;
		File file = new File("C:\\Users\\USER-1\\Desktop\\Java Proj\\login\\src\\main\\resources\\hash storage\\" +getFileName());
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			byte[] bFile = new byte[(int)file.length()];
			in.read(bFile,  0,  (int)file.length());
			String bText = new String(bFile);
			
			int useridIndex = bText.indexOf(" " +userid)+1; //the index of the userid of interest
			int startHash = -1; //the for loop below will give an out of bounds exception if the value remains -1
			
			for(int i=useridIndex; i>=0; i--){
				if(i==0 || bText.charAt(i)=='\n'){
					startHash = i+1;
					break;
				}
			}
			String hash = bText.substring(startHash, useridIndex-1);
			bHash = hash.getBytes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bHash;
	}
	
	
	public String getFileName() {
		return fileName;
	}
}