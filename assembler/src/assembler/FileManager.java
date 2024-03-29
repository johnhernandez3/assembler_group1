package assembler;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JFileChooser;

public class FileManager {

	
	public String read(File f) throws IOException {
		if (f == null) {
			throw new NullPointerException();
		}
		
		Reader in = null;
		try {
			in = new InputStreamReader(new BufferedInputStream(new FileInputStream(f)));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		char[] buffer = new char[32 * 1024];
		while (true) {
			int read = 0;
			try {
				read = in.read(buffer);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (read == -1) {
				break;
			}
			sb.append(buffer, 0, read);
		}
		
		return sb.toString().toLowerCase();
	}
	
//	method to save file
	
	public String save(String text, Component parent) {
		JFileChooser saver = new JFileChooser("./");
        int returnVal = saver.showSaveDialog(parent);
        File file = saver.getSelectedFile();
        BufferedWriter writer = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
				writer = new BufferedWriter(new FileWriter(file));
				writer.write(text);
	            writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		return "File saved.";
	}
//	handling file type acceptance
	
//	creation of object file (data to file is managed elsewhere
}
