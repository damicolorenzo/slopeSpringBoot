package com.lorenzoproject.slope.service.image;

import com.lorenzoproject.slope.dto.ImageDto;
import com.lorenzoproject.slope.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long skifacilityId);
    void updateImage(MultipartFile file, Long imageId);
}
