package dev.lab.sftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SftpUploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SftpUploaderApplication.class, args);
	}

}
