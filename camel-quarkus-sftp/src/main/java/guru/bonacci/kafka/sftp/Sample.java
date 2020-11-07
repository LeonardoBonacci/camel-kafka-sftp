package guru.bonacci.kafka.sftp;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CsvRecord(separator = ",")
public class Sample {

	@DataField(pos = 1) private String f1;
	@DataField(pos = 2) private String f2;
	@DataField(pos = 3) private String f3;
	@DataField(pos = 4) private String f4;
}