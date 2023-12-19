package com.funs.gifticonservice.domain.gifticon.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.funs.gifticonservice.domain.gifticon.entity.Gifticon;
import com.funs.gifticonservice.domain.gifticon.exception.NotConvertFileException;
import com.funs.gifticonservice.domain.gifticon.exception.NotExistGifticonException;
import com.funs.gifticonservice.domain.gifticon.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final GifticonRepository gifticonRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String upload(Long gifticonId, MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new NotConvertFileException("MultipartFile -> File로 전환이 실패했습니다."));
        System.out.println("기프티콘 service에 필요한 s3Uploader convert 호출 직후");

        return upload(gifticonId, uploadFile, dirName);
    }


    // s3로 파일 업로드하기
    private String upload(Long gifticonId, File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        System.out.println("기프티콘 service에 필요한 s3Uploader putS3 사진 파일 업로드 성공");

        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                        .orElseThrow(() -> new NotExistGifticonException("해당 기프티콘이 존재하지 않습니다."));

        gifticon.setImage(uploadImageUrl);
        return uploadImageUrl;
//        removeNewFile(uploadFile);
    }

    // 업로드하기
    private String putS3(File uploadFile, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String baseName = FilenameUtils.getBaseName(originalFilename);
        String extension = FilenameUtils.getExtension(originalFilename);

        String uniqueFilename = baseName + "_" + System.currentTimeMillis() + "." + extension;
        File convertFile = new File(System.getProperty("user.dir") + "/" + uniqueFilename);
//        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        System.out.println("기프티콘 service에 필요한 s3Uploader convert 호출 성공");


        return Optional.empty();
    }
}