import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

// Data from http://www.sacred-texts.com/bib/osrc/
// Download kjvdat.zip, and put it in the local
// directory of this script.

public class BibleChapters {

	public static void separateChapters() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("kjvdat.txt"));

			String currentBook = "Gen";
			String currentChapter = "1";
			String fileName = "bible-chapters/" + currentBook + currentChapter + ".txt";
			File file = new File(fileName);
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			String line;
			while (((line = br.readLine()) != null)) {

				String newBook = line.substring(0, line.indexOf('|'));
				String newChapter = line.substring(line.indexOf('|') + 1, line.indexOf('|', line.indexOf('|') + 1));
				boolean needNewFile = !(newBook.equals(currentBook) && newChapter.equals(currentChapter));
				if (needNewFile) {
					bw.close();
					currentBook = newBook;
					currentChapter = newChapter;
					fileName = "bible-chapters/" + currentBook + currentChapter + ".txt";
					file = new File(fileName);
					fw = new FileWriter(file, true);
					bw = new BufferedWriter(fw);
					line = line.substring(line.lastIndexOf('|') + 1, line.indexOf('~'));
					bw.write(line);
				} else {
					line = line.substring(line.lastIndexOf('|') + 1, line.indexOf('~'));
					bw.write(line);
				}
			}

			bw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		separateChapters();
	}

}
