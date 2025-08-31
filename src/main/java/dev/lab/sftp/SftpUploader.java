package dev.lab.sftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class SftpUploader {

    @Value("${localRoot.export.scan}")
    private String localSrcRoot;

    @Value("${localDest.import.scan}")
    private String localDestRoot;

    // 전체 on/off 스위치
    @Value("${mvExport.enabled:true}")
    private boolean mvEnabled;

    // 이동 대상 파일명 (기본값: sonar_ping_deep.wav)
    @Value("${mvExport.target.filename:sonar_ping_deep.wav}")
    private String targetFilename;

    // A(소스 폴더)에서 B(목적지 폴더)로 targetFilename 하나만 이동
    public void fileMoveExport() {
        if (!mvEnabled) {
            log.warn("파일 이동이 비활성화되어 있습니다 (mvExport.enabled=false).");
            return;
        }

        final Path srcRoot = Paths.get(localSrcRoot);
        final Path destRoot = Paths.get(localDestRoot);
        final Path srcFile = srcRoot.resolve(targetFilename);

        log.info("srcRoot = {}", srcRoot);
        log.info("destRoot = {}", destRoot);
        log.info("target  = {}", srcFile);

        try {
            if (!Files.exists(srcRoot)) {
                log.warn("소스 폴더가 존재하지 않습니다: {}", srcRoot);
                return;
            }
            if (!Files.exists(srcFile) || !Files.isRegularFile(srcFile)) {
                log.warn("이동 대상 파일이 없습니다: {}", srcFile);
                return;
            }
            if (!Files.exists(destRoot)) {
                Files.createDirectories(destRoot);
            }

            Path dstFile = destRoot.resolve(srcFile.getFileName()).normalize();

            try {
                Files.move(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {
                // 다른 볼륨/FS일 가능성 대비: copy → delete 폴백
                Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
                Files.deleteIfExists(srcFile);
            }

            log.info("이동 완료: {} -> {}", srcFile, dstFile);

        } catch (Exception e) {
            log.error("파일 이동 실패", e);
        }
    }

    // 필요 시 코드에서 직접 파일명을 지정해서 호출할 수도 있음
    public void moveOne(String filename) {
        String old = this.targetFilename;
        try {
            this.targetFilename = filename;
            fileMoveExport();
        } finally {
            this.targetFilename = old;
        }
    }
}
