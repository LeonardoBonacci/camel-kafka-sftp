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
public class Foo {

	@DataField(pos = 1) public String f1;
	@DataField(pos = 2) public String f2;
	@DataField(pos = 3) public String f3;
	@DataField(pos = 4) public String f4;
}
