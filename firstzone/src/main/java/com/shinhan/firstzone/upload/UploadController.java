package com.shinhan.firstzone.upload;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shinhan.firstzone.repository.ProfileRepository;
import com.shinhan.firstzone.service2.WebBoardService;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.ProfileEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@Log4j2
public class UploadController {
	// Spring 소유 @Value
    @Value("${com.shinhan.upload.path}")
    private String uploadPath; // application.properties 파일에 설정값을 읽어서 Injection
    
    @Autowired
    ProfileRepository proRepo;
    // WebBoardService boardService;
    
    @PostMapping("/uploadAjax")
    // 데이터와 상태값을 같이 보낼 수 있음
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles, String mid/*WebBoardDTO dto*/){
    	// System.out.println(dto);
    	System.out.println("mid : " + mid);
    	
    	MemberEntity member = MemberEntity.builder().mid(mid).build();
    	
        List<UploadResultDTO> resultDTOList = new ArrayList<>();
        // String saveName = null;
        
        // 향상된 for문을 사용중이므로 현재가 마지막인지 알 수 없음
        int i = 0;
        
        for (MultipartFile uploadFile: uploadFiles) {
            if(uploadFile.getContentType().startsWith("image") == false) { // getContentType : image인 것만 허용
                log.warn("this file is not image type");
                
                // ResponseEntity : 보내는 데이터 값, 상태
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            
            String originalName = uploadFile.getOriginalFilename(); // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            
            log.info("fileName: " + fileName);
            
            String folderPath = makeFolder(); // 날짜 폴더 생성           
            String uuid = UUID.randomUUID().toString(); // UUID
            
            // 저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +"_" + fileName;
            Path savePath = Paths.get(saveName);
            
            try {            
                uploadFile.transferTo(savePath); // 원본 파일 저장 (upload 하는 부분)
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator 
                										  +"s_" + uuid + "_" + fileName; // 섬네일 파일 이름은 중간에 s_로 시작하도록
                
                File thumbnailFile = new File(thumbnailSaveName);                
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100 ); // 섬네일 생성
                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
                
                // ========== DB에 저장 (하나의 멤버가 여러개의 profile을 가짐) ==========
                ProfileEntity entity = ProfileEntity.builder()
                		.fname(saveName)
                		.member(member)
                		.currentYn(i == uploadFiles.length - 1 ? true : false) // 현재가 마지막인지 확인
                		.build();
                
                proRepo.save(entity);
                i++;
                // ===============================================
                
            } catch (IOException e) { e.printStackTrace(); }
        } // end for
        
        // DB에 저장
        /*
        dto.setContent(dto.getContent() + " 이미지 경로 : " + saveName);
        boardService.register(dto);
        */
        
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath =  str.replace("//", File.separator); // File.separator 파일의 구분자로 변경 /
        
        // make folder --------
        File uploadPathFolder = new File(uploadPath, folderPath); // C:upload\2025/07/29 형식으로 생성

        if (uploadPathFolder.exists() == false) { // 존재하는지 확인
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {
        ResponseEntity<byte[]> result = null;
        
        try {
            String srcFileName =  URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName: " + srcFileName);
            
            File file = new File(uploadPath + File.separator + srcFileName);
            
            if(size != null && size.equals("1")){
                file  = new File(file.getParent(), file.getName().substring(2));
            }
            
            log.info("file: " + file);
            
            HttpHeaders header = new HttpHeaders();
            // MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
            
        } catch (Exception e) {
            log.error(e.getMessage());
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;
        
        try {
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();
            
            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();
            
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}