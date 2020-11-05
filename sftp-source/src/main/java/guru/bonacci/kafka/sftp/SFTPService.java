package guru.bonacci.kafka.sftp;

import org.springframework.stereotype.Service;

import java.util.Map;
 
@Service
public class SFTPService
{
    public void downloadAndProcessFile(String fileContent, Map<String, Object> headers)
    {
        System.out.println("File: " + headers.get("fileName") + " | Content: " + fileContent + "|");
    }
}