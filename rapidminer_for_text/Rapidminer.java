import java.awt.font.TransformAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.io.CSVExampleSetWriter;
import com.rapidminer.operator.nio.CSVExampleSource;
import com.rapidminer.operator.preprocessing.filter.ChangeAttributeRole;
import com.rapidminer.operator.preprocessing.filter.attributes.AttributeFilter;
import com.rapidminer.operator.preprocessing.filter.attributes.AttributeFilterCondition;
import com.rapidminer.tools.XMLException;

public class Rapidminer {
	public static void main(String[] args) {

		File cluster =new File (args[0]); //Cluster.rmp file
		File input_folder= new File(args[1]);// input csv folder
		File output_folder= new File(args[2]);	//ouput folder
		output_folder.mkdir();
		File file= null;
		File[] files = input_folder.listFiles();

		if(files.length!=0)
			for(int index=0;index<files.length;++index){
				file= new File(files[index].toString());
				if(files[index].toString().endsWith(".csv")){
					//String inputName=file.getName().split(".csv")[0].substring(1);
					System.out.println("File"+index+": "+file.getName());


					try {
						RapidMiner.setExecutionMode(RapidMiner.ExecutionMode.COMMAND_LINE);
						RapidMiner.init();
						Process process = new Process(new File(cluster));
						Operator read_op = process.getOperator("Read CSV");
						read_op.setParameter(CSVExampleSource.PARAMETER_CSV_FILE, file.getAbsolutePath());
						read_op.setParameter(CSVExampleSource.PARAMETER_FIRST_ROW_AS_NAMES, "true");
						Operator setrole = process.getOperator("Set Role");
						setrole.setParameter(ChangeAttributeRole.PARAMETER_NAME, "loc_id");
						setrole.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, "label");
						setrole.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, "label");
						Operator setrole2 = process.getOperator("Set Role (2)");
						setrole2.setParameter(ChangeAttributeRole.PARAMETER_NAME, "img_id");
						setrole2.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, "id");
						Operator write_op = process.getOperator("Write CSV");
						write_op.setParameter(CSVExampleSetWriter.PARAMETER_CSV_FILE, output_folder+"//"+file.getName());
						IOContainer ioResult = process.run();
						ExampleSet resultSet1 = (ExampleSet)ioResult.getElementAt(0);
						System.out.println(resultSet1.toString());


						Iterator<Attribute> attributes = resultSet1.getAttributes().allAttributes();
						while(attributes.hasNext())
							System.out.println(attributes.next());
						process.stop();

					} catch (IOException | XMLException | OperatorException ex) {
						ex.printStackTrace();
					}
				}
			}
	}
}