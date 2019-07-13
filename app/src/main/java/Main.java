import com.island.contactphotoeraser.*;
import java.io.*;
public class Main
{
	public static final String END="END";
	public static final String INPUT="/storage/emulated/0/AppProjects/Contact_Photo_eraser/00001.vcf";
	public static final String OUTPUT="/storage/emulated/0/AppProjects/Contact_Photo_eraser/test.vcf";
	public static void main(String[]args)throws IOException
	{
		FileEraser.erase(new BufferedReader(new FileReader(INPUT)),new BufferedWriter(new FileWriter(OUTPUT)));
		System.out.println(END);
	}
}
