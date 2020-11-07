package guru.bonacci.kafka.sftp;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@CsvRecord(separator = ",")
public class Sample {

	@DataField(pos = 1) String f1;
	@DataField(pos = 2) String f2;
	@DataField(pos = 3) String f3;
	@DataField(pos = 4) String f4;
}
