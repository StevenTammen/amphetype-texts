import java.io.*;
import java.util.Random;

// Data from https://www.wordfrequency.info/free.asp?s=y. The COCA corpus.
// You have to give an email to download, and sharing the data directly is
// not entirely encouraged. The input file wordFrequency.txt assumes a list
// of words followed by frequencies, separated by a tab, and should be in the
// local directory of this script.

public class FrequencyLists {

	private int numWords;
	private String[] words;
	private int[] frequencies;
	private int[] outputCounts;
	private int totalOutputCount;
	private String[] outputWords;
	
	public FrequencyLists(int numWords)
	{
		this.numWords = numWords;
		this.words = new String[numWords];
		this.frequencies = new int[numWords];
		this.outputCounts = new int[numWords];
	}
	
	public void getWordsAndFrequencies(String fileName)
	{
		
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;
            while (((line = br.readLine()) != null) && (i <= this.numWords - 1)) {
                this.words[i] = line.trim().substring(0, line.indexOf("\t"));
                this.frequencies[i] = Integer.parseInt(line.substring(line.indexOf("\t") + 1));
                i++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void getOutputCounts()
	{
		int totalFreq = 0;
		
		for(int freq : this.frequencies)
		{
			totalFreq += freq;
		}
		
		for(int i = 0; i <= this.outputCounts.length - 1; i++)
		{
			int count = (int)Math.ceil((100 * (double)this.frequencies[i]) / totalFreq);
			this.outputCounts[i] = count;
			this.totalOutputCount += count;
		}
	}
	
	public void getOutputWords()
	{
		
		this.outputWords = new String[this.totalOutputCount];
		
		int i = 0;
		
		for(int j = 0; j <= this.words.length - 1; j++)
		{
			for(int k = 1; k <= this.outputCounts[j]; k++)
			{
				this.outputWords[i] = this.words[j];
				i++;
			}
		}
	}
	
	public void randomizeWords()
	{
		
		Random r = new Random();
		
		for(int i = 1; i <= 100000; i++)
		{
			int index1 = r.nextInt(this.outputWords.length);
			int index2 = r.nextInt(this.outputWords.length);
			
			String copy = this.outputWords[index1];
			
			this.outputWords[index1] = this.outputWords[index2];
			this.outputWords[index2] = copy;
		}
	}
	
	public void writeWords()
	{
		try
	    {
			
			String fileName = "frequency-lists/top" + this.numWords + "words.txt";
		    File file = new File(fileName);
		    FileWriter fw = new FileWriter(file, true);
		    BufferedWriter bw = new BufferedWriter(fw);
		       
		    for(int i = 0; i <= this.outputWords.length - 1; i++)
		    {
		    	bw.write(this.outputWords[i]);
		    	bw.newLine();
		    }
		       
		    bw.close();
	    }
	    catch (Exception e)
	    {
	    	System.out.println(e);
	    }
	}
	
	public static void main(String[] args) {
		
		for(int numWords = 100; numWords <= 1000; numWords += 100)
		{
			FrequencyLists a        = new FrequencyLists(numWords);
			String         fileName = "wordFrequency.txt";
			a.getWordsAndFrequencies(fileName);
			a.getOutputCounts();
			a.getOutputWords();
			a.randomizeWords();
			a.writeWords();
		}
		
	}

}
