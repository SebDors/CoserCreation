package CoserCreation.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import CoserCreation.DAO.ColorDAO;
import CoserCreation.DTO.ColorDTO;
import CoserCreation.DTO.ColorMapper;
import CoserCreation.models.ColorModel;

@Service
public class ColorService {
    private final ColorDAO colorDAO;

    private final String fileSystemUploadDir = "/app/media/colors/";
    private final String webPath = "media/colors/";

    public ColorService(ColorDAO colorDAO) {
        this.colorDAO = colorDAO;
    }

    public List<ColorDTO> getAllColors() {
        return ColorMapper.toListDTO(colorDAO.findAll());
    }

    public ColorDTO getColorById(int id) {
        return ColorMapper.toDTO(colorDAO.findById(id).orElseThrow());
    }

    public void createColor(String name, MultipartFile image) {
        ColorModel newColor = new ColorModel();
        newColor.setName(name);

        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image, name); // Pass name
            newColor.setImage(imageUrl);
        }

        colorDAO.save(newColor);
    }

    public void deleteColorById(int id) {
        ColorModel color = colorDAO.findById(id).orElseThrow(() -> new RuntimeException("Color not found"));
        if (color.getImage() != null && !color.getImage().isEmpty()) {
            deleteImage(color.getImage());
        }
        colorDAO.deleteById(id);
    }

    public ColorDTO updateColor(int id, String name, MultipartFile image) {
        ColorModel existingColor = colorDAO.findById(id).orElseThrow(() -> new RuntimeException("Color not found"));
        existingColor.setName(name);

        if (image != null && !image.isEmpty()) {
            if (existingColor.getImage() != null && !existingColor.getImage().isEmpty()) {
                deleteImage(existingColor.getImage());
            }
            String imageUrl = saveImage(image, name); // Pass name
            existingColor.setImage(imageUrl);
        } else {
            // If no new image is provided, and the name is updated, we don't change the image.
            // If we want to remove the image, we would need a specific flag for it.
            // For now, if no new image, old image stays.
        }

        return ColorMapper.toDTO(colorDAO.save(existingColor));
    }

    private String saveImage(MultipartFile image, String colorName) { // Added colorName parameter
        File directory = new File(fileSystemUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileExtension = getFileExtension(image.getOriginalFilename());
        // Sanitize colorName for filename and add a timestamp to ensure uniqueness
        String sanitizedColorName = colorName.replaceAll("[^a-zA-Z0-9.-]", "_");
        String uniqueFileName = sanitizedColorName + "_" + System.currentTimeMillis() + fileExtension;
        
        Path filePath = Paths.get(fileSystemUploadDir + uniqueFileName);

        try {
            Files.write(filePath, image.getBytes());
            return webPath + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save color image", e);
        }
    }

    private void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        String fileName = imageUrl.replace(webPath, "");
        Path filePath = Paths.get(fileSystemUploadDir + fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Failed to delete color image: " + fileName + " Error: " + e.getMessage());
            // Log the error but continue, deletion of the file is not critical to the DB entry
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
