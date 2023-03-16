package com.springboot.rest.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.config.StorageProperties;
import com.springboot.rest.dto.mapper.FileMapper;
import com.springboot.rest.dto.model.FileDTO;
import com.springboot.rest.dto.model.LongestLineDTO;
import com.springboot.rest.exception.EmptyFileException;
import com.springboot.rest.exception.FileFormatException;
import com.springboot.rest.exception.FileNotFoundException;
import com.springboot.rest.exception.FileStorageSecurityException;
import com.springboot.rest.exception.StorageException;
import com.springboot.rest.model.FileEntity;
import com.springboot.rest.repository.FileRepository;
import com.springboot.rest.utility.FileUploadHelper;
import com.springboot.rest.utility.LongestLineHelper;


@Service
public class FileStorageService implements StorageService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private LineService lineService;

    private final Path rootLocation;

    @Autowired
    public FileStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    @Transactional(rollbackFor = Exception.class) 
    public void upload(MultipartFile mfile) {

        String fileName = StringUtils.cleanPath(mfile.getOriginalFilename());

        if (mfile.isEmpty()) {
            throw new FileNotFoundException();
        }

        if (!FileUploadHelper.isTextFile(fileName)) {
            throw new FileFormatException(fileName);
        }


        if (FileUploadHelper.isFileEmpty(mfile)) {
            throw new EmptyFileException();
        }

        String filePath = store(mfile);
        PriorityQueue<LongestLineDTO> pq = LongestLineHelper.getPriorityQueue(100,filePath);
        saveLongest20Lines(fileName, filePath, pq);
        update100LongestLines(pq);
    }

    private void saveLongest20Lines(String fileName, String filePath, PriorityQueue<LongestLineDTO> pq) {
        String twentyLongestLine = LongestLineHelper.getLongest20LinesFromFile(pq);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(filePath);
        fileEntity.setCreatedDate(LocalDateTime.now());
        fileEntity.setTwentyLongestLine(twentyLongestLine);
        fileRepository.save(fileEntity);
    }

    private void update100LongestLines(PriorityQueue<LongestLineDTO> newLines) {
        List<LongestLineDTO> longestLineList = lineService.loadAll();
        PriorityQueue<LongestLineDTO> linesFromDB = LongestLineHelper.fillPriorityQueue(longestLineList);
        longestLineList = LongestLineHelper.getLongest100LinesFromFiles(linesFromDB, newLines);
        lineService.deleteAll();
        lineService.saveAll(longestLineList);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public String store(MultipartFile file) {

        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new FileStorageSecurityException();
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileFormatException(destinationFile.toString());
        }

        return destinationFile.toString();

    }

    
   

    @Override
    public FileDTO findFirstByOrderByCreatedDateDesc() {
        Optional<FileEntity> fileEntity = Optional.ofNullable(fileRepository.findFirstByOrderByCreatedDateDesc());
        if (fileEntity.isPresent()) {
            return FileMapper.toFileDTO(fileEntity.get());
        }
        throw new FileNotFoundException();
    }

    @Override
    public FileDTO getRandomFile() {

        Optional<FileEntity> fileEntity = Optional.ofNullable(fileRepository.getRandomFile());
        if (fileEntity.isPresent()) {
            return FileMapper.toFileDTO(fileEntity.get());
        }
        throw new FileNotFoundException();
    }

    @Override
    public String getLongestTwentyLinesOfFile() {

        Optional<FileEntity> fileEntity = Optional.ofNullable(fileRepository.getRandomFile());
        if (fileEntity.isPresent()) {
            FileEntity file = fileRepository.findTwenty_Longest_LineById(fileEntity.get().getId());
            return file.getTwentyLongestLine();
        }
        throw new FileNotFoundException();
    }
}

