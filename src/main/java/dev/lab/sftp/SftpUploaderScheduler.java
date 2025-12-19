package dev.lab.sftp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SftpUploaderScheduler {

    private final SftpUploader sftpUploader;

    // 1분마다 실행 (매 1분 마다)
    @Scheduled(cron = "0 * * * * *")
    public void runMoveTask() {
        log.info("=== A에서 B로 파일이동 시작 ===");
        sftpUploader.fileMoveExport();
        log.info("=== A에서 B로 파일이동 종료 ===");
    }
}
